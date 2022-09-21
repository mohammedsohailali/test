package com.disney.data

import com.disney.data.remote.RemoteData
import com.disney.data.model.NYCGuesttem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DataRepository @Inject constructor(
    private val remoteRepository: RemoteData,
    private val ioDispatcher: CoroutineContext
) : DataRepositorySource {

    override suspend fun fetchList(): Flow<Resource<NYCGuesttem>> {
        return flow {
            emit(remoteRepository.fetchList())
        }.flowOn(ioDispatcher)
    }

}
