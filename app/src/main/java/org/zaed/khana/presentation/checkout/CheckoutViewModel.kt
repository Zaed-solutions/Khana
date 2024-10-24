package org.zaed.khana.presentation.checkout

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.zaed.khana.data.model.Order
import org.zaed.khana.data.model.ShippingAddress
import org.zaed.khana.data.model.ShippingType
import org.zaed.khana.data.repository.AuthenticationRepository
import org.zaed.khana.data.repository.CartRepository
import org.zaed.khana.data.repository.OrderRepository
import org.zaed.khana.data.repository.ShippingAddressRepository

class CheckoutViewModel(
    private val authRepo: AuthenticationRepository,
    private val addressRepo: ShippingAddressRepository,
    private val orderRepo: OrderRepository,
    private val cartRepo: CartRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CheckoutUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchCurrentUser()
    }

    private fun fetchCurrentUser() {
        viewModelScope.launch {
            authRepo.getSignedInUser().onSuccessWithData { user ->
                _uiState.update { it.copy(currentUser = user) }
                fetchUserShippingAddresses()
                fetchCartItems()
            }.onFailure {
                Log.e("HomeViewModel:fetchCurrentUser", it.userMessage)
            }
        }
    }

    private fun fetchUserShippingAddresses() {
        viewModelScope.launch {
            addressRepo.fetchShippingAddresses(_uiState.value.currentUser.id).collect { result ->
                result.onSuccessWithData { data ->
                    _uiState.update {
                        it.copy(
                            shippingAddresses = data,
                            selectedShippingAddress = data.firstOrNull()?: ShippingAddress(),
                        )
                    }
                }.onFailure {
                    Log.e("CheckoutViewModel:fetchUserShippingAddresses", it.userMessage)
                }
            }
        }
    }

    private fun fetchCartItems() {
        viewModelScope.launch {
            cartRepo.fetchUserCartItems(_uiState.value.currentUser.id).collect { result ->
                result.onSuccessWithData { data ->
                    _uiState.update { it.copy(cartItems = data, isLoading = false) }
                }.onFailure {
                    Log.e("CheckoutViewModel:fetchCartItems", it.userMessage)
                }
            }
        }
    }

    fun handleUiAction(action: CheckoutUiAction) {
        when (action) {
            is CheckoutUiAction.OnAddShippingAddress -> addShippingAddress(action.newAddress)
            is CheckoutUiAction.OnChangeShippingAddress -> updateShippingAddress(action.newAddress)
            is CheckoutUiAction.OnChangeShippingType -> updateShippingType(action.newType)
            CheckoutUiAction.OnContinueToPaymentClicked -> placeOrder()
            else -> Unit
        }
    }

    private fun addShippingAddress(newAddress: ShippingAddress) {
        viewModelScope.launch {
            addressRepo.addShippingAddress(newAddress.copy(userId = _uiState.value.currentUser.id))
                .onSuccessWithData { addressId ->
                    val updatedAddress = newAddress.copy(id = addressId)
                    _uiState.update {
                        it.copy(
                            shippingAddresses = it.shippingAddresses + updatedAddress,
                            selectedShippingAddress = updatedAddress
                        )
                    }
                }.onFailure {
                Log.e("CheckoutViewModel:addShippingAddress", it.userMessage)
            }
        }
    }

    private fun updateShippingAddress(newAddress: ShippingAddress) {
        viewModelScope.launch {
            _uiState.update { it.copy(selectedShippingAddress = newAddress) }
        }
    }

    private fun updateShippingType(newType: ShippingType) {
        viewModelScope.launch {
            _uiState.update { it.copy(selectedShippingType = newType) }
        }
    }

    private fun placeOrder() {
        viewModelScope.launch {
            val order = Order(
                userId = _uiState.value.currentUser.id,
                shippingAddress = _uiState.value.selectedShippingAddress,
                shippingType = _uiState.value.selectedShippingType.title,
                cartItems = _uiState.value.cartItems
            )
            orderRepo.placeOrder(
                order = order
            ).onSuccessWithData { orderId ->
                _uiState.update { it.copy(orderId = orderId, isOrderPlaced = true) }
            }.onFailure {
                Log.e("CheckoutViewModel:placeOrder", it.userMessage)
            }
        }
    }
}