package dev.simonas.fadecandyrest

import dev.simonas.fadecandyrest.contracts.FadecandyContract
import dev.simonas.fadecandyrest.controllers.FadecandyController
import dev.simonas.fadecandyrest.fadecandy.models.FcDeviceAddress
import dev.simonas.models.FcConfig
import kotlin.test.*

class FadecandyTest() {

    private var fadecandy: FadecandyContract = FadecandyController(
        initialAddress = FadecandyController.acquireTestAddress()
    )

    @BeforeTest
    fun before() {
        val result = fadecandy.stop()
        assertTrue { result.isSuccess }
    }

    @AfterTest
    fun after() {
        val result = fadecandy.stop()
        assertTrue { result.isSuccess }
    }

    @Test
    fun `Test getConfig()`() {
        assertTrue { fadecandy.getConfig().isSuccess }
    }

    @Test
    fun `Test setConfig()`() {
        val validResult = fadecandy.setConfig(FcConfig.defaultConfig.copy(
            listen = FcDeviceAddress("127.0.01", 9999)
        ))
        assertTrue { validResult.isSuccess }

        val invalidResult = fadecandy.setConfig(FcConfig.defaultConfig.copy(
            listen = FcDeviceAddress("localhost", 9999)
        ))
        assertTrue { invalidResult.isFailure }
    }

    @Test
    fun `Test restart()`() {
        val result = fadecandy.restart()
        assertTrue { result.isSuccess }
    }

    @Test
    fun `Test start()`() {
        val result = fadecandy.start()
        assertTrue { result.isSuccess }
    }

    @Test
    fun `Test stop()`() {
        fadecandy.start()
        val result = fadecandy.stop()
        assertTrue { result.isSuccess }
    }

    @Test
    fun `Test getState()`() {
        fadecandy.stop()
        val offState = fadecandy.getState().getOrThrow()
        assertFalse { offState.isRunning }
        fadecandy.start()
        val onState = fadecandy.getState().getOrThrow()
        assertTrue { onState.isRunning }
    }

}
