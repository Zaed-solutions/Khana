package org.zaed.khana.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()
    init{
        TODO("get hasNewNotifications")
        TODO("get ads")
        TODO("get categories")
        TODO("get flash sale end time")
        TODO("get labels")
        TODO("get products")
        TODO("get wishlisted products")
    }
    fun handleUiAction(action: HomeUiAction){
        when(action){
            is HomeUiAction.OnChangeSearchingStatus -> {
                updateSearchingStatus(action.isSearching)
            }
            is HomeUiAction.OnSearchQueryChanged -> {
                updateSearchQuery(action.newQuery)
            }
            is HomeUiAction.OnSelectLabel -> {
                updateSelectedLabel(action.label)
            }
            is HomeUiAction.OnWishlistProduct -> {
                wishlistProduct(action.productId)
            }
            else -> Unit
        }
    }
    private fun updateSearchingStatus(isSearching: Boolean){
        viewModelScope.launch {
            _uiState.update { it.copy(isSearching = isSearching) }
        }
    }
    private fun updateSearchQuery(query: String){
        viewModelScope.launch {
            _uiState.update { it.copy(searchQuery = query) }
        }
    }
    private fun updateSelectedLabel(label: String){
        viewModelScope.launch {
            _uiState.update { it.copy(selectedLabel = label) }
            TODO("update products based on label")
        }
    }
    private fun wishlistProduct(productId: String){
        viewModelScope.launch {
            if(uiState.value.wishlistedProducts.contains(productId)) {
                _uiState.update { it.copy(wishlistedProducts = it.wishlistedProducts.minusElement(productId)) }
                TODO("update wishlisted items on server")
            } else {
                _uiState.update { it.copy(wishlistedProducts = it.wishlistedProducts.plus(productId)) }
                TODO("update wishlisted items on server")
            }
        }
    }
}