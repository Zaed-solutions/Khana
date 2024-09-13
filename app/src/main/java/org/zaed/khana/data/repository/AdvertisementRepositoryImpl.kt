package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Advertisement
import org.zaed.khana.data.source.remote.AdvertisementRemoteDataSource

class AdvertisementRepositoryImpl (
    private val advertisementRemoteSource: AdvertisementRemoteDataSource
): AdvertisementRepository {
    override fun fetchAdvertisements(): Flow<List<Advertisement>> {
        return advertisementRemoteSource.fetchAdvertisements()
    }
}