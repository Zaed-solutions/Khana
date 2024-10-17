package org.zaed.khana.presentation.helpcenter

import org.zaed.khana.data.model.ContactInfo
import org.zaed.khana.data.model.FAQ

data class HelpCenterUiState (
    val faq: List<FAQ> = emptyList(),
    val faqTags: List<String> = emptyList(),
    val contactInfo: ContactInfo = ContactInfo()
)