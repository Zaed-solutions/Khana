package org.zaed.khana.presentation.category

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.zaed.khana.data.repository.AuthenticationRepository
import org.zaed.khana.data.repository.ProductRepository

class CategoryViewModel(
    private val authRepo: AuthenticationRepository,
    private val productRepo: ProductRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState = _uiState.asStateFlow()
    fun init(category: String) {
        _uiState.update { it.copy(category = category) }
        fetchCurrentUser()
        fetchProducts()
    }
    private fun fetchCurrentUser() {
        viewModelScope.launch {
            authRepo.getSignedInUser().onSuccessWithData { user ->
                _uiState.update { it.copy(currentUser = user) }
                fetchWishlistedProductsIds()
            }.onFailure { error ->
                Log.e("CategoryViewModel", "Failed to fetch user: $error")
            }
        }
    }
    private fun fetchWishlistedProductsIds(){
        viewModelScope.launch {
            productRepo.fetchWishlistedProductsIds(_uiState.value.currentUser.id).collect{ result ->
                result.onSuccessWithData { ids ->
                    _uiState.update { it.copy(wishlistedProductsIds = ids) }
                }.onFailure { error ->
                    Log.e("CategoryViewModel", "Failed to fetch wishlisted products ids: $error")
                }
            }
        }
    }
    private fun fetchProducts() {
        viewModelScope.launch {
            productRepo.fetchProductsByCategory(_uiState.value.category).collect{ result ->
                result.onSuccessWithData { products ->
                    _uiState.update { it.copy(products = products) }
                }.onFailure { error ->
                    Log.e("CategoryViewModel", "Failed to fetch products: $error")
                }
            }
        }
    }
    fun handleUiAction(action: CategoryUiAction) {
        when (action) {

            is CategoryUiAction.OnWishlistClicked -> {
                wishlistProduct(productId = action.productId)
            }

            else -> Unit
        }
    }
    private fun wishlistProduct(productId: String) {
        viewModelScope.launch {
            if (uiState.value.wishlistedProductsIds.contains(productId)) {
                productRepo.removeWishlistedProduct(
                    productId = productId,
                    userId = uiState.value.currentUser.id
                ).onSuccess {
                    _uiState.update { it.copy(wishlistedProductsIds = it.wishlistedProductsIds.minus(productId)) }
                }.onFailure { error ->
                    Log.e("ProductDetailsViewModel:wishlistProduct", error.userMessage)
                }
            } else {
                productRepo.addWishlistedProduct(
                    productId = productId,
                    userId = uiState.value.currentUser.id
                ).onSuccess {
                    _uiState.update { it.copy(wishlistedProductsIds = it.wishlistedProductsIds.plus(productId)) }
                }.onFailure { error ->
                    Log.e("ProductDetailsViewModel:wishlistProduct", error.userMessage)
                }
            }
        }
    }
}