package org.zaed.khana.data.source.local

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.zaed.khana.data.source.local.model.RecentSearchEntity
import org.zaed.khana.data.util.Result
import org.zaed.khana.data.util.SearchResult

class RecentSearchLocalDataSourceImpl(
    private val realm: Realm,
) : RecentSearchLocalDataSource {
    override suspend fun addRecentSearch(query: String): Result<Unit, SearchResult> {
        return try {
            realm.write {
                copyToRealm(RecentSearchEntity().apply {
                    this.query = query
                })
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(SearchResult.FAILED_TO_ADD_RECENT_SEARCHES)
        }
    }

    override fun getRecentSearches(): Flow<Result<List<String>, SearchResult>> = flow {
        emit(Result.Loading)
        try {
            val result = realm.query<RecentSearchEntity>().find().map { it.query }
            emit(Result.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(SearchResult.FAILED_TO_FETCH_RECENT_SEARCHES))
        }
    }

    override suspend fun deleteRecentSearch(query: String): Result<Unit, SearchResult> {
        return try {
            realm.writeBlocking {
                val result =
                    realm.query<RecentSearchEntity>("query == $0", query).find().firstOrNull()
                if (result != null) {
                    delete(result)
                }
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(SearchResult.FAILED_TO_DELETE_RECENT_SEARCH)
        }
    }

    override suspend fun deleteAllRecentSearches(): Result<Unit, SearchResult> {
        return try {
            realm.writeBlocking {
                val result = this.query<RecentSearchEntity>()
                delete(result)
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(SearchResult.FAILED_TO_CLEAR_RECENT_SEARCHES)
        }
    }
}