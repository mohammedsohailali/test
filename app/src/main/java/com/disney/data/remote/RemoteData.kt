package com.disney.data.remote

import com.disney.data.Resource
import com.disney.data.error.NETWORK_ERROR
import com.disney.data.error.NO_INTERNET_CONNECTION
import com.disney.data.remote.service.DisneyService
import com.disney.data.model.GuestItem
import com.disney.data.model.NYCGuesttem
import com.disney.utils.NetworkConnectivity
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RemoteData @Inject
constructor(
    private val serviceGenerator: ServiceGenerator,
    private val networkConnectivity: NetworkConnectivity
) : RemoteDataSource {
    override suspend fun fetchList(): Resource<NYCGuesttem> {
        val guestService = serviceGenerator.createService(DisneyService::class.java)
        return when (val response = processCall(guestService::fetchList)) {
            is List<*> -> {
                Resource.Success(data = NYCGuesttem(response as ArrayList<GuestItem>))
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }
}
