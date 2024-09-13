package org.zaed.khana.presentation.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import org.zaed.khana.R


@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    onSearchQueryChanged: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
    searchQuery: String = "",
    isSearching: Boolean = false,
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