package org.zaed.khana.presentation.productdetails.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun ImagesPreviewPager(
    modifier: Modifier = Modifier,
    imagesUrls: List<String>
) {
    val pagerState = rememberPagerState(pageCount = { imagesUrls.size })
    Box(
        modifier = modifier
            .height(320.dp)
            .fillMaxWidth()
    ) {
        HorizontalPager(state = pagerState) { pageIndex ->
            AsyncImage(
                model = imagesUrls[pageIndex],
                contentDescription = null,
            )
        }
        if (imagesUrls.size > 1) {
            DotsIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(vertical = 16.dp),
                dotCount = imagesUrls.size,
                type = ShiftIndicatorType(dotsGraphic = DotGraphic(color = MaterialTheme.colorScheme.primary)),
                pagerState = pagerState
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ImagesPagerPreview() {
    val images = listOf(
        "https://www.image.com/image.jpg",
        "https://www.image.com/image.jpg",
        "https://www.image.com/image.jpg"
    )
    KhanaTheme {
        ImagesPreviewPager(imagesUrls = images)
    }
}