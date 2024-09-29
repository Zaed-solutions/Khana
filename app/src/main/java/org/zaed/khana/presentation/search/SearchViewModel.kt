package org.zaed.khana.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.zaed.khana.data.repository.ProductRepository
import org.zaed.khana.data.repository.SearchRepository

class SearchViewModel(
    private val productRepo: ProductRepository,
    private val searchRepo: SearchRepository
) : ViewModel(){
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()
    init {
//        TODO("get currentUserId")
        fetchWishlistedProductIds()
        fetchRecentSearches()
    }

    private fun fetchWishlistedProductIds(){
        viewModelScope.launch {
            productRepo.fetchWishlistedProductsIds(uiState.value.currentUserId).collect{ result ->
                result.onSuccessWithData { ids ->
                    _uiState.update { it.copy(wishlistedProductsIds = ids) }
                }.onFailure { error ->
                    Log.e("SearchViewModel:fetchWishlistedProductIds", error.userMessage)
                }.onLoading {
                    //TODO: handle loading
                }
            }
        }
    }

    private fun fetchRecentSearches() {
        viewModelScope.launch {
            searchRepo.fetchRecentSearches().collect { result ->
                result.onSuccessWithData { searches ->
                    _uiState.update { it.copy(recentSearches = searches) }
                }.onFailure { error ->
                    Log.e("SearchViewModel:fetchRecentSearches", error.userMessage)
                }.onLoading {
                    //TODO: handle loading
                }
            }
        }
    }

    fun handleUiAction(action: SearchUiAction) {
        when (action) {
            SearchUiAction.OnClearRecentSearchesClicked -> clearRecentSearches()
            is SearchUiAction.OnDeleteRecentSearchClicked -> deleteRecentSearch(action.query)
            is SearchUiAction.OnSearchQueryChanged -> fetchSearchResult(action.query)
            is SearchUiAction.OnWishlistProductClicked -> wishlistProduct(action.productId)
            else -> Unit
        }
    }

    private fun clearRecentSearches(){
        viewModelScope.launch {
            searchRepo.clearRecentSearches().onSuccess {
                _uiState.update { it.copy(recentSearches = emptyList()) }
            }.onFailure { error ->
                Log.e("SearchViewModel:clearRecentSearches", error.userMessage)
            }
        }
    }

    private fun deleteRecentSearch(query: String){
        viewModelScope.launch {
            searchRepo.deleteRecentSearch(query).onSuccess {
                _uiState.update { it.copy(recentSearches = uiState.value.recentSearches.minusElement(query)) }
            }.onFailure { error ->
                Log.e("SearchViewModel:deleteRecentSearch", error.userMessage)
            }
        }
    }

    private fun fetchSearchResult(query: String){
        viewModelScope.launch {
            searchRepo.fetchSearchResult(query).collect { result ->
                result.onSuccessWithData { products ->
                    _uiState.update { it.copy(products = products) }
                }.onFailure { error ->
                    Log.e("SearchViewModel:fetchSearchResult", error.userMessage)
                }
            }
        }
    }

    private fun wishlistProduct(productId: String){
        viewModelScope.launch {
            if(uiState.value.wishlistedProductsIds.contains(productId)) {
                productRepo.removeWishlistedProduct(productId = productId, userId = uiState.value.currentUserId).onSuccess {
                    _uiState.update { it.copy(wishlistedProductsIds = it.wishlistedProductsIds.minusElement(productId)) }
                }.onFailure { error ->
                    Log.e("SearchViewModel:wishlistProduct", error.userMessage)
                }
            } else {
                productRepo.addWishlistedProduct(productId = productId, userId = uiState.value.currentUserId).onSuccess {
                    _uiState.update { it.copy(wishlistedProductsIds = it.wishlistedProductsIds.plus(productId)) }
                }.onFailure { error ->
                    Log.e("SearchViewModel:wishlistProduct", error.userMessage)
                }
            }
        }
    }
}