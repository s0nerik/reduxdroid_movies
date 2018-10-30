package com.github.s0nerik.reduxdroid_movies.core.serializers

import kotlinx.serialization.*
import kotlinx.serialization.internal.LongDescriptor
import org.joda.time.DateTime

@Serializer(DateTimeSerializer::class)
object DateTimeSerializer : KSerializer<DateTime> {
    override val descriptor: SerialDescriptor
        get() = LongDescriptor

    override fun deserialize(input: Decoder): DateTime {
        return DateTime(input.decodeLong())
    }

    override fun serialize(output: Encoder, obj: DateTime) {
        output.encodeLong(obj.millis)
    }
}