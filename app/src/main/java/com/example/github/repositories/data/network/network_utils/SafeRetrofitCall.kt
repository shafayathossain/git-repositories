package com.example.github.repositories.data.network.network_utils

import com.example.github.repositories.data.network.exception.Failure
import com.example.github.repositories.data.network.exception.parseException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

suspend inline fun <T> executeRetrofitCall(
    ioDispatcher: CoroutineDispatcher,
    crossinline retrofitCall: suspend () -> T
): Either<Failure, T> {
    return withContext(ioDispatcher) {
        try {
            return@withContext retrofitCall().toSuccess()
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext e.parseException().toError()
        }
    }
}