package org.zaed.khana.presentation.cart

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartViewModel(

): ViewModel(){
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState = _uiState.asStateFlow()
    init{
        //TODO: get current user id
        //TODO: get user cart items
    }
    fun handleUiAction(action: CartUiAction){
        when(action){
            is CartUiAction.OnApplyPromoCode -> TODO()
            is CartUiAction.OnDecrementItemQuantity -> TODO()
            is CartUiAction.OnIncrementItemQuantity -> TODO()
            is CartUiAction.OnRemoveItemFromCart -> TODO()
            else -> Unit
        }
    }
}