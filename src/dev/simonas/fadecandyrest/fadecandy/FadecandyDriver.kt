package dev.simonas.fadecandyrest.fadecandy

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dev.simonas.fadecandyrest.fadecandy.models.FcDeviceAddress
import dev.simonas.fadecandyrest.fadecandy.models.FcDeviceAddressAdapter
import dev.simonas.fadecandyrest.log
import dev.simonas.fadecandyrest.tryToResult
import dev.simonas.models.FcConfig
import dev.simonas.models.FcDevice
import java.io.File

class FadecandyDriver() {

    companion object {
        private const val LOG_TAIL_LENGTH = 15;
    }

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(
            FcDeviceAddress::class.java,
            FcDeviceAddressAdapter()
        )
        .setPrettyPrinting()
        .create()

    private var fcServerProcess: Process? = null

    var config: FcConfig = FcConfig.defaultConfig

    /**
     * Last [LOG_TAIL_LENGTH] amount of messages received from the server.
     */
    private var logTail: List<String> = listOf()
        set(value) {
            field = value.takeLast(LOG_TAIL_LENGTH)
        }


    var onDeviceConnected: (device : FcDevice) -> Unit = { _ -> }
    var onDeviceDisconnected: (device : FcDevice) -> Unit = { _ -> }
    var onError: (error : FadecandyError) -> Unit = { _ -> }

    init {
        kill()
    }

    fun start(): Result<Unit> {
        return tryToResult {
            val configJSON = gson.toJson(config)
            val filePath = "/tmp/fadecandy_rest_config.json"
            val configFile = File(filePath)
            configFile.writeText(configJSON)
            val command = "fcserver"
            val process = Runtime.getRuntime().exec(command).apply {
                FadecandyProcessReader(
                    this,
                    onLogLine = { line ->
                        logTail = logTail.toMutableList().apply { add(line) }
                    },
                    onDeviceConnected = onDeviceConnected,
                    onDeviceDisconnected = onDeviceDisconnected,
                    onError = onError
                )
            }
            fcServerProcess = process
            if (!process.isAlive) {
                throw Throwable(logTail.joinToString("\n"))
            }
        }
    }

    fun kill(): Result<Unit> {
        return tryToResult {
            fcServerProcess = null
            Runtime.getRuntime().exec("pkill fcserver")
            Unit
        }
    }
}