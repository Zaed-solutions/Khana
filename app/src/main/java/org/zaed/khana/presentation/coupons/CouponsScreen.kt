package org.zaed.khana.presentation.coupons

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.R
import org.zaed.khana.data.model.Coupon
import org.zaed.khana.data.util.CartResult
import org.zaed.khana.data.util.ProductResult
import org.zaed.khana.data.util.userMessage
import org.zaed.khana.presentation.coupons.components.CouponItem
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun CouponsScreen(
    modifier: Modifier = Modifier,
    viewModel: CouponsViewModel = koinViewModel(),
    onBackPressed: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    CouponsScreenContent(
        modifier = modifier,
        coupons = state.coupons,
        onAction = { action ->
            when (action) {
                CouponsUiAction.OnBackPressed -> onBackPressed()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun CouponsScreenContent(
    modifier: Modifier = Modifier,
    coupons: List<Coupon>,
    onAction: (CouponsUiAction) -> Unit,
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.coupons)) },
                navigationIcon = {
                    IconButton(onClick = { onAction(CouponsUiAction.OnBackPressed) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            item {
                Text(text = stringResource(R.string.best_offers_for_you), style = MaterialTheme.typography.headlineSmall)
            }
            items(coupons.size) { index ->
                val coupon = coupons[index]
                CouponItem(
                    coupon = coupon,
                    onCopyCouponCode = { code ->
                        clipboardManager.setText(AnnotatedString(code))
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Copied coupon code to clipboard!",
                                withDismissAction = true
                            )
                        }
                    }
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun CouponScreenContentPreview() {
    val coupons = listOf(
        Coupon(
            title = "WELCOME200",
            description = "GET 50% OFF for combo",
            code = "WELCOME200",
            discountPercentage = 20f,
            minAmount = 28f
        ),
        Coupon(
            title = "WELCOME300",
            description = "GET 36% OFF for combo",
            code = "WELCOME200",
            discountPercentage = 20f,
            minAmount = 36f
        ),
        Coupon(
            title = "WELCOME400",
            description = "GET 20% OFF for combo",
            code = "WELCOME200",
            discountPercentage = 20f,
            minAmount = 2f
        ),
        Coupon(
            title = "WELCOME200",
            description = "GET 50% OFF for combo",
            code = "WELCOME200",
            discountPercentage = 20f,
            minAmount = 28f
        ),
        Coupon(
            title = "WELCOME300",
            description = "GET 36% OFF for combo",
            code = "WELCOME200",
            discountPercentage = 20f,
            minAmount = 36f
        ),
        Coupon(
            title = "WELCOME400",
            description = "GET 20% OFF for combo",
            code = "WELCOME200",
            discountPercentage = 20f,
            minAmount = 2f
        ),
    )
    KhanaTheme {
        CouponsScreenContent(coupons = coupons) {
            
        }
    }
}