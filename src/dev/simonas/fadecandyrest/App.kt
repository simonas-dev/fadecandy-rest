package dev.simonas.fadecandyrest

import dev.simonas.fadecandyrest.controllers.Fadecandy
import dev.simonas.fadecandyrest.fadecandy.models.FcDeviceAddress
import dev.simonas.fadecandyrest.fadecandy.models.FcDeviceAddressAdapter
import dev.simonas.fadecandyrest.services.*
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.locations.Location
import io.ktor.locations.Locations
import io.ktor.routing.*

@Location("/") class Index

@Location("/fc") class FadecandyState
@Location("/fc/start") class FadecandyStart
@Location("/fc/stop") class FadecandyStop
@Location("/fc/restart") class FadecandyRestart
@Location("/fc/config") class FadecandyConfig

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
    install(Locations)
    install(Routing) {
        installIndex()
        installFadecandyService(Fadecandy)
    }
}

