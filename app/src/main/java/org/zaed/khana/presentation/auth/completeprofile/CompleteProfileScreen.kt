package org.zaed.khana.presentation.auth.completeprofile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun CompleteProfileScreen(
    viewModel: CompleteProfileViewModel = koinViewModel(),
    navigateToHomeScreen: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }
    CompleteProfileContent(
        state = state,
        navigateToHomeScreen=navigateToHomeScreen,
        updateProfile = viewModel::updateProfile,
        imageUri=imageUri,
        onUploadImage = {imagePickerLauncher.launch("image/*")}
    )
}
@Composable
fun CompleteProfileContent(
    state: CompleteProfileUiState,
    navigateToHomeScreen: () -> Unit = {},
    updateProfile: (String, String, Uri?) -> Unit = { _, _, _ -> },
    imageUri: Uri? = null,
    onUploadImage: () -> Unit = {}
){
    var name by remember { mutableStateOf(state.name) }
    var phoneNumber by remember { mutableStateOf(state.phoneNumber) }
    LaunchedEffect(state.isProfileCompleted) {
        if (state.isProfileCompleted) {
            navigateToHomeScreen()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Complete Your Profile", color = Color.Black)
        imageUri?.let {
            Image(
                painter = rememberImagePainter(it),
                contentDescription = "Profile Image",
                modifier = Modifier.size(128.dp),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick =
            onUploadImage
        ) {
            Text("Upload Profile Image")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
                updateProfile(name, phoneNumber, imageUri)
        }) {
            Text("Save Profile")
        }
        if (state.loading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfileScreenPreview() {
    KhanaTheme {
        CompleteProfileContent(
            state = CompleteProfileUiState(),
            navigateToHomeScreen = {},
            updateProfile = { _, _, _ -> },
            imageUri = null,
            onUploadImage = {}
        )
    }
}
