package com.disney.data.remote

import com.disney.data.Resource
import com.disney.data.model.NYCGuesttem

internal interface RemoteDataSource {
    suspend fun fetchList(): Resource<NYCGuesttem>
}
