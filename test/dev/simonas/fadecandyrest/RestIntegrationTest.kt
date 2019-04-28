package dev.simonas.fadecandyrest

import dev.simonas.fadecandyrest.models.FcServerState
import dev.simonas.fadecandyrest.services.Response
import io.ktor.http.HttpStatusCode
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.Test
import kotlin.test.assertTrue

class RestIntegrationTest: BaseIntegrationTest() {

    @Test
    fun `GET fc`() {
        val result = given()
            .contentType(ContentType.JSON)
            .When()
            .get("/fc")
            .then()
            .statusCode(HttpStatusCode.OK.value)
            .extract()
            .to<Response<FcServerState>>()

        assertTrue(result.isSuccess)
        assertTrue(result.value != null)
    }

    @Test
    fun `GET fc_start`() {
        val result = given()
            .contentType(ContentType.JSON)
            .When()
            .get("/fc/start")
            .then()
            .statusCode(HttpStatusCode.OK.value)
            .extract()
            .to<Response<FcServerState>>()

        assertTrue(result.isSuccess)
        assertTrue(result.value != null)
    }

    @Test
    fun `GET fc_stop`() {
        val result = given()
            .contentType(ContentType.JSON)
            .When()
            .get("/fc/stop")
            .then()
            .statusCode(HttpStatusCode.OK.value)
            .extract()
            .to<Response<FcServerState>>()

        assertTrue(result.isSuccess)
        assertTrue(result.value != null)
    }

    @Test
    fun `GET fc_restart`() {
        val result = given()
            .contentType(ContentType.JSON)
            .When()
            .get("/fc/restart")
            .then()
            .statusCode(HttpStatusCode.OK.value)
            .extract()
            .to<Response<FcServerState>>()

        assertTrue(result.isSuccess)
        assertTrue(result.value != null)
    }

    @Test
    fun `GET fc_config`() {
        val result = given()
            .contentType(ContentType.JSON)
            .When()
            .get("/fc/config")
            .then()
            .statusCode(HttpStatusCode.OK.value)
            .extract()
            .to<Response<FcServerState>>()

        assertTrue(result.isSuccess)
        assertTrue(result.value != null)
    }

    @Test
    fun `POST fc_config`() {
        val requestJSON = """
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
              "type": "fadecandy",
              "dither": false,
              "interpolate": true,
              "map": [
                [
                  0,
                  0,
                  0,
                  512
                ]
              ]
            }
          ]
        }
        """.trimIndent()
        val result = given()
            .contentType(ContentType.JSON)
            .When()
            .body(requestJSON)
            .post("/fc/config")
            .then()
            .statusCode(HttpStatusCode.OK.value)
            .extract()
            .to<Response<FcServerState>>()

        assertTrue(result.isSuccess)
        assertTrue(result.value != null)
    }
}