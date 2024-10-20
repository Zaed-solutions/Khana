package org.zaed.khana.presentation.passwordmanager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.R
import org.zaed.khana.presentation.passwordmanager.component.PasswordManagerTextField
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun PasswordManagerScreen(
    modifier: Modifier = Modifier,
    viewModel: PasswordManagerViewModel = koinViewModel(),
    onBackPressed: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(state.isPasswordChanged) {
        if (state.isPasswordChanged) {
            onBackPressed()
        }
    }
    PasswordManagerScreenContent(
        modifier = modifier,
        isCurrentPasswordWrong = state.isCurrentPasswordWrong,
        isNotMatchingPasswords = state.isNotMatchingPasswords,
        onAction = { action ->
            when (action) {
                PasswordManagerUiAction.OnBackPressed -> onBackPressed()
                else -> viewModel.handleAction(action)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordManagerScreenContent(
    modifier: Modifier = Modifier,
    onAction: (PasswordManagerUiAction) -> Unit,
    isCurrentPasswordWrong: Boolean,
    isNotMatchingPasswords: Boolean
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.password_manager))
                },
                navigationIcon = {
                    OutlinedIconButton(onClick = { onAction(PasswordManagerUiAction.OnBackPressed) }) {
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
                    onClick = { onAction(PasswordManagerUiAction.OnChangePasswordClicked) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Text(text = stringResource(R.string.change_password))
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PasswordManagerTextField(
                label = stringResource(R.string.current_password),
                onValueChanged = {
                    onAction(PasswordManagerUiAction.OnCurrentPasswordChanged(it))
                },
                isError = isCurrentPasswordWrong,
                errorMessage = stringResource(id = R.string.wrong_password)
            )
            PasswordManagerTextField(
                label = stringResource(R.string.new_password),
                onValueChanged = {
                    onAction(PasswordManagerUiAction.OnNewPasswordChanged(it))
                },
                isError = isNotMatchingPasswords,
                errorMessage = stringResource(R.string.passwords_do_not_match)
            )
            PasswordManagerTextField(
                label = stringResource(R.string.confirm_new_password),
                onValueChanged = {
                    onAction(PasswordManagerUiAction.OnConfirmPasswordChanged(it))
                },
                isError = isNotMatchingPasswords,
                errorMessage = stringResource(id = R.string.passwords_do_not_match)
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PasswordManagerContentPreview() {
    KhanaTheme {
        PasswordManagerScreenContent(
            onAction = {},
            isCurrentPasswordWrong = false,
            isNotMatchingPasswords = true
        )
    }
}