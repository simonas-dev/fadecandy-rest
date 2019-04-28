package dev.simonas.fadecandyrest.models

/**
 * JSON Example
 *
 * "color": {
 *     "gamma": 2.5,
 *     "whitepoint": [ 1, 1, 1 ],
 *     "linearSlope": 1.2,
 *     "linearCutoff": 0.0
 * },
 */
data class FcColor(
        val gamma: Float,
        val whitepoint: List<Float>,
        val linearSlope: Float? = null,
        val linearCutoff: Float? = null
)