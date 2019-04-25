package dev.simonas.fadecandyrest.services

import dev.simonas.fadecandyrest.*
import dev.simonas.fadecandyrest.contracts.FadecandyContract
import dev.simonas.models.FcConfig
import io.ktor.application.call
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.routing.Routing

fun Routing.installFadecandyService(controller: FadecandyContract) {

    get<FadecandyState> {
        call.respondWithResult(controller.getState())
    }

    get<FadecandyRestart> {
        call.respondWithResult(controller.restart())
    }

    get<FadecandyStart> {
        call.respondWithResult(controller.start())
    }

    get<FadecandyStop> {
        call.respondWithResult(controller.stop())
    }

    get<FadecandyConfig> {
        call.respondWithResult(controller.getConfig())
    }
    post<FadecandyConfig> {
        val model = call.receive(FcConfig::class)
        call.respondWithResult(controller.setConfig(model))
    }
}