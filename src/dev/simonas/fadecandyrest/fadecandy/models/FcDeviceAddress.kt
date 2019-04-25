package dev.simonas.fadecandyrest.fadecandy.models

import com.google.gson.*
import java.lang.reflect.Type


class FcDeviceAddress(
    val ip: String,
    val port: Int
)

/**
 * Original FadecandyLocation Server Developer decided on a funky idea
 * to using tuples when saving the address of the device.
 *
 * The server expects and sends an JSON array with two items in a specific order.
 * An example:
 * ["localhost", 8080]
 *
 * Kotlin cannot properly serialize Tuples like this it creates an JSON object
 * which is not accepted by the server.
 *
 * So this
 */
class FcDeviceAddressAdapter : JsonSerializer<FcDeviceAddress?>, JsonDeserializer<FcDeviceAddress?> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): FcDeviceAddress? {
        // This hack should mostly work.
        return try {
            FcDeviceAddress(
                ip = json!!.asJsonArray?.get(0)?.asString!!,
                port = json.asJsonArray?.get(0)?.asInt!!
            )
        } catch (e: Throwable) {
            e.printStackTrace()
            null
        }
    }

    override fun serialize(
        src: FcDeviceAddress?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonArray().apply {
            add(src?.ip)
            add(src?.port)
        }
    }
}