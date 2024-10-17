package org.zaed.khana.presentation.auth.forgetPassword

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.data.util.isNotIdle
import org.zaed.khana.presentation.theme.KhanaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgetPasswordScreen(
    viewModel: ForgetPasswordViewModel = koinViewModel(),
    navigateToOtpScreen: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = { CenterAlignedTopAppBar(
            title = { Text("Forget Password") },
            navigationIcon = { Icon(Icons.Filled.ArrowBack,null) }
        ) }
    ) {
        LaunchedEffect (uiState.userMessage){
            if (uiState.userMessage.isNotIdle()){
                navigateToOtpScreen(uiState.email)
            }
        }
        Column(
            modifier = Modifier.fillMaxSize().padding(it).padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(32.dp),
                label = { Text("Email") },
                value = uiState.email,
                onValueChange = {viewModel.handleUiState(ForgetPasswordUIAction.OnEmailChanged(it))},
                shape = RoundedCornerShape(16.dp)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Button(
                onClick = {viewModel.handleUiState(ForgetPasswordUIAction.OnSendOtpClicked)},
                modifier = Modifier.fillMaxWidth().padding(32.dp).height(54.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Send OTP Code",
                    fontSize = 16.sp
                    )
            }

        }
    }
}


@Preview
@Composable
fun ForgetPasswordScreenPreview() {
    KhanaTheme {
        ForgetPasswordScreen()
    }
}