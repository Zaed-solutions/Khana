package org.zaed.khana.presentation.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.R
import org.zaed.khana.data.model.ProductFilter
import org.zaed.khana.data.util.BrandFilterOption
import org.zaed.khana.data.util.GenderFilterOption
import org.zaed.khana.data.util.ReviewsFilterOption
import org.zaed.khana.data.util.SortByFilterOption
import org.zaed.khana.presentation.filter.component.BrandsFilterContent
import org.zaed.khana.presentation.filter.component.FilterBottomBar
import org.zaed.khana.presentation.filter.component.FilterSection
import org.zaed.khana.presentation.filter.component.GenderFilterContent
import org.zaed.khana.presentation.filter.component.PricingRangeFilterContent
import org.zaed.khana.presentation.filter.component.ReviewsFilterContent
import org.zaed.khana.presentation.filter.component.SortByFilterContent

@Composable
fun FilterScreen(
    modifier: Modifier = Modifier,
    viewModel: FilterViewModel = koinViewModel(),
    initialFilter: ProductFilter,
    onBackPressed: () -> Unit,
    onNavigateToHome: (ProductFilter) -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect (true){
        viewModel.init(initialFilter)
    }
    FilterScreenContent(modifier = modifier, filter = state.productFilter) { action ->
        when (action) {
            FilterUiAction.OnApplyFilters -> onNavigateToHome(state.productFilter)
            FilterUiAction.OnBackPressed -> onBackPressed()
            else -> viewModel.handleUiAction(action)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterScreenContent(
    modifier: Modifier = Modifier,
    filter: ProductFilter,
    onAction: (FilterUiAction) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.filter),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onAction(FilterUiAction.OnBackPressed) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {
            FilterBottomBar(
                onApplyFilters = { onAction(FilterUiAction.OnApplyFilters) },
                onResetFilters = { onAction(FilterUiAction.OnResetFilters) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            FilterSection(title = stringResource(R.string.brands)) {
                BrandsFilterContent(selectedBrand = BrandFilterOption.entries.first{it.displayName == filter.brand}) {
                    onAction(FilterUiAction.OnUpdateBrandFilter(it))
                }
            }
            FilterSection(title = stringResource(R.string.gender)) {
                GenderFilterContent(selectedGender = GenderFilterOption.entries.first{it.displayName == filter.gender}) {
                    onAction(FilterUiAction.OnUpdateGenderFilter(it))
                }
            }

            FilterSection(title = stringResource(R.string.sort_by)) {
                SortByFilterContent(selectedSortingOption = SortByFilterOption.entries.first{it.displayName == filter.sortedBy}) {
                    onAction(FilterUiAction.OnUpdateSortingOption(it))
                }
            }
            FilterSection(title = stringResource(R.string.pricing_range)) {
                PricingRangeFilterContent(priceRange = Pair(filter.minPrice, filter.maxPrice)) {
                    onAction(FilterUiAction.OnUpdatePriceRange(it))
                }
            }
            FilterSection(title = stringResource(R.string.reviews)) {
                ReviewsFilterContent(selectedReviewsOption = ReviewsFilterOption.entries.first{it.displayName == filter.reviews}) {
                    onAction(FilterUiAction.OnUpdateReviewsFilter(it))
                }
            }
        }
    }
}