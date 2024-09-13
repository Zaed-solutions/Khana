package org.zaed.khana.data.source.remote

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Category

interface CategoryRemoteDataSource {
    fun fetchCategories(): Flow<List<Category>>
}