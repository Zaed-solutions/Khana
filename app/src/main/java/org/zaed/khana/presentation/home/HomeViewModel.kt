package org.zaed.khana.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.zaed.khana.data.model.ProductFilter
import org.zaed.khana.data.repository.AuthenticationRepository
import org.zaed.khana.data.repository.AdvertisementRepository
import org.zaed.khana.data.repository.CategoryRepository
import org.zaed.khana.data.repository.ProductRepository
import org.zaed.khana.data.util.SortByFilterOption

class HomeViewModel(
    private val advertisementRepo: AdvertisementRepository,
    private val categoryRepo: CategoryRepository,
    private val productRepo: ProductRepository,
    private val authRepo: AuthenticationRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()
    fun init(filter: ProductFilter){
        _uiState.update { it.copy(filter = filter) }
        fetchCurrentUser()
        fetchAdvertisements()
        fetchCategories()
        fetchFlashSaleEndTime()
        fetchSorterByOptions()
    }

    private fun fetchCurrentUser(){
        viewModelScope.launch {
            authRepo.getSignedInUser().onSuccessWithData { user ->
                _uiState.update { it.copy(currentUser = user) }
                fetchWishlistedProductIds()
            }.onFailure {
                Log.e("HomeViewModel:fetchCurrentUser", it.userMessage)
            }
        }
    }

    private fun fetchAdvertisements(){
        viewModelScope.launch {
            advertisementRepo.fetchAdvertisements().collect{ result ->
                result.onSuccessWithData { ads ->
                    _uiState.update { it.copy(ads = ads) }
                }.onFailure { error ->
                    Log.e("HomeViewModel:fetchAdvertisement", error.userMessage)
                }
            }
        }
    }

    private fun fetchCategories(){
        viewModelScope.launch {
            categoryRepo.fetchCategories().collect{ result ->
                result.onSuccessWithData { categories ->
                    _uiState.update { it.copy(categories = categories) }
                }.onFailure { error ->
                    Log.e("HomeViewModel:fetchCategories", error.userMessage)
                }
            }
        }
    }

    private fun fetchFlashSaleEndTime(){
        viewModelScope.launch {
            val result = productRepo.fetchFlashSaleEndTime()
            result.onSuccessWithData { time ->
                _uiState.update { it.copy(flashSaleEndsAtEpochSeconds = time) }
            }.onFailure { error ->
                Log.e("HomeViewModel:fetchFlashSaleEndTime", error.userMessage)
            }
        }
    }

    private fun fetchSorterByOptions(){
        viewModelScope.launch {
            productRepo.fetchSortedByOptions().collect{ result ->
                result.onSuccessWithData { options ->
                    _uiState.update { it.copy(sorterByOptions = options) }
                    fetchProducts()
                }.onFailure { error ->
                    Log.e("HomeViewModel:fetchLabels", error.userMessage)
                }
            }
        }
    }

    private fun fetchProducts(){
        viewModelScope.launch {
            productRepo.fetchProductsByFilter(uiState.value.filter).collect{ result ->
                Log.d("HomeViewModel:fetchProducts", result.toString())
                result.onSuccessWithData { products ->
                    _uiState.update { it.copy(products = products) }
                }.onFailure { error ->
                    Log.e("HomeViewModel:fetchProducts", error.userMessage)
                }
            }
        }
    }

    private fun fetchWishlistedProductIds(){
        viewModelScope.launch {
            productRepo.fetchWishlistedProductsIds(uiState.value.currentUser.id).collect{ result ->
                result.onSuccessWithData { ids ->
                    _uiState.update { it.copy(wishlistedProductsIds = ids) }
                }.onFailure { error ->
                    Log.e("HomeViewModel:fetchWishlistedProductIds", error.userMessage)
                }
            }
        }
    }

    fun handleUiAction(action: HomeUiAction){
        when(action){
            is HomeUiAction.OnUpdateSortedByOption -> {
                updateSortedByOptions(action.sortedByOption)
            }
            is HomeUiAction.OnWishlistProduct -> {
                wishlistProduct(action.productId)
            }
            else -> Unit
        }
    }
    private fun updateSortedByOptions(sortedByOption: String){
        viewModelScope.launch {
            _uiState.update { it.copy(filter = it.filter.copy(sortedBy = SortByFilterOption.entries.first { it.displayName == sortedByOption })) }
            fetchProducts()
        }
    }
    private fun wishlistProduct(productId: String){
        viewModelScope.launch {
            if(uiState.value.wishlistedProductsIds.contains(productId)) {
                productRepo.removeWishlistedProduct(productId = productId, userId = uiState.value.currentUser.id).onSuccess {
                    _uiState.update { it.copy(wishlistedProductsIds = it.wishlistedProductsIds.minusElement(productId)) }
                }.onFailure { error ->
                    Log.e("HomeViewModel:wishlistProduct", error.userMessage)
                }
            } else {
                productRepo.addWishlistedProduct(productId = productId, userId = uiState.value.currentUser.id).onSuccess {
                    _uiState.update { it.copy(wishlistedProductsIds = it.wishlistedProductsIds.plus(productId)) }
                }.onFailure { error ->
                    Log.e("HomeViewModel:wishlistProduct", error.userMessage)
                }
            }
        }
    }
}