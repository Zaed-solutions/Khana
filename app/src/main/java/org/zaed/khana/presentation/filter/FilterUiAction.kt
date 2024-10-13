package org.zaed.khana.presentation.filter

import org.zaed.khana.data.model.MaximumPrice
import org.zaed.khana.data.model.MinimumPrice
import org.zaed.khana.data.util.BrandFilterOption
import org.zaed.khana.data.util.GenderFilterOption
import org.zaed.khana.data.util.ReviewsFilterOption
import org.zaed.khana.data.util.SortByFilterOption

sealed interface FilterUiAction {
    data object OnBackPressed : FilterUiAction
    data object OnApplyFilters : FilterUiAction
    data object OnResetFilters : FilterUiAction
    data class OnUpdateBrandFilter(val brand: BrandFilterOption) : FilterUiAction
    data class OnUpdateGenderFilter(val gender: GenderFilterOption) : FilterUiAction
    data class OnUpdateSortingOption(val sortingOption: SortByFilterOption) : FilterUiAction
    data class OnUpdatePriceRange(val range: Pair<MinimumPrice, MaximumPrice>) : FilterUiAction
    data class OnUpdateReviewsFilter(val reviews: ReviewsFilterOption) : FilterUiAction
}