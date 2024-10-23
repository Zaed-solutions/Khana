package org.zaed.khana.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import org.zaed.khana.presentation.theme.KhanaTheme
import org.zaed.khana.presentation.util.shimmerEffect

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
        SubcomposeAsyncImage(
            model = categoryImage,
            contentDescription = "category image",
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                Box(modifier = Modifier.fillMaxSize().shimmerEffect())
            } else {
                SubcomposeAsyncImageContent()
            }
        }

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