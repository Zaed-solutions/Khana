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
    val brand: String = BrandFilterOption.ALL.displayName,
    val gender: String = GenderFilterOption.ALL.displayName,
    val sortedBy: String = SortByFilterOption.LATEST.displayName,
    val minPrice: Int = 0,
    val maxPrice: Int = 9999,
    val reviews: String = ReviewsFilterOption.ALL.displayName
)
