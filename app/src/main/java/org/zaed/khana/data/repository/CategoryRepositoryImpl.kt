package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Category
import org.zaed.khana.data.source.remote.CategoryRemoteDataSource

class CategoryRepositoryImpl (
    private val categoryRemoteSource: CategoryRemoteDataSource,
): CategoryRepository {
    override fun fetchCategories(): Flow<List<Category>> {
        return categoryRemoteSource.fetchCategories()
    }
}