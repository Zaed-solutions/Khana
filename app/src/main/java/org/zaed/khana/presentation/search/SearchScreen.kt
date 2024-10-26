package org.zaed.khana.presentation.search

import android.util.Log
import androidx.compose.animation.Crossfade
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
import androidx.compose.runtime.derivedStateOf
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
import org.zaed.khana.data.model.Product
import org.zaed.khana.presentation.search.components.RecentSearchesSection
import org.zaed.khana.presentation.search.components.SearchResultList
import org.zaed.khana.presentation.search.components.SearchTextField
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = koinViewModel(),
    onBackPressed: () -> Unit,
    onNavigateToProductDetails: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    SearchScreenContent(
        modifier = modifier,
        products = state.products,
        isLoadingProducts = state.isLoadingProducts,
        wishlistedProductsIds = state.wishlistedProductsIds,
        recentSearches = state.recentSearches,
        isLoadingRecentSearches = state.isLoadingRecentSearches,
        onAction = { action ->
            when (action) {
                is SearchUiAction.OnBackPressed -> {
                    onBackPressed()
                }

                is SearchUiAction.OnProductClicked -> {
                    onNavigateToProductDetails(action.productId)
                }

                else -> viewModel.handleUiAction(action)
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreenContent(
    modifier: Modifier = Modifier,
    products: List<Product>,
    isLoadingProducts: Boolean,
    wishlistedProductsIds: List<String>,
    recentSearches: List<String>,
    isLoadingRecentSearches: Boolean,
    onAction: (SearchUiAction) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val isSearching by remember {
        derivedStateOf { searchQuery.isNotBlank() }
    }
    Log.d("SearchScreenContent", "searchQuery: $searchQuery, isSearching: $isSearching")
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.search),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    OutlinedIconButton(
                        onClick = { onAction(SearchUiAction.OnBackPressed) },
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back button"
                        )
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
        ) {
            SearchTextField(
                modifier = Modifier.fillMaxWidth(),
                onSearchQueryChanged = { query ->
                    searchQuery = query
                    onAction(SearchUiAction.OnSearchQueryChanged(query))
                },
                onDoneClicked = { onAction(SearchUiAction.OnAddRecentSearchItem(searchQuery)) },
                searchQuery = searchQuery
            )
            Crossfade(targetState = isSearching, label = "Search Tabs") { state ->
                when {
                    state -> {
                        SearchResultList(
                            modifier = Modifier.fillMaxSize(),
                            isLoading = isLoadingProducts,
                            searchQuery = searchQuery,
                            products = products,
                            onProductClicked = { id -> onAction(SearchUiAction.OnProductClicked(id)) },
                            onWishlistProduct = { id ->
                                onAction(
                                    SearchUiAction.OnWishlistProductClicked(
                                        id
                                    )
                                )
                            },
                            wishlistedProducts = wishlistedProductsIds
                        )
                    }

                    else -> {
                        RecentSearchesSection(
                            items = recentSearches,
                            isLoading = isLoadingRecentSearches,
                            onItemClick = { query ->
                                searchQuery = query
                                onAction(SearchUiAction.OnSearchQueryChanged(query))
                            },
                            onDeleteItem = { query ->
                                onAction(
                                    SearchUiAction.OnDeleteRecentSearchClicked(
                                        query
                                    )
                                )
                            },
                            onClearAllRecentSearches = { onAction(SearchUiAction.OnClearRecentSearchesClicked) }
                        )
                    }
                }

            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun SearchScreenContentPreview() {
    KhanaTheme {
        SearchScreenContent(
            products = emptyList(),
            wishlistedProductsIds = emptyList(),
            recentSearches = listOf("Shoes", "Shirts", "Pants"),
            onAction = {},
            isLoadingProducts = true,
            isLoadingRecentSearches = true
        )
    }
}