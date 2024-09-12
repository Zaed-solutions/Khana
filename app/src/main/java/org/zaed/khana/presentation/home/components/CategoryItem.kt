package org.zaed.khana.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun CategoryItem(
    categoryImage: String,
    categoryTitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(categoryImage)
                .crossfade(true)
                .build(),
            contentDescription = "category image",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(88.dp).clip(CircleShape)
        )
        Text(text = categoryTitle)
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryItemPreview() {
    KhanaTheme {
        CategoryItem(
            categoryImage = "https://us.123rf.com/450wm/pavelstasevich/pavelstasevich1904/pavelstasevich190400188/123197783-vector-illustration-flat-design-clothes-t-shirt-icon.jpg",
            categoryTitle = "Category")
    }
}