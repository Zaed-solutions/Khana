package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Product
import org.zaed.khana.data.util.ProductResult
import org.zaed.khana.data.util.Result
import org.zaed.khana.data.util.SearchResult

interface SearchRepository {
    fun fetchRecentSearches(): Flow<Result<List<String>, SearchResult>>
    suspend fun clearRecentSearches(): Result<Unit, SearchResult>
    suspend fun deleteRecentSearch(query: String): Result<Unit, SearchResult>
    suspend fun addRecentSearch(query: String): Result<Unit, SearchResult>
    fun fetchSearchResult(query: String): Flow<Result<List<Product>, ProductResult>>
}