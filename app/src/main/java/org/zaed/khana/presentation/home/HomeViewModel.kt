package org.zaed.khana.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.zaed.khana.data.repository.AdvertisementRepository
import org.zaed.khana.data.repository.CategoryRepository
import org.zaed.khana.data.repository.ProductRepository

class HomeViewModel(
    private val advertisementRepo: AdvertisementRepository,
    private val categoryRepo: CategoryRepository,
    private val productRepo: ProductRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()
    init{
        TODO("get currentUserId")
        TODO("get hasNewNotifications")
        fetchAdvertisements()
        fetchCategories()
        fetchFlashSaleEndTime()
        fetchLabels()
        fetchProducts()
        fetchWishlistedProductIds()
    }

    private fun fetchAdvertisements(){
        viewModelScope.launch {
            advertisementRepo.fetchAdvertisements().collect{ ads ->
                _uiState.update { it.copy(ads = ads) }
            }
        }
    }

    private fun fetchCategories(){
        viewModelScope.launch {
            categoryRepo.fetchCategories().collect{ categories ->
                _uiState.update { it.copy(categories = categories) }
            }
        }
    }

    private fun fetchFlashSaleEndTime(){
        viewModelScope.launch {
            val time = productRepo.fetchFlashSaleEndTime()
            _uiState.update { it.copy(flashSaleEndsAtEpochSeconds = time) }
        }
    }

    private fun fetchLabels(){
        viewModelScope.launch {
            productRepo.fetchLabels().collect{ labels ->
                _uiState.update { it.copy(labels = labels) }
            }
        }
    }

    private fun fetchProducts(){
        viewModelScope.launch {
            productRepo.fetchProductsByLabel(uiState.value.selectedLabel).collect{ products ->
                _uiState.update { it.copy(products = products) }
            }
        }
    }

    private fun fetchWishlistedProductIds(){
        viewModelScope.launch {
            productRepo.fetchWishlistedProductsIds(uiState.value.currentUserId).collect{ ids ->
                _uiState.update { it.copy(wishlistedProductsIds = ids) }
            }
        }
    }

    fun handleUiAction(action: HomeUiAction){
        when(action){
            is HomeUiAction.OnSelectLabel -> {
                updateSelectedLabel(action.label)
            }
            is HomeUiAction.OnWishlistProduct -> {
                wishlistProduct(action.productId)
            }
            else -> Unit
        }
    }
    private fun updateSelectedLabel(label: String){
        viewModelScope.launch {
            _uiState.update { it.copy(selectedLabel = label) }
            fetchProducts()
        }
    }
    private fun wishlistProduct(productId: String){
        viewModelScope.launch {
            if(uiState.value.wishlistedProductsIds.contains(productId)) {
                _uiState.update { it.copy(wishlistedProductsIds = it.wishlistedProductsIds.minusElement(productId)) }
                productRepo.removeWishlistedProduct(productId = productId, userId = uiState.value.currentUserId)
            } else {
                _uiState.update { it.copy(wishlistedProductsIds = it.wishlistedProductsIds.plus(productId)) }
                productRepo.addWishlistedProduct(productId = productId, userId = uiState.value.currentUserId)
            }
        }
    }
}