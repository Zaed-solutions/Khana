package org.zaed.khana.presentation.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.zaed.khana.data.repository.AuthenticationRepository
import org.zaed.khana.data.repository.CartRepository

class CartViewModel(
    private val cartRepo: CartRepository,
    private val authRepo: AuthenticationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchCurrentUser()
    }

    private fun fetchCurrentUser(){
        viewModelScope.launch {
            authRepo.getSignedInUser().onSuccessWithData { user ->
                _uiState.update { it.copy(currentUser = user) }
                fetchUserCartItems()
                fetchDeliveryFee()
            }.onFailure {
                Log.e("CartViewModel:fetchCurrentUser", it.userMessage)
            }
        }
    }

    private fun fetchUserCartItems() {
        viewModelScope.launch {
            cartRepo.fetchUserCartItems(uiState.value.currentUser.id).collect { result ->
                result.onSuccessWithData { items ->
                    _uiState.update { it.copy(cartItems = items) }
                }.onFailure {
                    Log.e(
                        "${this@CartViewModel::class.simpleName}:fetchUserCartItems",
                        it.userMessage
                    )
                }
            }
        }
    }

    private fun fetchDeliveryFee() {
        viewModelScope.launch {
            cartRepo.fetchDeliverFee(uiState.value.currentUser.id)
                .onSuccessWithData { fee ->
                    _uiState.update { it.copy(deliveryFee = fee) }
                }.onFailure {
                    Log.e(
                        "${this@CartViewModel::class.simpleName}:fetchDeliveryFee",
                        it.userMessage
                    )
                }
        }
    }

    fun handleUiAction(action: CartUiAction) {
        when (action) {
            is CartUiAction.OnApplyPromoCode -> {
                applyPromoCode(action.code)
            }

            is CartUiAction.OnDecrementItemQuantity -> {
                decrementItemQuantity(action.productId)
            }

            is CartUiAction.OnIncrementItemQuantity -> {
                incrementItemQuantity(action.productId)
            }

            is CartUiAction.OnRemoveItemFromCart -> {
                removeCartItem(action.productId)
            }

            else -> Unit
        }
    }

    private fun applyPromoCode(code: String) {
        viewModelScope.launch {
            cartRepo.fetchPromoCodeDiscountPercentage(code)
                .onSuccessWithData { discountPercentage ->
                    _uiState.value = uiState.value.copy(discountPercentage = discountPercentage)
                }.onFailure {
                    Log.e("${this@CartViewModel::class.simpleName}:applyPromoCode", it.userMessage)
                }
        }
    }

    private fun incrementItemQuantity(productId: String) {
        viewModelScope.launch {
            val item = uiState.value.cartItems.find { it.productId == productId } ?: return@launch
            val newQuantity = item.quantity + 1
            cartRepo.updateItemQuantity(item.id, newQuantity)
                .onSuccess {
                    val updatedItems = uiState.value.cartItems.map {
                        if (it.productId == productId) {
                            it.copy(quantity = newQuantity)
                        } else {
                            it
                        }
                    }
                    _uiState.value = uiState.value.copy(cartItems = updatedItems)
                }.onFailure {
                    Log.e(
                        "${this@CartViewModel::class.simpleName}:incrementItemQuantity",
                        it.userMessage
                    )
                }
        }
    }

    private fun decrementItemQuantity(productId: String) {
        viewModelScope.launch {
            val item = uiState.value.cartItems.find { it.productId == productId } ?: return@launch
            val newQuantity = item.quantity - 1
            cartRepo.updateItemQuantity(item.id, newQuantity)
                .onSuccess {
                    val updatedItems = uiState.value.cartItems.map {
                        if (it.productId == productId) {
                            it.copy(quantity = newQuantity)
                        } else {
                            it
                        }
                    }
                    _uiState.value = uiState.value.copy(cartItems = updatedItems)
                }.onFailure {
                    Log.e(
                        "${this@CartViewModel::class.simpleName}:decrementItemQuantity",
                        it.userMessage
                    )
                }
        }
    }

    private fun removeCartItem(productId: String) {
        viewModelScope.launch {
            val item = uiState.value.cartItems.find { it.productId == productId } ?: return@launch
            cartRepo.removeCartItem(item.id)
                .onSuccess {
                    val updatedItems = uiState.value.cartItems.filter { it.productId != productId }
                    _uiState.value = uiState.value.copy(cartItems = updatedItems)
                }.onFailure {
                    Log.e(
                        "${this@CartViewModel::class.simpleName}:removeCartItem",
                        it.userMessage
                    )
                }
        }
    }
}