package org.zaed.khana.data.source.remote

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Category
import org.zaed.khana.data.util.CategoryResult
import org.zaed.khana.data.util.Result

interface CategoryRemoteDataSource {
    fun fetchCategories(): Flow<Result<List<Category>, CategoryResult>>
}