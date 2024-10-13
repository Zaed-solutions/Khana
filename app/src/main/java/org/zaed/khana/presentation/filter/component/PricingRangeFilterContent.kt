package org.zaed.khana.presentation.filter.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.data.model.MaximumPrice
import org.zaed.khana.data.model.MinimumPrice
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun PricingRangeFilterContent(
    modifier: Modifier = Modifier,
    priceRange: Pair<MinimumPrice, MaximumPrice>,
    onRangeChanged: (Pair<MinimumPrice, MaximumPrice>) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        val maxPrice = 999
        Text("Min: $${priceRange.first}, Max: $${if (priceRange.second == maxPrice) "999+" else priceRange.second}")
        RangeSlider(
            value = priceRange.first.toFloat()..priceRange.second.toFloat(),
            onValueChange = { range ->
                onRangeChanged(Pair(range.start.toInt(), range.endInclusive.toInt()))
            },
            enabled = true,
            valueRange = 0f..maxPrice.toFloat(),
            steps = 10,
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PricingRangeFilterContentPreview() {
    KhanaTheme {
        PricingRangeFilterContent(
            priceRange = Pair(0, 99),
            onRangeChanged = {}
        )
    }
}