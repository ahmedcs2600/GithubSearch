package com.app.network.core

import com.app.common.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.IOException

abstract class NetworkBoundRepository<RESPONSE> {

    fun asFlow() : Flow<DataState<RESPONSE>> {
        return flow<DataState<RESPONSE>> {
            val response = fetchFromRemote()
            if(response.isSuccessful && response.body() != null) {
                response.body()?.let { emit(DataState.success(it)) }
            } else {
                emit(DataState.error("Response Error ${response.code()}"))
            }
        }.catch {
            if(it.cause is IOException) {
                emit(DataState.error("No Internet"))
            } else {
                emit(DataState.error(it.message ?: "Something went wrong"))
            }
        }.flowOn(Dispatchers.IO)
    }

    abstract suspend fun fetchFromRemote() : Response<RESPONSE>
}