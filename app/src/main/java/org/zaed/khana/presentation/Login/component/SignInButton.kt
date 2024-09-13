package org.zaed.khana.presentation.Login.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.zaed.khana.R
import org.zaed.khana.presentation.Login.LoginUIAction

@Composable
fun SignInButton(
    modifier: Modifier = Modifier,
    onClick: (LoginUIAction) -> Unit = {},
) {
    Button(
        onClick = { onClick(LoginUIAction.OnSignInClicked) },
        modifier = Modifier.fillMaxWidth().then(modifier)
    ) {
        Text(
            text = stringResource(R.string.sign_in),
            fontSize = 20.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}