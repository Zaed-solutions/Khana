package org.zaed.khana.ui.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.R
import org.zaed.khana.ui.theme.KhanaTheme

@Composable
fun SearchAndFiltersSection(
    modifier: Modifier = Modifier,
    onFiltersButtonClicked: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
    searchQuery: String,
    isSearching: Boolean,
    onChangeSearchingStatus: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchTextField(
            modifier = Modifier.weight(1f),
            onSearchQueryChanged = onSearchQueryChanged,
            onSearch = onSearch,
            searchQuery = searchQuery,
            isSearching = isSearching,
            onChangeSearchingStatus = onChangeSearchingStatus
        )
        IconButton(
            modifier = Modifier
                .padding(start = 8.dp)
                .size(52.dp),
            onClick = onFiltersButtonClicked,
            colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                imageVector = Icons.Default.Tune,
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )

        }
    }
}

@Composable
private fun SearchTextField(
    modifier: Modifier = Modifier,
    onSearchQueryChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
    searchQuery: String,
    isSearching: Boolean,
    onChangeSearchingStatus: (Boolean) -> Unit
) {
    val focusManager = LocalFocusManager.current
    if (!isSearching) {
        focusManager.clearFocus(true)
    }
    OutlinedTextField(
        modifier = modifier
            .padding(vertical = 8.dp)
            .onFocusChanged { state ->
                if (state.isFocused) {
                    onChangeSearchingStatus(true)
                }
            },
        placeholder = {
            Text(stringResource(id = R.string.search))
        },
        textStyle = MaterialTheme.typography.bodyMedium,
        shape = MaterialTheme.shapes.extraLarge,
        value = searchQuery,
        singleLine = true,
        onValueChange = {
            onSearchQueryChanged(it)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search, contentDescription = "Search"
            )
        },
        trailingIcon = {
            if (searchQuery.isNotBlank()) {
                IconButton(onClick = {
                    onSearchQueryChanged("")
                    onChangeSearchingStatus(true)
                }) {
                    Icon(
                        imageVector = Icons.Default.Close, contentDescription = "Clear"
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            onSearch(searchQuery)
            onChangeSearchingStatus(false)
        }),

        )
}

@Preview(showBackground = true)
@Composable
private fun SearchAndFiltersSectionPreview() {
    KhanaTheme {
        SearchAndFiltersSection(modifier = Modifier.padding(16.dp),
            onFiltersButtonClicked = {},
            onSearchQueryChanged = {},
            onSearch = {},
            searchQuery = "",
            isSearching = false,
            onChangeSearchingStatus = {})
    }

}