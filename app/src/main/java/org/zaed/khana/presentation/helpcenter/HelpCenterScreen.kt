package org.zaed.khana.presentation.helpcenter

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.data.model.ContactInfo
import org.zaed.khana.data.model.FAQ
import org.zaed.khana.presentation.helpcenter.components.ContactUsSection
import org.zaed.khana.presentation.helpcenter.components.FaqSection
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun HelpCenterScreen(
    modifier: Modifier = Modifier,
    viewModel: HelpCenterViewModel = koinViewModel(),
    onBackPressed: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HelpCenterScreenContent(
    modifier: Modifier = Modifier,
    faq: List<FAQ>,
    faqTags: List<String>,
    contactInfo: ContactInfo,
    onAction: (HelpCenterUiAction) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    var selectedTab by remember { mutableIntStateOf(0) }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Help Center")
                },
                navigationIcon = {
                    OutlinedIconButton(
                        onClick = { onAction(HelpCenterUiAction.OnBackPressed) }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PrimaryTabRow(
                selectedTabIndex = selectedTab,
                modifier = Modifier.fillMaxWidth()
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = {
                        selectedTab = 0
                    },
                    text = {
                        Text(text = "FAQ")
                    }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                    },
                    text = {
                        Text(text = "Contact Us")
                    }
                )
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
            ) { pageIndex ->
                AnimatedContent(targetState = selectedTab, label = "screenContent") { state ->
                    when(state){
                        0 -> {
                            FaqSection(faq = faq, faqTags = faqTags)
                        }
                        1 -> {
                            ContactUsSection(contactInfo = contactInfo)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun HelpCenterScreenContentPreview() {
    val faqs = listOf(
        FAQ(
            tag = "Account",
            question = "How to create an account?",
            answer = "To create an account, you need to click on the sign-up button and fill in the required details.",
        ),
        FAQ(
            tag = "Purchase",
            question = "Are refunds available?",
            answer = "Yes, refunds are available. Please refer to our refund policy for more information.",
        ),

        )
    val contactInfo = ContactInfo(
        customerSupportLines = listOf("1234567890"),
        whatsappNumbers = listOf("1234567890"),
        websiteUrls = listOf("https://www.google.com"),
        facebookPagesLinks = listOf("https://www.facebook.com"),
        twitterProfiles = listOf("https://www.twitter.com"),
        instagramPages = listOf("https://www.instagram.com")
    )
    KhanaTheme {
        HelpCenterScreenContent(
            faq = faqs,
            faqTags = listOf("All") + faqs.map { it.tag },
            contactInfo = contactInfo
        ){}
    }
}
