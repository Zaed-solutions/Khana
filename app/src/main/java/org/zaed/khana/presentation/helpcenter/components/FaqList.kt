package org.zaed.khana.presentation.helpcenter.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.data.model.FAQ
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun FaqList(
    modifier: Modifier = Modifier,
    faqs: List<FAQ>,
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        faqs.forEach { faq ->
            FaqItem(faq = faq)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun FaqListPreview() {
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
        FaqList(faqs =list)
    }
}