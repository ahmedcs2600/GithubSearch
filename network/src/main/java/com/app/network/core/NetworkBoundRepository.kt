package com.app.network.core

import com.app.common.API_RATE_LIMIT_ERROR
import com.app.common.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.IOException

abstract class NetworkBoundRepository<RESPONSE>(private val mNetworkErrorProvider: NetworkErrorProvider) {

    fun asFlow() : Flow<DataState<RESPONSE>> {
        return flow<DataState<RESPONSE>> {
            val response = fetchFromRemote()
            if(response.isSuccessful && response.body() != null) {
                response.body()?.let { emit(DataState.success(it)) }
            } else {
                if(response.code() == API_RATE_LIMIT_ERROR) {
                    emit(DataState.error(mNetworkErrorProvider.unableToGetTheResponse()))
                } else {
                    emit(DataState.error(mNetworkErrorProvider.somethingWentWrong()))
                }
            }
        }.catch {
            if(it.cause is IOException) {
                emit(DataState.error(mNetworkErrorProvider.internetConnectionMessage()))
            } else {
                emit(DataState.error(it.message ?: mNetworkErrorProvider.somethingWentWrong()))
            }
        }.flowOn(Dispatchers.IO)
    }

    abstract suspend fun fetchFromRemote() : Response<RESPONSE>
}