package org.zaed.khana.presentation.coupons.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.zaed.khana.R
import org.zaed.khana.data.model.Coupon
import org.zaed.khana.presentation.theme.KhanaTheme
import org.zaed.khana.presentation.util.shimmerEffect

@Composable
fun CouponItem(
    modifier: Modifier = Modifier,
    coupon: Coupon,
    onCopyCouponCode: (String) -> Unit
) {
    val cornerRadius = 16.dp
    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        shape = TicketShape(
            circleRadius = 20.dp,
            circleCutoffPercentage = 2.4F,
            cornerSize = CornerSize(cornerRadius)
        ),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 32.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = coupon.title,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Normal,
                    fontSize = 22.sp
                )
                Text(
                    text = "Add items worth \$${coupon.minAmount.toInt()} to unlock",
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = coupon.description,
                        maxLines = 1,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp
                    )
                }
            }
            FilledTonalButton(
                onClick = { onCopyCouponCode(coupon.code) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = cornerRadius,
                    bottomEnd = cornerRadius
                ),
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = stringResource(R.string.copy_code),
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun CouponItemShimmer(modifier: Modifier = Modifier) {
    val cornerRadius = 16.dp
    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        shape = TicketShape(
            circleRadius = 20.dp,
            circleCutoffPercentage = 2.4F,
            cornerSize = CornerSize(cornerRadius)
        ),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 32.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(26.dp)
                        .shimmerEffect()
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(24.dp)
                        .shimmerEffect()
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(26.dp)
                        .shimmerEffect()
                )

            }
            FilledTonalButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = cornerRadius,
                    bottomEnd = cornerRadius
                ),
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = stringResource(R.string.copy_code),
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun CouponItemPreview() {
    KhanaTheme {
        CouponItem(
            coupon = Coupon(
                title = "WELCOME200",
                description = "GET 50% OFF",
                code = "WELCOME200",
                discountPercentage = 20f,
                minAmount = 2f
            )
        ) {}
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun CouponShimmerPreview() {
    KhanaTheme {
        CouponItemShimmer()
    }
}