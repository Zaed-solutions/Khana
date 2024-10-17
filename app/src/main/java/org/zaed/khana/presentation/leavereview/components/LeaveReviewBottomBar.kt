package org.zaed.khana.presentation.leavereview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.R
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun LeaveReviewBottomBar(
    modifier: Modifier = Modifier,
    onSubmitClicked: () -> Unit,
    onCancelClicked: () -> Unit
) {
    Surface(
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        modifier = modifier.fillMaxWidth(),
    ){
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            FilledTonalButton(
                modifier = Modifier.widthIn(min = 120.dp),
                onClick = { onCancelClicked() }
            ) {
                Text(text = stringResource(R.string.cancel))
            }
            Button(
                modifier = Modifier.widthIn(min = 120.dp),
                onClick = { onSubmitClicked() }
            ) {
                Text(stringResource(R.string.submit))
            }
        }
    }
}
//write a preview for the above composable
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LeaveReviewBottomBarPreview() {
    KhanaTheme {
        LeaveReviewBottomBar(
            onSubmitClicked = {},
            onCancelClicked = {}
        )
    }
}