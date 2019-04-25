package dev.simonas.fadecandyrest.controllers

import dev.simonas.fadecandyrest.contracts.FadecandyContract
import dev.simonas.fadecandyrest.fadecandy.FadecandyDriver
import dev.simonas.fadecandyrest.fadecandy.FadecandyError
import dev.simonas.fadecandyrest.log
import dev.simonas.fadecandyrest.fadecandy.models.FcServerState
import dev.simonas.fadecandyrest.tryToResult
import dev.simonas.models.FcConfig
import dev.simonas.models.FcDevice

object Fadecandy : FadecandyContract {

    private val driver: FadecandyDriver = FadecandyDriver()
        .apply {
        onDeviceConnected = { device -> handleDeviceConnection(device) }
        onDeviceDisconnected = { device ->
            handleDeviceDisconnection(
                device
            )
        }
        onError = { error -> handleDeviceError(error) }
    }

    private var state: FcServerState = FcServerState(
        connectedDevices = listOf(),
        isRunning = false
    )

    init {
        stop().getOrThrow()
    }

    override fun start(): Result<Unit> {
        val result = driver.start()
        state = state.copy(isRunning = true)
        return result
    }

    override fun stop(): Result<Unit> {
        val result = driver.kill()
        state = state.copy(isRunning = false)
        return result
    }

    override fun restart(): Result<Unit> {
        return tryToResult {
            driver.kill().getOrThrow()
            driver.start().getOrThrow()
            Unit
        }
    }

    override fun setConfig(config: FcConfig): Result<FcConfig> {
        return tryToResult {
            driver.config = config
            restart().getOrThrow()
            driver.config
        }
    }

    override fun getConfig(): Result<FcConfig> {
        return tryToResult {
            driver.config
        }
    }

    override fun getState(): Result<FcServerState> {
        return tryToResult {
            state
        }
    }

    private fun handleDeviceConnection(device: FcDevice) {
        state = state.copy(
            connectedDevices = state.connectedDevices.toMutableList().apply {
                add(device)
            }
        )
    }

    private fun handleDeviceDisconnection(device: FcDevice) {
        state = state.copy(
            connectedDevices = state.connectedDevices.toMutableList().apply {
                remove(device)
            }
        )

    }

    private fun handleDeviceError(error: FadecandyError) {
        log(error)
    }
}
