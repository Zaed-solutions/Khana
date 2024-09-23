package org.zaed.khana.presentation.productdetails

import org.zaed.khana.data.model.Color
import org.zaed.khana.data.model.Product
import org.zaed.khana.data.util.ProductResult

data class ProductDetailsUiState(
    val currentUserId: String = "",
    val productId: String = "",
    val isWishlisted: Boolean = false,
    val product: Product = Product(),
    val selectedSize: String = "",
    val selectedColor: Color = Color(),
    val result: ProductResult = ProductResult.IDLE
)
