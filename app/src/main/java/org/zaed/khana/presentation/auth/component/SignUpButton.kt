package org.zaed.khana.presentation.auth.component

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
import org.zaed.khana.presentation.auth.login.LoginUIAction

@Composable
fun SignUpButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().then(modifier)
    ) {
        Text(
            text = stringResource(R.string.sign_up),
            fontSize = 20.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}