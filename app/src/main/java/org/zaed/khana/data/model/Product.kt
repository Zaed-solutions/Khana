package org.zaed.khana.data.model

data class Product(
    val id: String = "",
    val name: String = "",
    val rating: Float = 0f,
    val thumbnailImageLink: String = "",
    val previewImagesLinks: List<String> = emptyList(),
    val category: Category = Category(),
    val details: String = "",
    val availableSizes: List<String> = emptyList(),
    val availableHexColors: List<String> = emptyList(),
    val basePrice: Float = 0f,
    val isAvailable: Boolean = true,
)
