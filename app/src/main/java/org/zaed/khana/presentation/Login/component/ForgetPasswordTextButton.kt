package org.zaed.khana.presentation.Login.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import org.zaed.khana.R
import org.zaed.khana.presentation.Login.LoginUIAction

@Composable
fun ForgetPasswordTextButton(
    modifier: Modifier = Modifier,
    onClick: (LoginUIAction) -> Unit = {},
) {
    TextButton({ onClick(LoginUIAction.OnForgetPasswordClicked) }) {
        Text(
            text = stringResource(R.string.forget_password),
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.End,
            textDecoration = TextDecoration.Underline
        )
    }
}