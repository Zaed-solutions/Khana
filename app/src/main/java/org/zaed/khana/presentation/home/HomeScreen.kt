package org.zaed.khana.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.zaed.khana.presentation.home.components.AdvertisementSection
import org.zaed.khana.presentation.home.components.CategoriesSection
import org.zaed.khana.presentation.home.components.FlashSaleSection
import org.zaed.khana.presentation.home.components.LabelFilterSection
import org.zaed.khana.presentation.home.components.LocationAndNotificationsSection
import org.zaed.khana.presentation.home.components.ProductItems
import org.zaed.khana.presentation.home.components.SearchAndFiltersSection

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()

}

@Composable
private fun HomeContent(
    searchQuery: String,
    isSearching: Boolean,
    modifier: Modifier = Modifier,
) {
    Scaffold (

    ) { paddingValues ->
        LazyColumn (
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(all = 16.dp)
        ){
            //location and notification part
            item {
                LocationAndNotificationsSection()
            }
            //search and filters part
            item {
                SearchAndFiltersSection(
                    onFiltersButtonClicked = {/*TODO*/},
                    onSearchQueryChanged = {/*TODO*/},
                    onSearch = {/*TODO*/},
                    searchQuery = searchQuery,
                    isSearching = isSearching,
                    onChangeSearchingStatus = {/*TODO*/}
                )
            }
            //ads pager
            item{
                AdvertisementSection(ads = emptyList())
            }
            //categories
            item{
                CategoriesSection(emptyList())
            }
            //flash sale (optional)
            item{
                FlashSaleSection(0)
            }
            //items
            item{
                LabelFilterSection(
                    labels = emptyList(),
                    selectedLabel = "",
                    onSelectLabel = {}
                )
            }
            item{
                ProductItems()
            }
        }
    }
}