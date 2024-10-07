package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Category
import org.zaed.khana.data.source.remote.CategoryRemoteDataSource
import org.zaed.khana.data.util.CategoryResult
import org.zaed.khana.data.util.Result

class CategoryRepositoryImpl (
    private val categoryRemoteSource: CategoryRemoteDataSource,
): CategoryRepository {
    override fun fetchCategories(): Flow<Result<List<Category>, CategoryResult>> {
        return categoryRemoteSource.fetchCategories()
    }
}