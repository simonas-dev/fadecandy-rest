package dev.simonas.fadecandyrest.models

/**
 *
 * JSON Example
 * {
 *     "type": "fadecandy",
 *     "serial": "EEXSBPQWRVBYWQHJ",
 *     "version": "1.07"
 * }
 */
data class FcDevice(
    val type: String,
    val serial: String,
    val version: String
)