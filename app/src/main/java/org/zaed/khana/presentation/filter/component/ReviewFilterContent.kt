package org.zaed.khana.presentation.filter.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.data.util.ReviewsFilterOption
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun ReviewsFilterContent(
    modifier: Modifier = Modifier,
    selectedReviewsOption: ReviewsFilterOption,
    onReviewsOptionSelected: (ReviewsFilterOption) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),

        ) {
        ReviewsFilterOption.entries.forEach { option ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = option.displayName)
                RadioButton(
                    selected = option == selectedReviewsOption,
                    onClick = { onReviewsOptionSelected(option) }
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ReviewsFilterContentPreview() {
    KhanaTheme {
        ReviewsFilterContent(
            selectedReviewsOption = ReviewsFilterOption.ALL,
            onReviewsOptionSelected = {}
        )
    }
}