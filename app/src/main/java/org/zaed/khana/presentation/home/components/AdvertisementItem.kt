package org.zaed.khana.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import org.zaed.khana.data.model.Advertisement
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun AdvertisementItem(
    advertisement: Advertisement,
    modifier: Modifier = Modifier
) {
    Box (
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ){
        SubcomposeAsyncImage(
            model = advertisement.backgroundImageUrl,
            contentDescription = "ad background",
            modifier = Modifier.align(Alignment.Center),
            contentScale = ContentScale.FillBounds
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                CircularProgressIndicator()
            } else {
                SubcomposeAsyncImageContent()
            }
        }
        Column (
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Text(
                text = advertisement.title,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = advertisement.description,
                style = MaterialTheme.typography.bodyLarge
            )
            Button(onClick = { /*TODO*/ }, shape = MaterialTheme.shapes.large) {
                Text(text = "Shop Now")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AdvertisementItemPreview() {
    val ad = Advertisement(title = "Test Advertisement", description = "Test Descriptions", backgroundImageUrl = "https://www.test.com/test.jpg")
    KhanaTheme {
        AdvertisementItem(modifier = Modifier.padding(16.dp), advertisement = ad)
    }
}