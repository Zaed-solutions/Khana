package org.zaed.khana.data.source.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.zaed.khana.data.model.Advertisement
import org.zaed.khana.data.source.remote.util.EndPoint
import org.zaed.khana.data.source.remote.util.endPoint
import org.zaed.khana.data.util.AdvertisementResult
import org.zaed.khana.data.util.Result

class AdvertisementRemoteDataSourceImpl(
    private val httpClient: HttpClient,
) : AdvertisementRemoteDataSource {
    //fetch the displayed ads in home screen
    override fun fetchAdvertisements(): Flow<Result<List<Advertisement>, AdvertisementResult>> = flow{
        emit(Result.Loading)
        try{
            val response = httpClient.get {
                endPoint(EndPoint.Advertisement.FetchAdvertisement.route)
            }
            if(response.status == HttpStatusCode.OK){
                emit(Result.success(response.body<List<Advertisement>>()))
            }
        } catch (e: Exception){
            e.printStackTrace()
            emit(Result.failure(AdvertisementResult.SERVER_ERROR))
        }
    }
}