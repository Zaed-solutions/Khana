package org.zaed.khana.presentation.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.zaed.khana.data.model.MaximumPrice
import org.zaed.khana.data.model.MinimumPrice
import org.zaed.khana.data.util.BrandFilterOption
import org.zaed.khana.data.util.GenderFilterOption
import org.zaed.khana.data.util.ReviewsFilterOption
import org.zaed.khana.data.util.SortByFilterOption

class FilterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(FilterUiState())
    val uiState = _uiState.asStateFlow()

    fun handleUiAction(action: FilterUiAction) {
        when (action) {
            FilterUiAction.OnResetFilters -> resetFilters()
            is FilterUiAction.OnUpdateBrandFilter -> updateBrandFilter(action.brand)
            is FilterUiAction.OnUpdateGenderFilter -> updateGenderFilter(action.gender)
            is FilterUiAction.OnUpdatePriceRange -> updatePricingRange(action.range)
            is FilterUiAction.OnUpdateReviewsFilter -> updateReviewsFilter(action.reviews)
            is FilterUiAction.OnUpdateSortingOption -> updateSortedByFilter(action.sortingOption)
            else -> Unit
        }
    }

    private fun updateSortedByFilter(sortingOption: SortByFilterOption) {
        viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(productFilter = _uiState.value.productFilter.copy(sortedBy = sortingOption.displayName))
        }
    }

    private fun updateReviewsFilter(reviews: ReviewsFilterOption) {
        viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(productFilter = _uiState.value.productFilter.copy(reviews = reviews.displayName))
        }
    }

    private fun updatePricingRange(range: Pair<MinimumPrice, MaximumPrice>) {
        viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(productFilter = _uiState.value.productFilter.copy(minPrice = range.first, maxPrice = range.second))
        }
    }

    private fun updateGenderFilter(gender: GenderFilterOption) {
        viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(productFilter = _uiState.value.productFilter.copy(gender = gender.displayName))
        }
    }

    private fun updateBrandFilter(brand: BrandFilterOption) {
        viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(productFilter = _uiState.value.productFilter.copy(brand = brand.displayName))
        }
    }

    private fun resetFilters() {
        viewModelScope.launch {
            _uiState.value = FilterUiState()
        }
    }
}