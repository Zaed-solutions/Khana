package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Advertisement
import org.zaed.khana.data.util.AdvertisementResult
import org.zaed.khana.data.util.Result

interface AdvertisementRepository {
    fun fetchAdvertisements(): Flow<Result<List<Advertisement>, AdvertisementResult>>
}