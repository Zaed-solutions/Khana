package org.zaed.khana.presentation.auth.otp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import org.zaed.khana.R

@Composable
fun OTPVerificationScreen() {
    var otp1 by remember { mutableStateOf("") }
    var otp2 by remember { mutableStateOf("") }
    var otp3 by remember { mutableStateOf("") }
    var otp4 by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        // Title
        Text(text = stringResource(R.string.verify_code), fontSize = 24.sp)

        Spacer(modifier = Modifier.height(8.dp))

        // Instructions
        Text(text = stringResource(R.string.please_enter_the_code_we_just_sent_to_email))
        Text(text = stringResource(R.string.example_email_com), color = Color.Gray)

        Spacer(modifier = Modifier.height(24.dp))

        // OTP Input Boxes
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OTPTextField(value = otp1) { otp1 = it }
            OTPTextField(value = otp2) { otp2 = it }
            OTPTextField(value = otp3) { otp3 = it }
            OTPTextField(value = otp4) { otp4 = it }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Resend Code
        Text(
            text = stringResource(R.string.didn_t_receive_otp),
        )
        TextButton({}) {
            Text(
                text = stringResource(R.string.resend_code),
                modifier = Modifier.clickable { /* Resend code action */ },
                textDecoration = TextDecoration.Underline
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        // Verify Button
        Button(
            onClick = { /* Handle OTP verification */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
        ) {
            Text(text = "Verify", color = Color.White, fontSize = 16.sp)
        }
    }
}

@Composable
fun OTPTextField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = { if (it.length <= 1) onValueChange(it) },
        modifier = Modifier
            .size(60.dp)
            .background(Color.White),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        textStyle = LocalTextStyle.current.copy(fontSize = 24.sp, textAlign = androidx.compose.ui.text.style.TextAlign.Center),
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun OTPVerificationScreenPreview() {
    OTPVerificationScreen()
}
