package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Category
import org.zaed.khana.data.util.CategoryResult
import org.zaed.khana.data.util.Result

interface CategoryRepository {
    fun fetchCategories(): Flow<Result<List<Category>, CategoryResult>>
}