package org.zaed.khana.presentation.profile

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.R
import org.zaed.khana.presentation.profile.components.ProfileHeader
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = koinViewModel(),
    onBackPressed: () -> Unit,
    onNavigateToHelpCenter: () -> Unit,
    onNavigateToMyOrders: () -> Unit,
    onNavigateToPaymentMethods: () -> Unit,
    onNavigateToPrivacyPolicy: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = state.isLoggedOut) {
        if(state.isLoggedOut){
            onNavigateToLogin()
        }
    }
    ProfileScreenContent(
        modifier = modifier,
        name = state.currentUser.firstName+" "+state.currentUser.lastName,
        avatarUrl = state.currentUser.avatar,
        avatarUri = null,
        onAction = { action ->
            when(action) {
                ProfileUiAction.OnBackPressed -> onBackPressed()
                ProfileUiAction.OnHelpCenterClicked -> onNavigateToHelpCenter()
                ProfileUiAction.OnMyOrdersClicked -> onNavigateToMyOrders()
                ProfileUiAction.OnPaymentMethodsClicked -> onNavigateToPaymentMethods()
                ProfileUiAction.OnPrivacyPolicyClicked -> onNavigateToPrivacyPolicy()
                ProfileUiAction.OnSettingsClicked -> onNavigateToSettings()
                else -> viewModel.handleUiAction(action)
            }
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    name: String,
    avatarUrl: String,
    avatarUri: Uri?,
    onAction: (ProfileUiAction) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.profile))
                },
                navigationIcon = {
                    OutlinedIconButton(onClick = { onAction(ProfileUiAction.OnBackPressed) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ProfileHeader(
                modifier = Modifier.padding(bottom = 16.dp),
                name = name,
                avatarUrl = avatarUrl,
                avatarUri = avatarUri,
                onAvatarPicked = { uri -> onAction(ProfileUiAction.OnAvatarPicked(uri)) }
            )
            ProfileScreenOptions.entries.forEach { option ->
                ListItem(
                    headlineContent = {
                        Text(text = stringResource(option.titleId))
                    },
                    leadingContent = {
                        Icon(imageVector = option.icon, contentDescription = null)
                    },
                    trailingContent = {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = null )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { if(option != ProfileScreenOptions.PAYMENT_METHODS) onAction(option.action) }
                        .graphicsLayer(alpha = if(option == ProfileScreenOptions.PAYMENT_METHODS) 0.12f else 1f)
                )
                if(option != ProfileScreenOptions.LOGOUT) {
                    HorizontalDivider(thickness = 0.5.dp)
                }
            }
        }

    }
}

enum class ProfileScreenOptions(val titleId: Int, val icon: ImageVector, val action: ProfileUiAction) {
    PAYMENT_METHODS(R.string.payment_methods, Icons.Default.CreditCard, ProfileUiAction.OnPaymentMethodsClicked),
    MY_ORDERS(R.string.my_orders, Icons.AutoMirrored.Filled.Assignment, ProfileUiAction.OnMyOrdersClicked),
    SETTINGS(R.string.settings, Icons.Default.Settings, ProfileUiAction.OnSettingsClicked),
    HELP_CENTER(R.string.help_center, Icons.Default.Info, ProfileUiAction.OnHelpCenterClicked),
    PRIVACY_POLICY(R.string.privacy_policy, Icons.Default.Lock, ProfileUiAction.OnPrivacyPolicyClicked),
    LOGOUT(R.string.log_out, Icons.AutoMirrored.Filled.Logout, ProfileUiAction.OnLogoutClicked),
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ProfileScreenContentPreview() {
    KhanaTheme {
        ProfileScreenContent(name = "Muhammed Edrees", avatarUrl = "", avatarUri = null) {

        }
    }
}