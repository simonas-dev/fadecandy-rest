package dev.simonas.fadecandyrest

import dev.simonas.fadecandyrest.contracts.FadecandyContract
import dev.simonas.fadecandyrest.controllers.FadecandyController
import dev.simonas.fadecandyrest.services.*
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.Locations
import io.ktor.routing.*
import io.ktor.util.pipeline.PipelinePhase

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
    fadecandy: FadecandyContract = FadecandyController()
) {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
    install(Locations)
    install(Routing) {
        installFadecandyService(fadecandy)
    }
    install(DefaultHeaders) {
        header("Access-Control-Allow-Origin", "http://localhost:8081");
        header("Access-Control-Allow-Credentials", "true");
        header("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        header("Access-Control-Max-Age", "3600");
    }
}

