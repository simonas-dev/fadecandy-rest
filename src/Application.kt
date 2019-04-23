package dev.simonas

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
  install(Routing) {
        get("/") {
            val process = Runtime.getRuntime().exec("pwd")
            process.waitFor()
            val outByteArr = process.inputStream.readBytes()
            val outString = String(outByteArr)
            call.respondText("pwd: $outString", ContentType.Text.Html)
        }
    }
}

