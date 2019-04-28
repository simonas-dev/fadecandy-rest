package dev.simonas.fadecandyrest

import dev.simonas.fadecandyrest.contracts.FadecandyContract
import dev.simonas.fadecandyrest.controllers.FadecandyController
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.restassured.RestAssured
import io.restassured.response.ResponseBodyExtractionOptions
import io.restassured.specification.RequestSpecification
import java.util.concurrent.TimeUnit
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

abstract class BaseIntegrationTest {

    companion object {
        private val fadecandy: FadecandyContract = FadecandyController(
            initialAddress = FadecandyController.acquireTestAddress()
        )

        private val server: ApplicationEngine by lazy {
            embeddedServer(
                factory = Netty,
                port = 8080,
                host = "localhost",
                module = {
                    module(fadecandy)
                }
            ).apply {
                start()
                RestAssured.baseURI = "http://localhost"
                RestAssured.port = 8080
                Runtime.getRuntime().addShutdownHook(Thread {
                    server.stop(0, 0, TimeUnit.SECONDS)
                })
            }
        }
    }

    @BeforeTest
    fun before() {
        // Interact server for it to be lazily loaded if required.
        server
    }

    @AfterTest
    fun after() {
        fadecandy.stop()
    }

    protected fun RequestSpecification.When(): RequestSpecification {
        return this.`when`()
    }

    // allows response.to<Widget>() -> Widget instance
    protected inline fun <reified T> ResponseBodyExtractionOptions.to(): T {
        return this.`as`(T::class.java)
    }
}