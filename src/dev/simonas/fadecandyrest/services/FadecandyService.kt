package dev.simonas.fadecandyrest.services

import dev.simonas.fadecandyrest.*
import dev.simonas.fadecandyrest.contracts.FadecandyContract
import dev.simonas.models.FcConfig
import io.ktor.application.call
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing

fun Routing.installFadecandyService(controller: FadecandyContract) {

    get<FadecandyState> {
        call.respond(controller.getState())
    }

    get<FadecandyRestart> {
        call.respond(controller.restart())
    }

    get<FadecandyStart> {
        call.respond(controller.start())
    }

    get<FadecandyStop> {
        call.respond(controller.stop())
    }

    get<FadecandyConfig> {
        call.respond(controller.getConfig())
    }
    post<FadecandyConfig> {
        val model = call.receive(FcConfig::class)
        call.respond(controller.setConfig(model))
    }
}