package org.zaed.khana.presentation.productdetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.zaed.khana.data.model.Color
import org.zaed.khana.data.repository.ProductRepository

class ProductDetailsViewModel(
    private val productRepo: ProductRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductDetailsUiState())
    val uiState = _uiState.asStateFlow()
    fun init(productId: String) {
        _uiState.update { it.copy(productId = productId) }
        fetchProduct(productId)
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
    private fun checkIfIsWishlisted(productId: String){
        viewModelScope.launch {
            productRepo.checkIfIsProductWishlisted(uiState.value.currentUserId, productId).onSuccessWithData{ isWishlisted ->
                _uiState.update { it.copy(isWishlisted = isWishlisted) }
            }.onFailure{ error ->
                Log.e("ProductDetailsViewModel:checkIfIsWishlisted", error.userMessage)
            }
        }
    }
    fun handleUiAction(action: ProductDetailsUiAction){
        when(action){
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
                //TODO("Add Item to cart")
            }
            else -> Unit
        }
    }

    private fun wishlistProduct(productId: String){
        viewModelScope.launch {
            if(uiState.value.isWishlisted) {
                productRepo.removeWishlistedProduct(productId = productId, userId = uiState.value.currentUserId).onSuccess {
                    _uiState.update { it.copy(isWishlisted = false) }
                }.onFailure { error ->
                    Log.e("ProductDetailsViewModel:wishlistProduct", error.userMessage)
                }
            } else {
                productRepo.addWishlistedProduct(productId = productId, userId = uiState.value.currentUserId).onSuccess {
                    _uiState.update { it.copy(isWishlisted = true) }
                }.onFailure { error ->
                    Log.e("ProductDetailsViewModel:wishlistProduct", error.userMessage)
                }
            }
        }
    }

    private fun updateSelectedColor(hexColor: String){
        viewModelScope.launch {
            val selectedColor = uiState.value.product.availableColors.find { it.hex == hexColor }?:Color()
            _uiState.update { it.copy(selectedColor = selectedColor) }
        }
    }
    private fun updateSelectedSize(size: String){
        viewModelScope.launch {
            _uiState.update { it.copy(selectedSize = size) }
        }
    }
}