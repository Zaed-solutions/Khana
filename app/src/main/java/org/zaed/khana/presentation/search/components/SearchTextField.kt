package org.zaed.khana.presentation.search.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    onSearchQueryChanged: (String) -> Unit,
    onDoneClicked: () -> Unit = {},
    searchQuery: String,
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
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
            focusManager.clearFocus(true)
        }),

        )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun SearchTextFieldPreview() {
    KhanaTheme {
        SearchTextField(
            onSearchQueryChanged = {},
            searchQuery = "",
        )
    }
}