package dev.simonas.fadecandyrest

import dev.simonas.fadecandyrest.contracts.FadecandyContract
import dev.simonas.fadecandyrest.models.FcColor
import dev.simonas.fadecandyrest.models.FcDevice
import dev.simonas.fadecandyrest.models.FcDeviceMapping
import dev.simonas.fadecandyrest.models.FcServerState
import dev.simonas.models.FcConfig

class MockFadecandy: FadecandyContract {

    var getConfigCallCount: Int = 0
        private set(value) { field = value}
    var setConfigCallCount: Int = 0
        private set(value) { field = value}
    var restartCallCount: Int = 0
        private set(value) { field = value}
    var startCallCount: Int = 0
        private set(value) { field = value}
    var stopCallCount: Int = 0
        private set(value) { field = value}
    var getStateCallCount: Int = 0
        private set(value) { field = value}

    private var config: FcConfig = FcConfig.defaultConfig.copy(
        color = FcColor(
            gamma = 2.5f,
            whitepoint = listOf(1f, 1f, 1f),
            linearCutoff = 0f,
            linearSlope = 1.2f
        ),
        devices = listOf(
            FcDeviceMapping(
                type = "fadecandy",
                dither = true,
                interpolate = true,
                serial = "EEXSBPQWRVBYWQHJ",
                map = listOf(
                    listOf(0, 0, 0, 512)
                ))
        )
    )

    private var state: FcServerState = FcServerState(
        isRunning = false,
        connectedDevices = setOf(
            FcDevice("test_device", "test_serial", "0.0.0")
        )
    )

    override fun getConfig(): Result<FcConfig> {
        return tryToResult {
            getConfigCallCount++
            config
        }
    }

    override fun setConfig(config: FcConfig): Result<FcConfig> {
        return tryToResult {
            setConfigCallCount++
            this.config = config
            this.config
        }
    }

    override fun restart(): Result<Unit> {
        return tryToResult {
            restartCallCount++
            state = state.copy(isRunning = false)
            state = state.copy(isRunning = true)
            Unit
        }
    }

    override fun start(): Result<Unit> {
        return tryToResult {
            startCallCount++
            state = state.copy(isRunning = true)
        }
    }

    override fun stop(): Result<Unit> {
        return tryToResult {
            stopCallCount++
            state = state.copy(isRunning = false)
        }
    }

    override fun getState(): Result<FcServerState> {
        return tryToResult {
            getStateCallCount++
            state
        }
    }
}