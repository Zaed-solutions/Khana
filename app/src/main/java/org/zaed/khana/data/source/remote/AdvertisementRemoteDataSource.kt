package org.zaed.khana.data.source.remote

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Advertisement

interface AdvertisementRemoteDataSource {
    fun fetchAdvertisements(): Flow<List<Advertisement>>
}