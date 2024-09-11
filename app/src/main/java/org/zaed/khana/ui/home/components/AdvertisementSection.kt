package org.zaed.khana.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
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
import org.zaed.khana.ui.theme.KhanaTheme

@Composable
fun AdvertisementSection(
    ads: List<Advertisement>,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(pageCount = {
        ads.size
    })
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(state = pagerState) { page ->
            AdvertisementItem(
                advertisement = ads[page]
            )
        }
        DotsIndicator(
            modifier = Modifier.padding(vertical = 8.dp),
            dotCount = ads.size,
            type = ShiftIndicatorType(dotsGraphic = DotGraphic(color = MaterialTheme.colorScheme.primary)),
            pagerState = pagerState
        )
    }
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
        AdvertisementSection(modifier = Modifier.padding(16.dp), ads = ads)
    }
}