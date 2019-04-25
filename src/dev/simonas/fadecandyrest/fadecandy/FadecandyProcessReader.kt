package dev.simonas.fadecandyrest.fadecandy

import dev.simonas.fadecandyrest.log
import dev.simonas.models.FcDevice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FadecandyProcessReader(
    val process: Process,
    val onLogLine: (String) -> Unit,
    val onDeviceConnected: (FcDevice) -> Unit,
    val onDeviceDisconnected: (FcDevice) -> Unit,
    val onError: (FadecandyError) -> Unit
) {
    private val connectivityRegex = "(USB device\\s)(.*)(\\(Serial#\\s)(.*)(,\\sVersion\\s)(.*)(\\) )(.*)(.)".toRegex()
    private val bindingToPortRegex = "ERROR on binding to port".toRegex()

    init {
        GlobalScope.launch(Dispatchers.IO) {
            process.errorStream.reader().forEachLine {
                log("errorStream: $it")
                processError(it)
            }
        }
    }

    private fun processError(line: String) {
        onLogLine(line)
        when {
            bindingToPortRegex.matches(line) -> {
                onError(FadecandyError.AlreadyRunning())
            }
            connectivityRegex.matches(line) -> {
                val groups = connectivityRegex.find(line)!!.groups
                val device = FcDevice(
                    type = groups[2]!!.value,
                    serial = groups[4]!!.value,
                    version = groups[6]!!.value
                )
                val event = groups[8]!!.value
                when (event) {
                    "attached" -> onDeviceConnected(device)
                    "removed" -> onDeviceDisconnected(device)
                }
            }
        }
    }
}