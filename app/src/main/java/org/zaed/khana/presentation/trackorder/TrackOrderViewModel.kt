package org.zaed.khana.presentation.trackorder

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.zaed.khana.data.repository.AuthenticationRepository
import org.zaed.khana.data.repository.CartRepository
import org.zaed.khana.data.repository.OrderRepository

class TrackOrderViewModel(
    private val orderRepo: OrderRepository,
    private val cartRepository: CartRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(TrackOrderUiState())
    val uiState = _uiState.asStateFlow()
    fun init(orderId: String, cartItemId: String) {
        fetchCartItem(orderId, cartItemId)
        fetchOrderDetails(orderId)
    }

    private fun fetchOrderDetails(orderId: String) {
        viewModelScope.launch {
            orderRepo.fetchOrderById(orderId).onSuccessWithData { order ->
                _uiState.update { it.copy(order = order) }
            }.onFailure {
                Log.e("TrackOrderViewModel:fetchOrderDetails", "fetchOrderDetails: $it")
            }
        }
    }

    private fun fetchCartItem(orderId: String, cartItemId: String) {
        viewModelScope.launch {
            cartRepository.fetchOrderedCartItem(orderId = orderId, cartItemId = cartItemId).onSuccessWithData { item ->
                _uiState.update { it.copy(cartItem = item) }
            }.onFailure {
                Log.e("TrackOrderViewModel:fetchCartItem", "fetchCartItem: $it")
            }
        }
    }
}