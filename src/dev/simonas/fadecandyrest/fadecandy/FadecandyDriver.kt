package dev.simonas.fadecandyrest.fadecandy

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dev.simonas.fadecandyrest.models.FcDeviceAddress
import dev.simonas.fadecandyrest.models.FcDeviceAddressAdapter
import dev.simonas.fadecandyrest.log
import dev.simonas.fadecandyrest.tryToResult
import dev.simonas.models.FcConfig
import dev.simonas.fadecandyrest.models.FcDevice
import kotlinx.coroutines.*
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

class FadecandyDriver {

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(
            FcDeviceAddress::class.java,
            FcDeviceAddressAdapter()
        )
        .setPrettyPrinting()
        .create()

    private var fcServerProcess: Process? = null
    private var logJob: Job? = null

    var config: FcConfig = FcConfig.defaultConfig
    var onDeviceConnected: (device : FcDevice) -> Unit = {}
    var onDeviceDisconnected: (device : FcDevice) -> Unit = {}

    init {
        kill()
    }

    fun start(): Result<Unit> {
        return tryToResult {
            val configJSON = gson.toJson(config)
            val filePath = "/tmp/fadecandy_rest_config.json"
            val configFile = File(filePath)
            configFile.writeText(configJSON)

            val parser = FadecandyProcessReader()

            val process = ProcessBuilder()
                .command("fcserver", filePath)
                .start()

            Runtime.getRuntime().addShutdownHook(Thread {
                process.destroyForcibly()
            })

            val reader = process.errorStream.bufferedReader()
            var line: String? = reader.readLine()

            if (line == null) {
                throw Throwable("Startup Failed! ${config.listen}")
            }

            val startupEvent = parser.parseLog(line)
            if (startupEvent !is FadecandyProcessReader.Event.Running) {
                throw Throwable(line)
            }

            logJob = GlobalScope.launch (Dispatchers.IO)
            {
                while (line != null) {
                    log("errorStream: $line")
                    val event: FadecandyProcessReader.Event? = line?.let { parser.parseLog(it) }
                    event?.let { handleEvent(it) }
                    try {
                        line = reader.readLine()
                    } catch (e: IOException) {
                        // I don't care.
                        line = null
                    }
                }
            }

            fcServerProcess = process

            Unit
        }
    }

    fun kill(): Result<Unit> {
        return tryToResult {
            logJob?.cancel()
            fcServerProcess?.destroyForcibly()
            fcServerProcess = null
            val process = Runtime.getRuntime().exec("pkill fcserver")
            process.waitFor(5, TimeUnit.SECONDS)
            Unit
        }
    }

    private fun handleEvent(event: FadecandyProcessReader.Event) {
        when (event) {
            is FadecandyProcessReader.Event.Running -> {

            }
            is FadecandyProcessReader.Event.Error -> {
                // TODO
            }
            is FadecandyProcessReader.Event.Connected -> {
                onDeviceConnected(event.device)
            }
            is FadecandyProcessReader.Event.Disconnected -> {
                onDeviceDisconnected(event.device)
            }
        }
    }
}