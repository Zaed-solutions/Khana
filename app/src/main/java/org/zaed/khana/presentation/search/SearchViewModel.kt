package org.zaed.khana.presentation.search

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()
    init {
        //TODO: Initialize the uiState with the current user id and load user wishlist ids
    }
    fun handleUiAction(action: SearchUiAction) {
        when (action) {
            else -> Unit
        }
    }
}