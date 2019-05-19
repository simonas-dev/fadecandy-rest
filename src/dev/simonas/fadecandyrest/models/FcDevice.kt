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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FcDevice

        if (serial != other.serial) return false

        return true
    }

    override fun hashCode(): Int {
        return serial.hashCode()
    }
}