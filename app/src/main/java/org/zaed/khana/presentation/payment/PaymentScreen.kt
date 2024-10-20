package org.zaed.khana.presentation.payment

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.R
import org.zaed.khana.presentation.payment.components.PaymentMethodsSection

@Composable
fun PaymentScreen(
    modifier: Modifier = Modifier,
    viewModel: PaymentViewModel = koinViewModel(),
    onBackPressed: () -> Unit,
    onNavigateToHome: () -> Unit,
    orderId: String,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(true) {
        viewModel.init(orderId)
    }
    LaunchedEffect(state.isPaymentConfirmed) {
        if (state.isPaymentConfirmed) {
            snackbarHostState.showSnackbar("Payment Confirmed")
            delay(4000)
            onNavigateToHome()
        }
    }
    PaymentScreenContent(
        snackbarHostState = snackbarHostState,
        selectedMethod = state.selectedPaymentMethod,
        onAction = { action ->
            when (action) {
                PaymentUiAction.OnBackPressed -> onBackPressed()
                else -> viewModel.handleAction(action)
            }
        },

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreenContent(
    modifier: Modifier = Modifier,
    onAction: (PaymentUiAction) -> Unit,
    snackbarHostState: SnackbarHostState,
    selectedMethod: String,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.payment_methods))
                },
                navigationIcon = {
                    OutlinedIconButton(onClick = { onAction(PaymentUiAction.OnBackPressed) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp
            ) {
                Button(
                    onClick = { onAction(PaymentUiAction.OnContinueClicked) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Text(text = stringResource(R.string.continue_))
                }
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 32.dp, vertical = 8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            PaymentMethodsSection(
                title = stringResource(R.string.credit_debit_card),
            ) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    shadowElevation = 1.dp,
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    )
                ) {
                    ListItem(
                        headlineContent = {
                            Text(text = stringResource(R.string.add_card))
                        },
                        leadingContent = {
                            Icon(imageVector = Icons.Default.CreditCard, contentDescription = null)
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowForwardIos,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .graphicsLayer(alpha = 0.5f)
                    )
                }
            }
            PaymentMethodsSection(title = stringResource(R.string.more_payment_options)) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    shadowElevation = 1.dp,
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    )
                ) {
                    Column{

                        PaymentMethods.entries.forEach { method ->
                            ListItem(
                                leadingContent = {
                                    Icon(
                                        painter = painterResource(id = method.iconId),
                                        tint = Color.Unspecified,
                                        contentDescription = null
                                    )
                                },
                                headlineContent = {
                                    Text(text = stringResource(id = method.displayNameId))
                                },
                                trailingContent = {
                                    RadioButton(
                                        selected = method.name == selectedMethod,
                                        enabled = method.enabled,
                                        onClick = {
                                            onAction(PaymentUiAction.OnPaymentMethodSelected(method.name))
                                        }
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        0.5.dp,
                                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                                    )
                                    .graphicsLayer(alpha = if (method.enabled) 1f else 0.5f)
                            )
                        }
                    }
                }
            }
        }

    }
}

enum class PaymentMethods(
    @DrawableRes val iconId: Int,
    val enabled: Boolean,
    @StringRes val displayNameId: Int
) {
    CASH_ON_DELIVERY(R.drawable.cod, true, R.string.cash_on_delivery),
    PAYPAL(R.drawable.paypal, false, R.string.paypal),
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PaymentScreenPreview() {
    PaymentScreenContent(
        onAction = {},
        snackbarHostState = remember {
            SnackbarHostState()
        },
        selectedMethod = PaymentMethods.CASH_ON_DELIVERY.name
    )
}