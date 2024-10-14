package org.zaed.khana.presentation.myorders

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.zaed.khana.data.model.OrderStatus
import org.zaed.khana.data.repository.AuthenticationRepository
import org.zaed.khana.data.repository.OrderRepository

class MyOrdersViewModel(
    private val authRepo: AuthenticationRepository,
    private val orderRepo: OrderRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyOrdersUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchCurrentUser()
    }

    private fun fetchCurrentUser() {
        viewModelScope.launch {
            authRepo.getSignedInUser().onSuccessWithData { user ->
                _uiState.value = uiState.value.copy(currentUser = user)
                fetchUserOrders()
            }.onFailure {
                Log.e("MyOrdersViewModel:fetchCurrentUser", "fetchCurrentUser: $it")
            }
        }
    }

    private fun fetchUserOrders() {
        viewModelScope.launch {
            orderRepo.fetchUserOrders(_uiState.value.currentUser.id).collect { result ->
                result.onSuccessWithData { orders ->
                    _uiState.value = uiState.value.copy(orders = orders)
                    updateDisplayedItems(OrdersTabs.ACTIVE)
                }.onFailure {
                    Log.e("MyOrdersViewModel:fetchUserOrders", "fetchUserOrders: $it")
                }
            }
        }
    }

    fun handleUiAction(action: MyOrdersUiAction) {
        when (action) {
            is MyOrdersUiAction.OnChangeTab -> updateDisplayedItems(action.tab)
            else -> Unit
        }
    }

    private fun updateDisplayedItems(tab: OrdersTabs) {
        viewModelScope.launch {
            val items = when (tab) {
                OrdersTabs.ACTIVE -> {
                    _uiState.value.orders.filter { it.orderStatus == OrderStatus.SHIPPED.name || it.orderStatus == OrderStatus.CONFIRMED.name }
                        .flatMap { it.cartItems }
                }

                OrdersTabs.COMPLETED -> {
                    _uiState.value.orders.filter { it.orderStatus == OrderStatus.DELIVERED.name }
                        .flatMap { it.cartItems }
                }

                OrdersTabs.CANCELLED -> {
                    _uiState.value.orders.filter { it.orderStatus == OrderStatus.CANCELLED.name }
                        .flatMap { it.cartItems }
                }
            }
            _uiState.value = uiState.value.copy(displayedItems = items)
        }
    }
}