package org.zaed.khana.data.source.local

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.util.Result
import org.zaed.khana.data.util.SearchResult

interface RecentSearchLocalDataSource {
    suspend fun addRecentSearch(query: String): Result<Unit, SearchResult>
    fun getRecentSearches(): Flow<Result<List<String>, SearchResult>>
    suspend fun deleteRecentSearch(query: String): Result<Unit, SearchResult>
    suspend fun deleteAllRecentSearches(): Result<Unit, SearchResult>
}