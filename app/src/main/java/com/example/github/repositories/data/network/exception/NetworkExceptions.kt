package com.example.github.repositories.data.network.exception

import com.example.github.repositories.data.network.exception.Failure

object NoInternetConnectionError : Failure("No internet connection found")

object TimeOut : Failure("Connection timeout")

object NetworkConnectionLostSuddenly : Failure("Network connection lost")

object SSLError : Failure("SSL connection error")

/**
 * If your service return some custom error use this with the given params you expect.
 */
data class ServiceBodyFailure(
    val internalCode: Int,
    val internalMessage: String?
) : Failure()