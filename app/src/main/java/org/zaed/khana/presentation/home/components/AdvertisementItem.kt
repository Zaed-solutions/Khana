package org.zaed.khana.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import org.zaed.khana.R
import org.zaed.khana.data.model.Advertisement
import org.zaed.khana.presentation.theme.KhanaTheme
import org.zaed.khana.presentation.util.shimmerEffect

@Composable
fun AdvertisementItem(
    modifier: Modifier = Modifier,
    advertisement: Advertisement,
    onBrowseOffersClicked: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .height(150.dp)
    ) {
        SubcomposeAsyncImage(
            model = advertisement.backgroundImageUrl,
            contentDescription = "ad background",
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .shimmerEffect()
                )
            } else {
                SubcomposeAsyncImageContent()
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            Text(
                text = advertisement.title,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = advertisement.description.wrapText(21),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Button(onClick = { onBrowseOffersClicked() }, shape = MaterialTheme.shapes.large) {
                Text(text = stringResource(R.string.browse_offers))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AdvertisementItemPreview() {
    val ad = Advertisement(
        title = "Test Advertisement",
        description = "Test Descriptions",
        backgroundImageUrl = "https://www.test.com/test.jpg"
    )
    KhanaTheme {
        AdvertisementItem(modifier = Modifier.padding(16.dp), advertisement = ad)
    }
}

fun String.wrapText(maxCharsPerLine: Int): String {
    val words = this.split(" ")
    val result = StringBuilder()
    var currentLineLength = 0
    for (word in words) {
        if (currentLineLength + word.length + (if (currentLineLength > 0) 1 else 0) > maxCharsPerLine) {
            result.append("\n")
            currentLineLength = 0
        } else if (currentLineLength > 0) {
            result.append(" ")
            currentLineLength++
        }
        result.append(word)
        currentLineLength += word.length
    }
    return result.toString()
}