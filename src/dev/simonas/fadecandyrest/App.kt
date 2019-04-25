package dev.simonas.fadecandyrest

import dev.simonas.fadecandyrest.contracts.FadecandyContract
import dev.simonas.fadecandyrest.controllers.Fadecandy
import dev.simonas.fadecandyrest.services.*
import dev.simonas.models.FcConfig
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.Locations
import io.ktor.routing.*

@KtorExperimentalLocationsAPI
@Location("/") class Index

@KtorExperimentalLocationsAPI
@Location("/fc") class FadecandyLocation {
    @Location("/start") class Start
    @Location("/stop") class Stop
    @Location("/restart") class Restart
    @Location("/config") class Config
}

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalLocationsAPI
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(
    fadecandy: FadecandyContract = Fadecandy
) {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
    install(Locations)
    install(Routing) {
        installIndex()
        installFadecandyService(fadecandy)
    }
}

