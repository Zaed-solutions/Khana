package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Category

interface CategoryRepository {
    fun fetchCategories(): Flow<List<Category>>
}