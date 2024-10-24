package org.zaed.khana.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import org.zaed.khana.presentation.util.shimmerEffect

@Composable
fun StatefulAsyncImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    shape: Shape = MaterialTheme.shapes.large,
    shadowElevation: Dp = 0.dp,
    contentScale: ContentScale = ContentScale.Crop
) {
    Surface(
        modifier = modifier,
        shadowElevation = shadowElevation,
        shape = shape
    ) {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = contentScale
        ) {
            val state = painter.state
            when(state){
                is AsyncImagePainter.State.Success -> {
                    SubcomposeAsyncImageContent()
                }
                is AsyncImagePainter.State.Loading -> {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .shimmerEffect()
                    )
                }
                is AsyncImagePainter.State.Error -> {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.Gray.copy(alpha = 0.3f)
                    ){
                        Icon(
                            imageVector = Icons.Default.BrokenImage,
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(36.dp)
                        )
                    }
                }
                else -> Unit
            }
        }
    }
}