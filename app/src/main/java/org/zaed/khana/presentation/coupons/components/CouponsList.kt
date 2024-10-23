package org.zaed.khana.presentation.coupons.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.zaed.khana.R
import org.zaed.khana.data.model.Coupon

@Composable
fun CouponsList(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    coupons: List<Coupon>,
    onCopyCouponCode: (String) -> Unit,
) {

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()

    ) {
        item {
            Text(
                text = stringResource(R.string.best_offers_for_you),
                style = MaterialTheme.typography.headlineSmall
            )
        }
        if(isLoading){
            CouponsListShimmer()
        } else {
            items(coupons.size) { index ->
                val coupon = coupons[index]
                CouponItem(
                    coupon = coupon,
                    onCopyCouponCode = onCopyCouponCode
                )
            }
        }
    }
}

private fun LazyListScope.CouponsListShimmer() {
    items(4){
        CouponItemShimmer()
    }
}
