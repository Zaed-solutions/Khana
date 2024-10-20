package org.zaed.khana.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.R
import org.zaed.khana.presentation.settings.components.ConfirmAccountDeletionDialog
import org.zaed.khana.presentation.settings.components.SettingsOptions
import org.zaed.khana.presentation.settings.components.VerifyPasswordDialog
import org.zaed.khana.presentation.settings.components.VisibleDialogOptions
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel(),
    onBackPressed: () -> Unit,
    onNavigateToPasswordManager: () -> Unit,
    onNavigateToNotificationSettings: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var visibleDialog by remember {
        mutableStateOf(VisibleDialogOptions.NONE)
    }
    LaunchedEffect(state.isWrongPassword) {
        visibleDialog = VisibleDialogOptions.VERIFY_PASSWORD
    }
    LaunchedEffect(state.isAccountDeleted) {
        onNavigateToLogin()
    }
    SettingsScreenContent(
        modifier = modifier,
        visibleDialog = visibleDialog,
        onAction = { action ->
            when (action) {
                SettingsUiAction.OnBackPressed -> onBackPressed()
                SettingsUiAction.OnPasswordManagerClicked -> onNavigateToPasswordManager()
                SettingsUiAction.OnCancelDeleteAccountClicked -> {
                    visibleDialog = VisibleDialogOptions.NONE
                    viewModel.handleAction(SettingsUiAction.OnCancelDeleteAccountClicked)
                }

                SettingsUiAction.OnConfirmDeleteAccountClicked -> {
                    visibleDialog = VisibleDialogOptions.VERIFY_PASSWORD
                }

                SettingsUiAction.OnDeleteAccountClicked -> {
                    visibleDialog = VisibleDialogOptions.CONFIRM_ACCOUNT_DELETION
                }

                SettingsUiAction.OnNotificationsSettingClicked -> onNavigateToNotificationSettings()
                else -> viewModel.handleAction(action)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsScreenContent(
    modifier: Modifier = Modifier,
    onAction: (SettingsUiAction) -> Unit,
    visibleDialog: VisibleDialogOptions,
    isWrongPassword: Boolean = false
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.settings))
                },
                navigationIcon = {
                    OutlinedIconButton(onClick = { onAction(SettingsUiAction.OnBackPressed) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SettingsOptions.entries.forEach { option ->
                ListItem(
                    leadingContent = {
                        Icon(
                            imageVector = option.displayIcon,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = null
                        )
                    },
                    headlineContent = {
                        Text(text = stringResource(option.displayNameId))
                    },
                    trailingContent = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onAction(option.action)
                        }
                )
                if (option != SettingsOptions.DELETE_ACCOUNT) {
                    HorizontalDivider(thickness = 0.5.dp)
                }
            }
        }
        if (visibleDialog != VisibleDialogOptions.NONE) {
            ModalBottomSheet(
                onDismissRequest = { onAction(SettingsUiAction.OnCancelDeleteAccountClicked) },
                sheetState = rememberModalBottomSheetState()
            ) {
                when (visibleDialog) {
                    VisibleDialogOptions.CONFIRM_ACCOUNT_DELETION -> {
                        ConfirmAccountDeletionDialog(
                            onDismiss = { onAction(SettingsUiAction.OnCancelDeleteAccountClicked) },
                            onConfirm = { onAction(SettingsUiAction.OnConfirmDeleteAccountClicked) }
                        )
                    }

                    VisibleDialogOptions.VERIFY_PASSWORD -> {
                        VerifyPasswordDialog(
                            onDismiss = { onAction(SettingsUiAction.OnCancelDeleteAccountClicked) },
                            onContinue = { password ->
                                onAction(
                                    SettingsUiAction.OnVerifyPasswordAndDeleteAccount(
                                        password
                                    )
                                )
                            },
                            isWrongPassword = isWrongPassword
                        )
                    }

                    else -> Unit
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun SettingsContentPreview() {
    KhanaTheme {
        SettingsScreenContent(
            onAction = {},
            visibleDialog = VisibleDialogOptions.CONFIRM_ACCOUNT_DELETION
        )
    }
}













