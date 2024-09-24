package org.zaed.khana.presentation.wishlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.zaed.khana.data.repository.ProductRepository

class WishlistViewModel(
    private val productRepo: ProductRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(WishlistUiState())
    val uiState = _uiState.asStateFlow()

    init {
//        getCurrentUserId()
        getWishlistedProducts()
        fetchCategoriesFromWishlistedProducts()
    }

    private fun getWishlistedProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            productRepo.fetchWishlistedProducts(uiState.value.currentUserId).collect { result ->
                result.onSuccessWithData { products ->
                    _uiState.update {
                        it.copy(
                            wishlistedProducts = products,
                        )
                    }
                }.onLoading {
                    //TODO: handle loading
                }.onFailure { error ->
                    Log.e(
                        "${this@WishlistViewModel::class.simpleName}:getWishlistedProducts",
                        error.userMessage
                    )
                    //TODO: handle error
                }
            }
        }
    }

    private fun fetchCategoriesFromWishlistedProducts(selectedCategory: String = "All") {
        viewModelScope.launch {
            val products = uiState.value.wishlistedProducts
            val categories = mutableSetOf("All")
            categories.addAll(products.map { it.category.categoryTitle })
            if (categories.contains(selectedCategory)) {
                _uiState.update {
                    it.copy(
                        categories = categories,
                        selectedCategory = selectedCategory
                    )
                }
            } else {
                _uiState.update { it.copy(categories = categories, selectedCategory = "All") }
            }
            filterProductsByCategory(uiState.value.selectedCategory)
        }
    }

    fun handleUiAction(action: WishlistUiAction) {
        when (action) {
            is WishlistUiAction.OnCategoryClicked -> {
                filterProductsByCategory(action.category)
            }

            is WishlistUiAction.OnRemoverWishlistedProduct -> {
                removeWishlistedProduct(action.productId)
            }

            else -> Unit
        }
    }

    private fun filterProductsByCategory(category: String) {
        viewModelScope.launch {
            if (category == "All") {
                _uiState.update {
                    it.copy(
                        selectedCategory = category,
                        displayedProducts = uiState.value.wishlistedProducts
                    )
                }
            } else {
                val filteredProducts = uiState.value.wishlistedProducts.filter {
                    it.category.categoryTitle == category
                }
                _uiState.update {
                    it.copy(
                        selectedCategory = category,
                        displayedProducts = filteredProducts
                    )
                }
            }
        }
    }

    private fun removeWishlistedProduct(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepo.removeWishlistedProduct(
                productId = productId,
                userId = uiState.value.currentUserId
            ).onSuccess {
                val updatedWishlistedProducts =
                    uiState.value.wishlistedProducts.filter { it.id != productId }
                _uiState.update { it.copy(wishlistedProducts = updatedWishlistedProducts) }
                fetchCategoriesFromWishlistedProducts(selectedCategory = uiState.value.selectedCategory)
            }.onFailure { error ->
                Log.e(
                    "${this@WishlistViewModel::class.simpleName}:removeWishlistedProduct",
                    error.userMessage
                )
                //TODO: handle error
            }
        }
    }
}