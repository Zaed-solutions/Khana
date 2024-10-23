package org.zaed.khana.presentation.productdetails.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.zaed.khana.R
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ProductDetailsTopBar(
    onBackPressed: () -> Unit,
    onWishlistProduct: () -> Unit,
    isWishlisted: Boolean,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.product_details),
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back button"
                )
            }
        },
        actions = {
            IconButton(
                onClick = { onWishlistProduct() },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(
                        alpha = 0.7f
                    ), contentColor = MaterialTheme.colorScheme.primary
                ),
            ) {
                Icon(
                    imageVector = if (isWishlisted) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null
                )
            }
        })
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ProductDetailsTopBarPreview() {
    KhanaTheme {
        ProductDetailsTopBar(
            onBackPressed = { /*TODO*/ },
            onWishlistProduct = { /*TODO*/ },
            isWishlisted = true
        )
    }
}