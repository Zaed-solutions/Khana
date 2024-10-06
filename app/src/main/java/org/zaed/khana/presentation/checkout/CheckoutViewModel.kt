package org.zaed.khana.presentation.checkout

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CheckoutViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(CheckoutUiState())
    val uiState = _uiState.asStateFlow()
    fun init() {
        //TODO: get current user id
        //TODO: get shipping address
        //TODO: get order items
    }
    fun handleUiAction(action: CheckoutUiAction) {}
}