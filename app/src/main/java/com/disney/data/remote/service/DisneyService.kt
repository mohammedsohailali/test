package com.disney.data.remote.service

import com.disney.data.model.GuestItem
import retrofit2.Response
import retrofit2.http.GET

interface DisneyService {
    @GET("END_POINT_HERE")
    suspend fun fetchList(): Response<List<GuestItem>>
}
