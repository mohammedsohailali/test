package com.disney.data

import com.disney.data.model.NYCGuesttem
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {
    suspend fun fetchList(): Flow<Resource<NYCGuesttem>>
}
