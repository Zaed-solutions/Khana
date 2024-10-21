package org.zaed.khana.presentation.productdetails

import org.zaed.khana.data.model.Color
import org.zaed.khana.data.model.Product
import org.zaed.khana.data.model.User
import org.zaed.khana.data.util.Error
import org.zaed.khana.data.util.ProductResult

data class ProductDetailsUiState(
    val currentUser: User = User(),
    val productId: String = "",
    val isWishlisted: Boolean = false,
    val isAddedToCart: Boolean = false,
    val isLoading: Boolean = true,
    val product: Product = Product(),
    val selectedSize: String = "",
    val selectedColor: Color = Color(),
    val result: Error = ProductResult.IDLE
)
