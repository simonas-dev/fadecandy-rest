package dev.simonas.fadecandyrest.controllers

import dev.simonas.fadecandyrest.contracts.FadecandyContract
import dev.simonas.fadecandyrest.fadecandy.FadecandyDriver
import dev.simonas.fadecandyrest.models.FcDeviceAddress
import dev.simonas.fadecandyrest.models.FcServerState
import dev.simonas.fadecandyrest.tryToResult
import dev.simonas.models.FcConfig
import dev.simonas.fadecandyrest.models.FcDevice
import kotlin.random.Random

class FadecandyController(
    initialAddress: FcDeviceAddress = FcConfig.defaultConfig.listen
) : FadecandyContract {

    private val driver: FadecandyDriver = FadecandyDriver().apply {
        onDeviceConnected = { device -> handleDeviceConnection(device) }
        onDeviceDisconnected = { device ->
            handleDeviceDisconnection(
                device
            )
        }
    }

    private var state: FcServerState = FcServerState(
        connectedDevices = setOf(),
        isRunning = false
    )

    init {
        lockAddress(initialAddress)
        driver.config = driver.config.copy(
            listen = initialAddress
        )
        stop().getOrThrow()
    }

    override fun start(): Result<Unit> {
        return tryToResult {
            driver.start().getOrThrow()
            state = state.copy(isRunning = true, connectedDevices = setOf())
        }
    }

    override fun stop(): Result<Unit> {
        return tryToResult {
            driver.kill().getOrThrow()
            state = state.copy(isRunning = false, connectedDevices = setOf())
        }
    }

    override fun restart(): Result<Unit> {
        return tryToResult {
            driver.kill().getOrThrow()
            state = state.copy(isRunning = false, connectedDevices = setOf())
            driver.start().getOrThrow()
            state = state.copy(isRunning = true, connectedDevices = setOf())
            Unit
        }
    }

    override fun setConfig(config: FcConfig): Result<FcConfig> {
        lockAddress(config.listen)
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
            connectedDevices = state.connectedDevices.toMutableSet().apply {
                add(device)
            }
        )
    }

    private fun handleDeviceDisconnection(device: FcDevice) {
        state = state.copy(
            connectedDevices = state.connectedDevices.toMutableSet().apply {
                remove(device)
            }
        )
    }


    companion object {
        private val usedAddressList: MutableSet<Pair<String, Int>> = mutableSetOf()

        fun lockAddress(address: FcDeviceAddress) {
            usedAddressList.add(address.ip to address.port)
        }

        /**
         * For when it's required to acquire an unique address when doing integration tests.
         *
         * There's 2K port possibilities between these two ranges. It should be enough. For now...
         */
        fun acquireTestAddress(): FcDeviceAddress {
            var randomPort = Random.nextInt(29170, 29998)
            while (usedAddressList.any { it.second == randomPort }) {
                randomPort = Random.nextInt(38866, 39680)
            }
            return FcDeviceAddress("127.0.0.1", randomPort)
        }
    }
}
