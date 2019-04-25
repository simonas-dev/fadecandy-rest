package dev.simonas.fadecandyrest.services

import dev.simonas.fadecandyrest.*
import dev.simonas.fadecandyrest.contracts.FadecandyContract
import dev.simonas.models.FcConfig
import io.ktor.application.call
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.routing.Routing

@KtorExperimentalLocationsAPI
fun Routing.installFadecandyService(controller: FadecandyContract) {

    get<FadecandyLocation> {
        call.respondWithResult(controller.getState())
    }

    get<FadecandyLocation.Restart> {
        call.respondWithResult(controller.restart())
    }

    get<FadecandyLocation.Start> {
        call.respondWithResult(controller.start())
    }

    get<FadecandyLocation.Stop> {
        call.respondWithResult(controller.stop())
    }

    get<FadecandyLocation.Config> {
        call.respondWithResult(controller.getConfig())
    }
    post<FadecandyLocation.Config> {
        val model = call.receive(FcConfig::class)
        call.respondWithResult(controller.setConfig(model))
    }
}