package org.zaed.khana.data.source.remote

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Advertisement
import org.zaed.khana.data.util.AdvertisementResult
import org.zaed.khana.data.util.Result

interface AdvertisementRemoteDataSource {
    fun fetchAdvertisements(): Flow<Result<List<Advertisement>, AdvertisementResult>>
}