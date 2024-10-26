package org.zaed.khana.presentation.leavereview.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.zaed.khana.R

@Composable
fun RatingStar(
    modifier: Modifier = Modifier,
    rating: Int,
    index: Int,
    onRatingChanged: (Int) -> Unit
) {
    val painter = if (rating >= index + 1) {
        painterResource(id = R.drawable.ic_star_filled)
    } else {
        painterResource(id = R.drawable.ic_star_outlined)
    }
    IconButton(
        modifier = modifier,
        onClick = { onRatingChanged(index + 1) }
    ) {
        Icon(
            painter = painter,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(36.dp),
            contentDescription = null
        )
    }
}