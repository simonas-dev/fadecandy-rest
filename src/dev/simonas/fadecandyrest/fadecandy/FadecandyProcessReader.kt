package dev.simonas.fadecandyrest.fadecandy

import dev.simonas.models.FcDevice

class FadecandyProcessReader() {

    sealed class Event {
        class Running(): Event()
        class Error(val message: String): Event()
        class Connected(val device: FcDevice): Event()
        class Disconnected(val device: FcDevice): Event()
    }

    private val connectivityRegex = "(USB device\\s)(.*)(\\(Serial#\\s)(.*)(,\\sVersion\\s)(.*)(\\) )(.*)(.)".toRegex()

    fun parseLog(line: String) : Event {
        return when {
            line.contains("NOTICE: Server listening on") -> Event.Running()
            line.contains("error", true) -> Event.Error(line)
            connectivityRegex.matches(line) -> {
                val groups = connectivityRegex.find(line)!!.groups
                val device = FcDevice(
                    type = groups[2]!!.value,
                    serial = groups[4]!!.value,
                    version = groups[6]!!.value
                )
                val event = groups[8]!!.value
                when (event) {
                    "attached" -> Event.Connected(device)
                    "removed" -> Event.Disconnected(device)
                    else -> Event.Error("Unexpected event")
                }
            }
            else -> Event.Error("Unexpected Log Message")
        }
    }
}