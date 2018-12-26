package com.github.insanusmokrassar.TelegramBotAPI.types

import kotlinx.serialization.*
import org.joda.time.DateTime
import java.util.concurrent.TimeUnit

@Serializable(TelegramDateSerializer::class)
class TelegramDate(
    private val date: Long
) {
    constructor(dateTime: DateTime) : this(
        TimeUnit.MILLISECONDS.toSeconds(dateTime.millis)
    )

    @Transient
    val asDate: DateTime = DateTime(
        TimeUnit.SECONDS.toMillis(date)
    )
}

fun DateTime.toTelegramDate(): TelegramDate = TelegramDate(this)

@Serializer(TelegramDate::class)
internal class TelegramDateSerializer: KSerializer<TelegramDate> {
    override fun serialize(output: Encoder, obj: TelegramDate) {
        output.encodeLong(
            TimeUnit.MILLISECONDS.toSeconds(obj.asDate.millis)
        )
    }

    override fun deserialize(input: Decoder): TelegramDate {
        return TelegramDate(
            input.decodeLong()
        )
    }
}
