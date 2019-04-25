package dev.simonas.models

/**
 *
 * JSON Example
 * {
 *     "type": "fadecandy",
 *     "dither": false,
 *     "interpolate": true,
 *     "serial": "EEXSBPQWRVBYWQHJ",
 *     "map": [
 *         [0, 64, 0, 32],
 *         [0, 160, 0, 32],
 *         [0, 256, 64, 32],
 *         [0, 352, 64, 32],
 *         [0, 448, 128, 32],
 *         [0, 544, 128, 32],
 *         [0, 640, 192, 32],
 *         [0, 736, 192, 32],
 *         [0, 832, 256, 32],
 *         [0, 928, 256, 32],
 *         [0, 1024, 320, 32],
 *         [0, 1120, 320, 32],
 *         [0, 1216, 384, 32],
 *         [0, 1312, 384, 32],
 *         [0, 1408, 448, 32],
 *         [0, 1504, 448, 32]
 *     ]
 * }
 */
data class FcDeviceMapping(
    val type: String,
    val dither: Boolean,
    val interpolate: Boolean,
    val serial: String?,
    val map: List<List<Int>>
)