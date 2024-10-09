package org.zaed.khana.presentation.auth.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import org.zaed.khana.R
import org.zaed.khana.presentation.auth.signup.SignUpUiState

@Composable
fun TermsAndConditions(
    modifier: Modifier,
    state: SignUpUiState,
    termsAndConditionsApproved: (Boolean) -> Unit,
    onShowTermsAndConditions: () -> Unit,
) {
    val annotatedString = buildAnnotatedString {
        append("Agree With ")
        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
            append("Terms & Conditions")
        }
    }
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = state.termsAndConditions,
            onCheckedChange = { termsAndConditionsApproved(it) }
        )
        Text(
            text = annotatedString,
            modifier = Modifier
                .clickable {
                    onShowTermsAndConditions()
                }
                .fillMaxWidth(),
            textAlign = TextAlign.Start
        )    }





}