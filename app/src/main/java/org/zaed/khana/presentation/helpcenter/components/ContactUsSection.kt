package org.zaed.khana.presentation.helpcenter.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Title
import androidx.compose.material.icons.filled.Web
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.R
import org.zaed.khana.data.model.ContactInfo
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun ContactUsSection(
    modifier: Modifier = Modifier,
    contactInfo: ContactInfo
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (contactInfo.customerSupportLines.isNotEmpty()) {
            ContactInfoItem(
                icon = Icons.Default.HeadsetMic,
                title = stringResource(R.string.customer_service),
                subItems = contactInfo.customerSupportLines,
                onItemClick = { phoneNumber ->
                    val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:$phoneNumber")
                    }
                    context.startActivity(dialIntent)
                }
            )
        }
        if (contactInfo.whatsappNumbers.isNotEmpty()) {
            ContactInfoItem(
                icon = Icons.Default.Whatsapp,
                title = stringResource(R.string.whatsapp),
                subItems = contactInfo.whatsappNumbers,
                onItemClick = { phoneNumber ->
                    val whatsappIntent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://wa.me/$phoneNumber")
                    }
                    context.startActivity(whatsappIntent)
                }
            )
        }
        if (contactInfo.websiteUrls.isNotEmpty()) {
            ContactInfoItem(
                icon = Icons.Default.Web,
                title = stringResource(R.string.website),
                subItems = contactInfo.websiteUrls,
                onItemClick = { url ->
                    val browserIntent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(url)
                    }
                    context.startActivity(browserIntent)
                }
            )
        }
        if (contactInfo.facebookPagesLinks.isNotEmpty()) {
            ContactInfoItem(
                icon = Icons.Default.Facebook,
                title = stringResource(R.string.facebook),
                subItems = contactInfo.facebookPagesLinks,
                onItemClick = { pageLink ->
                    val facebookIntent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(pageLink)
                    }
                    context.startActivity(facebookIntent)
                }
            )
        }
        if (contactInfo.twitterProfiles.isNotEmpty()) {
            ContactInfoItem(
                icon = Icons.Default.Title,
                title = stringResource(R.string.twitter),
                subItems = contactInfo.twitterProfiles,
                onItemClick = { profileLink ->
                    val twitterIntent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(profileLink)
                    }
                    context.startActivity(twitterIntent)
                }
            )
        }
        if (contactInfo.instagramPages.isNotEmpty()) {
            ContactInfoItem(
                icon = Icons.Default.Image,
                title = stringResource(R.string.instagram),
                subItems = contactInfo.instagramPages,
                onItemClick = { pageLink ->
                    val instagramIntent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(pageLink)
                    }
                    context.startActivity(instagramIntent)
                }
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ContactUsSectionPreview() {
    val contactInfo = ContactInfo(
        customerSupportLines = listOf("1234567890"),
        whatsappNumbers = listOf("1234567890"),
        websiteUrls = listOf("https://www.google.com"),
        facebookPagesLinks = listOf("https://www.facebook.com"),
        twitterProfiles = listOf("https://www.twitter.com"),
        instagramPages = listOf("https://www.instagram.com")
    )
    KhanaTheme {
        ContactUsSection(contactInfo = contactInfo)
    }
}