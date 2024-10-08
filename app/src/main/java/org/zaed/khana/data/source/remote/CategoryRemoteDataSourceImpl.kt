package org.zaed.khana.data.source.remote

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.zaed.khana.data.model.Category
import org.zaed.khana.data.source.remote.util.EndPoint
import org.zaed.khana.data.source.remote.util.GenericResponse
import org.zaed.khana.data.source.remote.util.endPoint
import org.zaed.khana.data.util.CategoryResult
import org.zaed.khana.data.util.Result

class CategoryRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : CategoryRemoteDataSource {
    //fetch the displayed categories in home screen
    override fun fetchCategories(): Flow<Result<List<Category>, CategoryResult>> = flow {
        emit(Result.Loading)
        try{
            val response = httpClient.get{
                endPoint(EndPoint.Category.FetchCategories.route)
            }
            if(response.status == HttpStatusCode.OK) {
                emit(Result.success(response.body<GenericResponse<List<Category>>>().data ?: emptyList()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(CategoryResult.SERVER_ERROR))
        }
    }
}