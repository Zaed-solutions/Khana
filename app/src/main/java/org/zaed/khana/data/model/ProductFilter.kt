package org.zaed.khana.data.model

import kotlinx.serialization.Serializable
import org.zaed.khana.data.util.BrandFilterOption
import org.zaed.khana.data.util.GenderFilterOption
import org.zaed.khana.data.util.ReviewsFilterOption
import org.zaed.khana.data.util.SortByFilterOption

typealias MinimumPrice = Int
typealias MaximumPrice = Int

@Serializable
data class ProductFilter(
    val brand: BrandFilterOption = BrandFilterOption.ALL,
    val gender: GenderFilterOption = GenderFilterOption.ALL,
    val sortedBy: SortByFilterOption = SortByFilterOption.LATEST,
    val priceRange: Pair<MinimumPrice, MaximumPrice> = Pair(0, 9999),
    val reviews: ReviewsFilterOption = ReviewsFilterOption.ALL
)
