package org.zaed.khana.presentation.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType
import org.zaed.khana.data.model.Advertisement
import org.zaed.khana.presentation.theme.KhanaTheme
import org.zaed.khana.presentation.util.shimmerEffect

@Composable
fun AdvertisementSection(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    ads: List<Advertisement>,
) {
    if(isLoading){
        AdvertisementSectionShimmer(modifier = modifier)
    } else {
        val pagerState = rememberPagerState(pageCount = {
            ads.size
        })
        Column(
            modifier = modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(state = pagerState) { page ->
                AdvertisementItem(
                    advertisement = ads[page]
                )
            }
            if (ads.size > 1) {
                DotsIndicator(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .height(8.dp),
                    dotCount = ads.size,
                    type = ShiftIndicatorType(dotsGraphic = DotGraphic(color = MaterialTheme.colorScheme.primary)),
                    pagerState = pagerState
                )
            }
        }

    }
}

@Composable
private fun AdvertisementSectionShimmer(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(MaterialTheme.shapes.large)
            .shimmerEffect()
    )
}


@Preview(showBackground = false, showSystemUi = false)
@Composable
private fun AdvertisementSectionPreview() {
    val ads = listOf(
        Advertisement(
            title = "Test Advertisement 1",
            description = "Test Descriptions 1",
            backgroundImageUrl = "https://www.test.com/test.jpg"
        ),
        Advertisement(
            title = "Test Advertisement 2",
            description = "Test Descriptions 2",
            backgroundImageUrl = "https://www.test.com/test.jpg"
        ),
        Advertisement(
            title = "Test Advertisement 3",
            description = "Test Descriptions 3",
            backgroundImageUrl = "https://www.test.com/test.jpg"
        ),
        Advertisement(
            title = "Test Advertisement 4",
            description = "Test Descriptions 4",
            backgroundImageUrl = "https://www.test.com/test.jpg"
        ),
        Advertisement(
            title = "Test Advertisement 5",
            description = "Test Descriptions 5",
            backgroundImageUrl = "https://www.test.com/test.jpg"
        )
    )
    KhanaTheme {
        AdvertisementSection(modifier = Modifier.padding(16.dp), isLoading = false, ads = ads)
    }
}