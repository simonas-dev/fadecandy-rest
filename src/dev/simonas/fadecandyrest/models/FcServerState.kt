package dev.simonas.fadecandyrest.models

data class FcServerState(
        val isRunning: Boolean,
        val connectedDevices: List<FcDevice>
)