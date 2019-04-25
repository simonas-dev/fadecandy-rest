package dev.simonas.fadecandyrest.contracts

import dev.simonas.fadecandyrest.fadecandy.models.FcServerState
import dev.simonas.models.FcConfig

interface FadecandyContract {
    fun getConfig(): Result<FcConfig>
    fun setConfig(config: FcConfig): Result<FcConfig>
    fun restart(): Result<Unit>
    fun start(): Result<Unit>
    fun stop(): Result<Unit>
    fun getState(): Result<FcServerState>
}