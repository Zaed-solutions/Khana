package org.zaed.khana.presentation.productdetails

import org.zaed.khana.data.model.Color
import org.zaed.khana.data.model.Product

data class ProductDetailsUiState(
    val userId: String = "",
    val productId: String = "",
    val isWishlisted: Boolean = false,
    val product: Product = Product(),
    val selectedSize: String = "",
    val selectedColor: Color = Color(),
)
