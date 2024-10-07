package org.zaed.khana.presentation.productdetails

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.data.model.Color
import org.zaed.khana.data.util.ProductResult
import org.zaed.khana.data.util.isNotIdle
import org.zaed.khana.presentation.home.components.ColorSelectionSection
import org.zaed.khana.presentation.productdetails.components.ImagesPreviewPager
import org.zaed.khana.presentation.productdetails.components.ProductDetailsBottomBar
import org.zaed.khana.presentation.productdetails.components.ProductDetailsTopBar
import org.zaed.khana.presentation.productdetails.components.ProductInformationSection
import org.zaed.khana.presentation.productdetails.components.SizeSelectionSection
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun ProductDetailsScreen(
    modifier: Modifier = Modifier,
    productId: String,
    onBackPressed: () -> Unit,
    viewModel: ProductDetailsViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.init(productId)
    }
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    ProductDetailsScreenContent(
        modifier = modifier,
        onAction = { action ->
            when (action) {
                is ProductDetailsUiAction.OnBackPressed -> onBackPressed()
                else -> viewModel.handleUiAction(action)
            }
        },
        isWishlisted = state.isWishlisted,
        title = state.product.name,
        category = state.product.category.categoryTitle,
        rating = state.product.rating,
        description = state.product.description,
        availableSizes = state.product.availableSizes,
        selectedSize = state.selectedSize,
        availableColors = state.product.availableColors,
        selectedColor = state.selectedColor,
        price = state.product.basePrice,
        imagesUrls = state.product.previewImagesLinks,
        actionResult = state.result
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductDetailsScreenContent(
    modifier: Modifier = Modifier,
    onAction: (ProductDetailsUiAction) -> Unit,
    isWishlisted: Boolean,
    title: String,
    category: String,
    rating: Float,
    description: String,
    availableSizes: List<String>,
    selectedSize: String,
    imagesUrls: List<String>,
    availableColors: List<Color>,
    selectedColor: Color,
    price: Float,
    actionResult: ProductResult
) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = actionResult) {
        if (actionResult.isNotIdle()) {
            snackbarHostState.showSnackbar(
                message = actionResult.userMessage,
                actionLabel = when (actionResult) {
                    ProductResult.ADD_ITEM_TO_CART_FAILED,
                    ProductResult.ADD_WISHLISTED_PRODUCTS_FAILED -> "Retry"
                    else -> null
                }
            ).run {
                when (this) {
                    SnackbarResult.Dismissed -> {}
                    SnackbarResult.ActionPerformed -> {
                        when (actionResult) {
                            ProductResult.ADD_ITEM_TO_CART_FAILED -> {
                                onAction(
                                    ProductDetailsUiAction.OnAddToCartClicked
                                )
                            }

                            ProductResult.ADD_WISHLISTED_PRODUCTS_FAILED -> {
                                onAction(ProductDetailsUiAction.OnWishlistProduct)
                            }

                            else -> Unit
                        }
                    }
                }
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            ProductDetailsTopBar(
                onBackPressed = { onAction(ProductDetailsUiAction.OnBackPressed) },
                onWishlistProduct = { onAction(ProductDetailsUiAction.OnWishlistProduct) },
                isWishlisted = isWishlisted
            )
        },
        bottomBar = {
            ProductDetailsBottomBar(
                price = price,
                onAddToCartClicked = { onAction(ProductDetailsUiAction.OnAddToCartClicked) }
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth(),
        ) {
            ImagesPreviewPager(imagesUrls = imagesUrls)
            ProductInformationSection(
                title = title,
                description = description,
                category = category,
                rating = rating,
                modifier = Modifier.padding(horizontal = 16.dp).padding(top = 16.dp)
            )
            SizeSelectionSection(
                availableSizes = availableSizes,
                selectedSize = selectedSize,
                onSelectSize = { size -> onAction(ProductDetailsUiAction.OnSelectSize(size)) },
                modifier = Modifier.padding(horizontal = 16.dp).padding(top = 8.dp)
            )
            ColorSelectionSection(
                availableColors = availableColors,
                selectedColor = selectedColor,
                onSelectColor = { hexColor -> onAction(ProductDetailsUiAction.OnSelectColor(hexColor)) },
                modifier = Modifier.padding(horizontal = 16.dp).padding(top = 8.dp)
            )
        }
    }
}


@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun ProductDetailsScreenPreview() {
    KhanaTheme {
        ProductDetailsScreenContent(
            onAction = {  },
            isWishlisted = true,
            title = "Test Product",
            category = "Test Category",
            rating = 4.7f,
            description = "Test Description, Test Description, Test Description, Test Description, Test Description, Test Description, Test Description, Test Description",
            availableSizes = listOf("S", "M", "L", "XL", "XXL"),
            selectedSize = "L",
            imagesUrls = listOf("https://www.test.com/image1.jpg", "https://www.test.com/image2.jpg", "https://www.test.com/image3.jpg", ),
            availableColors = listOf(Color("Black", "#000000"), Color("White", "#ffffff"), Color("Red", "#ff0000")),
            selectedColor = Color("Black", "#000000"),
            price = 183.5f,
            actionResult = ProductResult.IDLE
        )
    }
}
