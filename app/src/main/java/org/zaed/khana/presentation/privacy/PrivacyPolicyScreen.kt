package org.zaed.khana.presentation.privacy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.R
import org.zaed.khana.data.model.LegalInfo
import org.zaed.khana.presentation.privacy.components.LegalInfoSection

@Composable
fun PrivacyPolicyScreen(
    modifier: Modifier = Modifier,
    viewModel: PrivacyPolicyViewModel = koinViewModel(),
    onBackPressed: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    PrivacyPolicyScreenContent(
        modifier = modifier,
        legalInfo = state.legalInfo,
        onAction = { action ->
            when(action){
                PrivacyPolicyUiAction.OnBackPressed -> onBackPressed()
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PrivacyPolicyScreenContent(
    modifier: Modifier = Modifier,
    legalInfo: LegalInfo,
    onAction: (PrivacyPolicyUiAction) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.privacy_policy),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    OutlinedIconButton(onClick = { onAction(PrivacyPolicyUiAction.OnBackPressed) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LegalInfoSection(
                title = stringResource(id = R.string.privacy_policy),
                subtitle = legalInfo.privacyPolicy
            )
            LegalInfoSection(
                title = stringResource(R.string.terms_and_conditions),
                subtitle = legalInfo.termsAndConditions
            )
        }
    }
}