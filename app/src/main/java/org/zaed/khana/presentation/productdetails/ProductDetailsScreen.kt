package org.zaed.khana.presentation.productdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.data.model.Color
import org.zaed.khana.presentation.home.components.ColorSelectionSection
import org.zaed.khana.presentation.productdetails.components.ImagesPreviewPager
import org.zaed.khana.presentation.productdetails.components.ProductDetailsBottomBar
import org.zaed.khana.presentation.productdetails.components.ProductDetailsTopBar
import org.zaed.khana.presentation.productdetails.components.ProductInformationSection
import org.zaed.khana.presentation.productdetails.components.SizeSelectionSection

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
        onAction = { action ->
            when(action){
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
        imagesUrls = state.product.previewImagesLinks
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
) {
    Scaffold(
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
                .fillMaxWidth()
        ) {
            ImagesPreviewPager(imagesUrls = imagesUrls)
            ProductInformationSection(
                title = title,
                description = description,
                category = category,
                rating = rating
            )
            SizeSelectionSection(
                availableSizes = availableSizes,
                selectedSize = selectedSize,
                onSelectSize = { size -> onAction(ProductDetailsUiAction.OnSelectSize(size)) }
            )
            ColorSelectionSection(
                availableColors = availableColors,
                selectedColor = selectedColor,
                onSelectColor = { hexColor -> onAction(ProductDetailsUiAction.OnSelectColor(hexColor)) }
            )
        }
    }
}



