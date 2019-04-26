package dev.simonas.fadecandyrest

import dev.simonas.fadecandyrest.contracts.FadecandyContract
import io.ktor.locations.locations
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.withTestApplication



fun testApp(
    fadecandy: FadecandyContract = MockFadecandy(),
    test: TestApplicationEngine.() -> Unit
) {
    withTestApplication(
        moduleFunction = {
            module(fadecandy)
        },
        test = test
    )
}

fun TestApplicationEngine.getLocationHref(location: Any): String {
    return application.locations.href(location)
}
