package dev.inmo.tgbotapi.requests.edit.LiveLocation

import dev.inmo.tgbotapi.requests.edit.abstracts.EditInlineMessage
import dev.inmo.tgbotapi.requests.edit.abstracts.EditReplyMessage
import dev.inmo.tgbotapi.types.*
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardMarkup
import kotlinx.serialization.*

@Serializable
data class StopInlineMessageLiveLocation(
    @SerialName(inlineMessageIdField)
    override val inlineMessageId: InlineMessageIdentifier,
    @SerialName(replyMarkupField)
    override val replyMarkup: InlineKeyboardMarkup? = null
) : EditInlineMessage, EditReplyMessage {
    override fun method(): String = "stopMessageLiveLocation"
    override val requestSerializer: SerializationStrategy<*>
        get() = serializer()
}
