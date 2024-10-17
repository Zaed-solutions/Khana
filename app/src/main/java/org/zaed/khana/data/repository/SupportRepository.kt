package org.zaed.khana.data.repository

import org.zaed.khana.data.model.ContactInfo
import org.zaed.khana.data.model.FAQ
import org.zaed.khana.data.util.Result
import org.zaed.khana.data.util.SupportResult

interface SupportRepository {
    suspend fun fetchFAQs(): Result<List<FAQ>, SupportResult>
    suspend fun fetchContactInfo(): Result<ContactInfo, SupportResult>
}