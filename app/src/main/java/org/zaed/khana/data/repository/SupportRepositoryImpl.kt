package org.zaed.khana.data.repository

import org.zaed.khana.data.model.ContactInfo
import org.zaed.khana.data.model.FAQ
import org.zaed.khana.data.source.remote.SupportRemoteDataSource
import org.zaed.khana.data.util.Result
import org.zaed.khana.data.util.SupportResult

class SupportRepositoryImpl(
    private val supportRemoteSource: SupportRemoteDataSource
) : SupportRepository {
    override suspend fun fetchFAQs(): Result<List<FAQ>, SupportResult> {
        return supportRemoteSource.fetchFAQs()
    }

    override suspend fun fetchContactInfo(): Result<ContactInfo, SupportResult> {
        return supportRemoteSource.fetchContactInfo()
    }
}