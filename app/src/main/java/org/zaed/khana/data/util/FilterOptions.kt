package org.zaed.khana.data.util

import kotlinx.serialization.Serializable

@Serializable
enum class BrandFilterOption(val displayName: String) {
    ALL("All"),
    NIKE("Nike"),
    ADIDAS("Adidas"),
    PUMA("Puma"),
    REEBOK("Reebok"),
    JORDAN("Jordan")
}

@Serializable
enum class SortByFilterOption(val displayName: String) {
    LATEST("Latest"),
    POPULAR("Popular"),
    PRICE_LOW_TO_HIGH("Price - Low to High"),
    PRICE_HIGH_TO_LOW("Price - High to Low")
}

@Serializable
enum class ReviewsFilterOption(val displayName: String, val minRating: Float) {
    ALL("All", 0f),
    FOUR_STARS_AND_ABOVE("4 and above", 4f),
    THREE_STARS_AND_ABOVE("3 and above", 3f),
    TWO_STARS_AND_ABOVE("2 and above", 2f),
    ONE_STAR_AND_ABOVE("1 and above", 1f)
}


@Serializable
enum class GenderFilterOption(val displayName: String) {
    ALL("All"),
    MEN("Men"),
    WOMEN("Women"),
}