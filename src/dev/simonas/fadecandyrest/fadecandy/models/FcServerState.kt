package dev.simonas.fadecandyrest.fadecandy.models

import dev.simonas.models.FcDevice

data class FcServerState(
        val isRunning: Boolean,
        val connectedDevices: List<FcDevice>
)