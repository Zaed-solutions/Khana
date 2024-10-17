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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.R
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun OTPVerificationScreen(
    viewModel: OtpViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Text(text = stringResource(R.string.verify_code), fontSize = 24.sp)

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = stringResource(R.string.please_enter_the_code_we_just_sent_to_email))
        Text(text = stringResource(R.string.example_email_com), color = Color.Gray)

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OTPTextField(value = state.otp1) {  viewModel.handleUiState(OtpUIAction.OnOtp1Changed(it)) }
            OTPTextField(value = state.otp2) {  viewModel.handleUiState(OtpUIAction.OnOtp2Changed(it)) }
            OTPTextField(value = state.otp3) {  viewModel.handleUiState(OtpUIAction.OnOtp3Changed(it)) }
            OTPTextField(value = state.otp4) {  viewModel.handleUiState(OtpUIAction.OnOtp4Changed(it)) }

        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.didn_t_receive_otp),
        )
        TextButton({viewModel.handleUiState(OtpUIAction.OnResendOtpClicked)}) {
            Text(
                text = stringResource(R.string.resend_code),
                modifier = Modifier.clickable { viewModel.handleUiState(OtpUIAction.OnResendOtpClicked)},
                textDecoration = TextDecoration.Underline
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        // Verify Button
        Button(
            onClick = {viewModel.handleUiState(OtpUIAction.OnVerifyOtpClicked)},
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
        textStyle = LocalTextStyle.current.copy(fontSize = 24.sp, textAlign = TextAlign.Center),
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun OTPVerificationScreenPreview() {
    KhanaTheme {
        OTPVerificationScreen()
    }
}
