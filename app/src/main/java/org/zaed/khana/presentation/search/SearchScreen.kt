package org.zaed.khana.presentation.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = koinViewModel(),
    onBackPressed: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
}

@Composable
private fun SearchScreenContent(modifier: Modifier = Modifier) {

}