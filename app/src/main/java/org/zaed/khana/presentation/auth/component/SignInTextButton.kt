package org.zaed.khana.presentation.auth.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import org.zaed.khana.R

@Composable
fun SignInTextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val annotatedString = buildAnnotatedString {
        append(stringResource(R.string.don_t_have_an_account))
        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
            append(stringResource(R.string.sign_in))
        }
    }
    Column(
        modifier = modifier,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        Text(
            text = annotatedString,
            modifier = Modifier
                .clickable {
                    onClick()
                }
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}