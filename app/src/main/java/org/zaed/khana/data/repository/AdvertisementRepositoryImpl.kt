package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Advertisement
import org.zaed.khana.data.source.remote.AdvertisementRemoteDataSource
import org.zaed.khana.data.util.AdvertisementResult
import org.zaed.khana.data.util.Result

class AdvertisementRepositoryImpl (
    private val advertisementRemoteSource: AdvertisementRemoteDataSource
): AdvertisementRepository {
    override fun fetchAdvertisements(): Flow<Result<List<Advertisement>, AdvertisementResult>> {
        return advertisementRemoteSource.fetchAdvertisements()
    }
}