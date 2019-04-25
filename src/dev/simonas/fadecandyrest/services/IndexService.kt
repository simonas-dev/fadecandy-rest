package dev.simonas.fadecandyrest.services

import dev.simonas.fadecandyrest.Index
import io.ktor.application.call
import io.ktor.locations.get
import io.ktor.response.respondText
import io.ktor.routing.Routing

fun Routing.installIndex() {
    get<Index> {
       call.respondText { "Hello, the server is up and running!" }
    }
}