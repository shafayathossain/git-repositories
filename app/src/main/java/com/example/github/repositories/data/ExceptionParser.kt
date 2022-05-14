package com.example.github.repositories.data

import com.google.gson.Gson
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException
import javax.net.ssl.SSLHandshakeException

fun Throwable.parseException(): Failure {
    return when (this) {
        is UnknownHostException -> NoInternetConnectionError
        is SocketTimeoutException -> TimeOut
        is SSLException -> NetworkConnectionLostSuddenly
        is SSLHandshakeException -> SSLError
        is HttpException -> {
            val errorService =
                Gson().fromJson(response()?.errorBody()?.string(), ResponseError::class.java)
            if (errorService != null) {
                Failure(
                    message = errorService.message
                )
            } else {
                Failure(
                    message = "Service ERROR BODY does not match."
                )
            }
        }
        else -> Failure(
            message = message ?: "Exception not handled caused an Unknown failure"
        )
    }
}