package org.zaed.khana.data.source.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import org.zaed.khana.data.model.ContactInfo
import org.zaed.khana.data.model.FAQ
import org.zaed.khana.data.source.remote.util.EndPoint
import org.zaed.khana.data.source.remote.util.GenericResponse
import org.zaed.khana.data.source.remote.util.endPoint
import org.zaed.khana.data.util.Result
import org.zaed.khana.data.util.SupportResult

class SupportRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : SupportRemoteDataSource {
    override suspend fun fetchFAQs(): Result<List<FAQ>, SupportResult> {
        return try{
            val response = httpClient.get {
                endPoint(EndPoint.Support.FetchFAQ.route)
            }
            if(response.status == HttpStatusCode.OK){
                val responseData = response.body<GenericResponse<List<FAQ>>>().data
                if(responseData != null){
                    Result.success(responseData)
                } else {
                    Result.Error(SupportResult.FETCH_FAQS_FAILED)
                }
            } else {
                Result.Error(SupportResult.FETCH_FAQS_FAILED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(SupportResult.SERVER_ERROR)
        }
    }

    override suspend fun fetchContactInfo(): Result<ContactInfo, SupportResult> {
        return try{
            val response = httpClient.get {
                endPoint(EndPoint.Support.FetchContactInfo.route)
            }
            if(response.status == HttpStatusCode.OK){
                val responseData = response.body<GenericResponse<ContactInfo>>().data
                if(responseData != null){
                    Result.success(responseData)
                } else {
                    Result.Error(SupportResult.FETCH_CONTACT_INFO_FAILED)
                }
            } else {
                Result.Error(SupportResult.FETCH_CONTACT_INFO_FAILED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(SupportResult.SERVER_ERROR)
        }
    }
}