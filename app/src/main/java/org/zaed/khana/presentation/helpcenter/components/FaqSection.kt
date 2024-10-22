package org.zaed.khana.presentation.helpcenter.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.data.model.FAQ
import org.zaed.khana.presentation.theme.KhanaTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FaqSection(
    modifier: Modifier = Modifier,
    faq: List<FAQ>,
    faqTags: List<String>,
) {
    var selectedTag by remember {
        mutableStateOf(faqTags.firstOrNull()?:"")
    }
    val filteredFaqs by remember(faq, selectedTag) {
        mutableStateOf(faq.filter { selectedTag == "All" || it.tag == selectedTag })
    }
    Column (
        modifier = modifier.fillMaxWidth(),
    ) {
        LazyRow (
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(faqTags.size) { index ->
                val tag = faqTags[index]
                FaqTagItem(
                    tag = tag,
                    isSelected = tag == selectedTag,
                    onTagClicked = { selectedTag = it }
                )
            }
        }
        FaqList(faqs = filteredFaqs)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun FaqSectionPreview() {
    val list = listOf(
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
    KhanaTheme {
        FaqSection(faq = list, faqTags = listOf("All") + list.map { it.tag })
    }
}