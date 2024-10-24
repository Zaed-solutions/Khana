package org.zaed.khana.presentation.leavereview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.R
import org.zaed.khana.data.model.CartItem
import org.zaed.khana.presentation.leavereview.components.LeaveReviewBottomBar
import org.zaed.khana.presentation.leavereview.components.LeaveReviewDetailsSection
import org.zaed.khana.presentation.leavereview.components.LeaveReviewRatingSection
import org.zaed.khana.presentation.myorders.components.PlacedOrderItem
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun LeaveReviewScreen(
    modifier: Modifier = Modifier,
    viewModel: LeaveReviewViewModel = koinViewModel(),
    onBackPressed: () -> Unit,
    orderId: String,
    cartItemId: String,
) {
    LaunchedEffect(true) {
        viewModel.init(orderId, cartItemId)
    }
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = state.isReviewSubmitted) {
        if (state.isReviewSubmitted) {
            onBackPressed()
        }
    }
    LeaveReviewScreenContent(
        modifier = modifier,
        item = state.item,
        rating = state.rating,
        onAction = { action ->
            when (action) {
                LeaveReviewUiAction.OnBackPressed -> {
                    onBackPressed()
                }

                LeaveReviewUiAction.OnCancelClicked -> {
                    onBackPressed()
                }

                else -> viewModel.handleUiAction(action)
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LeaveReviewScreenContent(
    modifier: Modifier = Modifier,
    item: CartItem,
    rating: Int,
    onAction: (LeaveReviewUiAction) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.leave_review),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    OutlinedIconButton(onClick = { onAction(LeaveReviewUiAction.OnBackPressed) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {
            LeaveReviewBottomBar(
                onSubmitClicked = { onAction(LeaveReviewUiAction.OnSubmitClicked) },
                onCancelClicked = { onAction(LeaveReviewUiAction.OnCancelClicked) }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PlacedOrderItem(
                modifier = Modifier.padding(top = 16.dp),
                thumbnailUrl = item.productThumbnail,
                title = item.productColor.name + " " + item.productName,
                quantity = item.quantity,
                size = item.productSize,
                price = item.productBasePrice,
                buttonText = stringResource(R.string.reorder),
                onButtonClicked = { onAction(LeaveReviewUiAction.OnReorderClicked) }
            )
            Text(
                text = stringResource(R.string.how_is_your_order),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 24.dp)
            )
            LeaveReviewRatingSection(
                rating = rating,
                onRatingChanged = { onAction(LeaveReviewUiAction.OnRatingChanged(it)) }
            )
            LeaveReviewDetailsSection {
                onAction(LeaveReviewUiAction.OnReviewChanged(it))
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun LeaveReviewScreenContentPreview() {
    val item = CartItem(productName = "Product Name")
    KhanaTheme {
        LeaveReviewScreenContent(item = item, rating = 3) {

        }
    }
}