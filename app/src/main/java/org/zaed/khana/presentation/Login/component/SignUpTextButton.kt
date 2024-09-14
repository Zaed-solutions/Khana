package org.zaed.khana.presentation.Login.component

import androidx.compose.foundation.clickable
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
import org.zaed.khana.presentation.Login.LoginUIAction

@Composable
fun SignUpTextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val annotatedString = buildAnnotatedString {
        append(stringResource(R.string.don_t_have_an_account))
        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
            append(stringResource(R.string.sign_up))
        }
    }

    Text(
        text = annotatedString,
        modifier = Modifier
            .clickable {
                onClick()
            }
            .then(modifier)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}