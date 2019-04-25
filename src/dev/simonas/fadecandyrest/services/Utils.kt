package dev.simonas.fadecandyrest.services

import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond

suspend fun <T> ApplicationCall.respondWithResult(result: Result<T>) {
    val response = Response(result)
    val statusCode = when {
        result.isSuccess ->HttpStatusCode.OK
        else -> HttpStatusCode.InternalServerError
    }
    respond(statusCode, response)
}