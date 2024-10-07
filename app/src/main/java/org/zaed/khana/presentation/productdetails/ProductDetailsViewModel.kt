package org.zaed.khana.presentation.productdetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.zaed.khana.data.model.Color
import org.zaed.khana.data.repository.AuthenticationRepository
import org.zaed.khana.data.repository.CartRepository
import org.zaed.khana.data.repository.ProductRepository
import org.zaed.khana.data.util.ProductResult

class ProductDetailsViewModel(
    private val productRepo: ProductRepository,
    private val authRepo: AuthenticationRepository
    private val cartRepo: CartRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductDetailsUiState())
    val uiState = _uiState.asStateFlow()
    fun init(productId: String) {
        _uiState.update { it.copy(productId = productId) }
        fetchCurrentUser()
        fetchProduct(productId)
    }
    private fun fetchCurrentUser(){
        viewModelScope.launch {
            authRepo.getSignedInUser().onSuccessWithData { user ->
                _uiState.update { it.copy(currentUser = user) }
            }.onFailure {
                Log.e("ProductDetailsViewModel:fetchCurrentUser", it.userMessage)
            }
        }
    }
    private fun fetchProduct(productId: String) {
        viewModelScope.launch {
            productRepo.fetchProductById(productId).onSuccessWithData { product ->
                _uiState.update {
                    it.copy(
                        product = product,
                        selectedSize = product.availableSizes.firstOrNull() ?: "",
                        selectedColor = product.availableColors.firstOrNull() ?: Color()
                    )
                }
                checkIfIsWishlisted(productId)
            }.onFailure { error ->
                Log.e("ProductDetailsViewModel:fetchProduct", error.userMessage)
            }
        }
    }

    private fun checkIfIsWishlisted(productId: String) {
        viewModelScope.launch {
            productRepo.checkIfIsProductWishlisted(uiState.value.currentUser.id, productId)
                .onSuccessWithData { isWishlisted ->
                    _uiState.update { it.copy(isWishlisted = isWishlisted) }
                }.onFailure { error ->
                Log.e("ProductDetailsViewModel:checkIfIsWishlisted", error.userMessage)
            }
        }
    }

    fun handleUiAction(action: ProductDetailsUiAction) {
        when (action) {
            is ProductDetailsUiAction.OnSelectSize -> {
                updateSelectedSize(action.size)
            }

            is ProductDetailsUiAction.OnSelectColor -> {
                updateSelectedColor(action.hexColor)
            }

            is ProductDetailsUiAction.OnWishlistProduct -> {
                wishlistProduct(productId = uiState.value.productId)
            }

            is ProductDetailsUiAction.OnAddToCartClicked -> {
                addItemToCart()
            }

            else -> Unit
        }
    }

    private fun addItemToCart() {
        viewModelScope.launch {
            with(uiState.value) {
                cartRepo.addItemToCart(currentUserId, productId, selectedColor, selectedSize)
                    .onSuccess {
                        _uiState.update { it.copy(result = ProductResult.IDLE) }
                    }.onFailure { error ->
                    Log.e("ProductDetailsViewModel:addItemToCart", error.userMessage)
                    _uiState.update { it.copy(result = error) }
                }
            }
        }
    }

    private fun wishlistProduct(productId: String) {
        viewModelScope.launch {
            if (uiState.value.isWishlisted) {
                productRepo.removeWishlistedProduct(
                    productId = productId,
                    userId = uiState.value.currentUser.id
                ).onSuccess {
                    _uiState.update { it.copy(isWishlisted = false, result = ProductResult.IDLE) }
                }.onFailure { error ->
                    Log.e("ProductDetailsViewModel:wishlistProduct", error.userMessage)
                    _uiState.update { it.copy(result = error) }
                }
            } else {
                productRepo.addWishlistedProduct(
                    productId = productId,
                    userId = uiState.value.currentUser.id
                ).onSuccess {
                    _uiState.update { it.copy(isWishlisted = true, result = ProductResult.IDLE) }
                }.onFailure { error ->
                    Log.e("ProductDetailsViewModel:wishlistProduct", error.userMessage)
                    _uiState.update { it.copy(result = error) }
                }
            }
        }
    }

    private fun updateSelectedColor(hexColor: String) {
        viewModelScope.launch {
            val selectedColor =
                uiState.value.product.availableColors.find { it.hex == hexColor } ?: Color()
            _uiState.update { it.copy(selectedColor = selectedColor) }
        }
    }

    private fun updateSelectedSize(size: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(selectedSize = size) }
        }
    }
}