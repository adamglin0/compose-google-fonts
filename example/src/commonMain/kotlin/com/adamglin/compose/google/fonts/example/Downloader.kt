package com.adamglin.compose.google.fonts.example

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.delay
import kotlinx.io.IOException
import kotlin.time.Duration.Companion.seconds

class Downloader {
    val client = HttpClient()
    val maxRetries = 3
    val retryDelay = 2.seconds
    suspend fun downloadBytes(url: String): ByteArray? {
        var attempt = 0
        while (attempt <= maxRetries) {
            println("#$attempt download $url")
            try {
                val response: HttpResponse = client.get(url)
                return response.body()
            } catch (e: Exception) {
                if (isRetryableError(e) && attempt < maxRetries) {
                    delay(retryDelay)
                    attempt++
                } else {
                    return null
                }
            } finally {
                println("download done")
            }
        }
        println("download result is null")
        return null
    }

    private fun isRetryableError(error: Throwable): Boolean {
        return when (error) {
            is IOException -> true
            is io.ktor.client.plugins.HttpRequestTimeoutException -> true
            is io.ktor.client.network.sockets.SocketTimeoutException -> true
            is io.ktor.client.network.sockets.ConnectTimeoutException -> true
            else -> false
        }
    }
}