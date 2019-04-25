package dev.simonas.models

import dev.simonas.fadecandyrest.fadecandy.models.FcDeviceAddress

/**
 *
 * JSON Example
 * {
 *     "listen": [ "127.0.0.1", 7890 ],
 *     "relay": null,
 *     "verbose": true,
 *     "color": { ... },
 *     "devices": [ ... ]
 * }
 */
data class FcConfig (
    val listen: FcDeviceAddress,
    val relay: FcDeviceAddress?,
    val verbose: Boolean,
    val color: FcColor,
    val devices: List<FcDeviceMapping>
) {

    companion object {
        val defaultConfig: FcConfig
            get() = FcConfig(
                listen = FcDeviceAddress("127.0.0.1", 7890),
                relay = null,
                verbose = true,
                color = FcColor(
                    gamma = 2.5f,
                    whitepoint = listOf(1f, 1f, 1f)
                ),
                devices = listOf(
                    FcDeviceMapping(
                        type = "fadecandy",
                        map = listOf(listOf(0, 0, 0, 512)),
                        dither = false,
                        interpolate = true,
                        serial = null
                    )
                )
            )
    }
}
