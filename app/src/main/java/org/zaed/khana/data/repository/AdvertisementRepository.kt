package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Advertisement

interface AdvertisementRepository {
    fun fetchAdvertisements(): Flow<List<Advertisement>>
}