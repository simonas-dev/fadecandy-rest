package dev.simonas.fadecandyrest

import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FadecandyServiceTest {

    @Test
    fun `GET Fadecandy`() {
        val device = MockFadecandy()
        testApp(device) {
            val uri = getLocationHref(FadecandyLocation())
            handleRequest(HttpMethod.Get, uri).apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(1, device.getStateCallCount)
            }
        }
    }

    @Test
    fun `GET FadecandyRestart`() {
        val device = MockFadecandy()
        testApp(device) {
            val uri = getLocationHref(FadecandyLocation.Restart())
            handleRequest(HttpMethod.Get, uri).apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(1, device.restartCallCount)
            }
        }
    }

    @Test
    fun `GET FadecandyStart`() {
        val device = MockFadecandy()
        testApp(device) {
            val uri = getLocationHref(FadecandyLocation.Start())
            handleRequest(HttpMethod.Get, uri).apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(1, device.startCallCount)
            }
        }
    }

    @Test
    fun `GET FadecandyStop`() {
        val device = MockFadecandy()
        testApp(device) {
            val uri = getLocationHref(FadecandyLocation.Stop())
            handleRequest(HttpMethod.Get, uri).apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(1, device.stopCallCount)
            }
        }
    }

    @Test
    fun `GET FadecandyConfig`() {
        val device = MockFadecandy()
        testApp(device) {
            val uri = getLocationHref(FadecandyLocation.Config())
            handleRequest(HttpMethod.Get, uri).apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(1, device.getConfigCallCount)
            }
        }
    }

    @Test
    fun `POST FadecandyConfig`() {
        val device = MockFadecandy()
        testApp(device) {
            val uniqueSerial = "really-very-unique-serial-code-trust-me"
            val deviceMapping: String = """
            {
                "listen": {
                    "ip": "127.0.0.1",
                    "port": 7890
                },
                "verbose": true,
                "color": {
                    "gamma": 2.5,
                    "whitepoint": [
                        1.0,
                        1.0,
                        1.0
                    ]
                },
                "devices": [
                    {
                        "type": "FadecandyConfig_Test",
                        "dither": false,
                        "interpolate": true,
                        "map": [
                            [
                                0,
                                0,
                                0,
                                512
                            ]
                        ],
                        "serial": $uniqueSerial
                    }
                ]
            }
            """.trimIndent()
            val uri = getLocationHref(FadecandyLocation.Config())
            val call = handleRequest(
                method = HttpMethod.Post,
                uri = uri,
                setup = {
                    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    setBody(deviceMapping)
                }
            )
            call.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertTrue { response.content!!.contains(uniqueSerial) }
                assertEquals(1, device.setConfigCallCount)
            }
        }
    }
}
