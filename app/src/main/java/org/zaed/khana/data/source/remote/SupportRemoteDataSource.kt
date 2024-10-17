package org.zaed.khana.data.source.remote

import org.zaed.khana.data.model.ContactInfo
import org.zaed.khana.data.model.FAQ
import org.zaed.khana.data.util.Result
import org.zaed.khana.data.util.SupportResult

interface SupportRemoteDataSource {
    suspend fun fetchFAQs(): Result<List<FAQ>, SupportResult>
    suspend fun fetchContactInfo(): Result<ContactInfo, SupportResult>
}