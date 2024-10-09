package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Product
import org.zaed.khana.data.source.local.RecentSearchLocalDataSource
import org.zaed.khana.data.source.remote.ProductRemoteDataSource
import org.zaed.khana.data.util.ProductResult
import org.zaed.khana.data.util.Result
import org.zaed.khana.data.util.SearchResult

class SearchRepositoryImpl(
    private val recentSearchLocalSource: RecentSearchLocalDataSource,
    private val productRemoteSource: ProductRemoteDataSource
) : SearchRepository {
    override fun fetchRecentSearches(): Flow<Result<List<String>, SearchResult>> {
        return recentSearchLocalSource.getRecentSearches()
    }

    override suspend fun clearRecentSearches(): Result<Unit, SearchResult> {
        return recentSearchLocalSource.deleteAllRecentSearches()
    }

    override suspend fun deleteRecentSearch(query: String): Result<Unit, SearchResult> {
        return recentSearchLocalSource.deleteRecentSearch(query)
    }

    override suspend fun addRecentSearch(query: String): Result<Unit, SearchResult> {
        return recentSearchLocalSource.addRecentSearch(query)
    }

    override fun fetchSearchResult(query: String): Flow<Result<List<Product>, ProductResult>> {
        return productRemoteSource.searchProductsByTitle(query)
    }
}