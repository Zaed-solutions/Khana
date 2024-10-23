package org.zaed.khana.presentation.productdetails.components

import android.icu.text.DecimalFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.R
import org.zaed.khana.presentation.theme.KhanaTheme
import org.zaed.khana.presentation.util.shimmerEffect

@Composable
fun ProductInformationSection(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    title: String,
    description: String,
    category: String,
    rating: Float
) {
    if(isLoading){
        ProductInformationShimmer(modifier = modifier)
    } else {
        Column (
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = category,
                    style = MaterialTheme.typography.labelLarge
                )
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(imageVector = Icons.Rounded.Star, tint  = MaterialTheme.colorScheme.secondary, contentDescription = null)
                    Text(
                        text = DecimalFormat("#.#").format(rating),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.description),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
            ExpandableText(text = description)
        }
    }
}

@Composable
private fun ProductInformationShimmer(modifier: Modifier = Modifier) {
    Column (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.5f)
                .height(24.dp)
                .shimmerEffect()
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.8f)
                .height(36.dp)
                .shimmerEffect()
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .shimmerEffect()
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ProductInformationPreview() {
    KhanaTheme {
        ProductInformationSection(
            modifier = Modifier.padding(16.dp),
            isLoading = true,
            title = "Jacket Bracket",
            description = "\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum", category = "Men's Jacket", rating = 4.7f)
    }
}

