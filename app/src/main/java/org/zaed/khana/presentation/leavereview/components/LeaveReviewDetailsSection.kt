package org.zaed.khana.presentation.leavereview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.R
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun LeaveReviewDetailsSection(
    modifier: Modifier = Modifier,
    onReviewChanged: (String) -> Unit,
) {
    var reviewDetails by remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.add_detailed_review),
            style = MaterialTheme.typography.titleSmall,
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value =reviewDetails,
            onValueChange = {
                reviewDetails = it
                onReviewChanged(it)
            },
            placeholder = {
                Text(text = stringResource(R.string.enter_here))
            },
            minLines = 5,
            shape = MaterialTheme.shapes.medium,
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun DetailsSectionPreview() {
    KhanaTheme {
        LeaveReviewDetailsSection(
            onReviewChanged = {}
        )
    }
}