package com.github.insanusmokrassar.TelegramBotAPI.requests.send.media

import com.github.insanusmokrassar.TelegramBotAPI.bot.RequestsExecutor
import com.github.insanusmokrassar.TelegramBotAPI.requests.abstracts.*
import com.github.insanusmokrassar.TelegramBotAPI.requests.send.abstracts.*
import com.github.insanusmokrassar.TelegramBotAPI.requests.send.media.base.*
import com.github.insanusmokrassar.TelegramBotAPI.types.*
import com.github.insanusmokrassar.TelegramBotAPI.types.ParseMode.ParseMode
import com.github.insanusmokrassar.TelegramBotAPI.types.ParseMode.parseModeField
import com.github.insanusmokrassar.TelegramBotAPI.types.buttons.KeyboardMarkup
import com.github.insanusmokrassar.TelegramBotAPI.types.files.AnimationFile
import com.github.insanusmokrassar.TelegramBotAPI.types.files.PhotoSize
import com.github.insanusmokrassar.TelegramBotAPI.types.message.abstracts.ContentMessage
import com.github.insanusmokrassar.TelegramBotAPI.types.message.abstracts.TelegramBotAPIMessageDeserializationStrategyClass
import com.github.insanusmokrassar.TelegramBotAPI.types.message.content.media.AnimationContent
import com.github.insanusmokrassar.TelegramBotAPI.utils.mapOfNotNull
import kotlinx.serialization.*

fun SendAnimation(
    chatId: ChatIdentifier,
    animation: InputFile,
    thumb: InputFile? = null,
    caption: String? = null,
    parseMode: ParseMode? = null,
    duration: Long? = null,
    width: Int? = null,
    height: Int? = null,
    disableNotification: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    replyMarkup: KeyboardMarkup? = null
): Request<ContentMessage<AnimationContent>> {
    val animationAsFileId = (animation as? FileId) ?.fileId
    val animationAsFile = animation as? MultipartFile
    val thumbAsFileId = (thumb as? FileId) ?.fileId
    val thumbAsFile = thumb as? MultipartFile

    val data = SendAnimationData(
        chatId,
        animationAsFileId,
        thumbAsFileId,
        caption,
        parseMode,
        duration,
        width,
        height,
        disableNotification,
        replyToMessageId,
        replyMarkup
    )

    return if (animationAsFile == null && thumbAsFile == null) {
        data
    } else {
        MultipartRequestImpl(
            data,
            SendAnimationFiles(animationAsFile, thumbAsFile)
        )
    }
}

private val commonResultDeserializer: DeserializationStrategy<ContentMessage<AnimationContent>>
    = TelegramBotAPIMessageDeserializationStrategyClass()

@Serializable
data class SendAnimationData internal constructor(
    @SerialName(chatIdField)
    override val chatId: ChatIdentifier,
    @SerialName(animationField)
    val animation: String? = null,
    @SerialName(thumbField)
    override val thumb: String? = null,
    @SerialName(captionField)
    override val text: String? = null,
    @SerialName(parseModeField)
    override val parseMode: ParseMode? = null,
    @SerialName(durationField)
    override val duration: Long? = null,
    @SerialName(widthField)
    override val width: Int? = null,
    @SerialName(heightField)
    override val height: Int? = null,
    @SerialName(disableNotificationField)
    override val disableNotification: Boolean = false,
    @SerialName(replyToMessageIdField)
    override val replyToMessageId: MessageIdentifier? = null,
    @SerialName(replyMarkupField)
    override val replyMarkup: KeyboardMarkup? = null
) : DataRequest<ContentMessage<AnimationContent>>,
    SendMessageRequest<ContentMessage<AnimationContent>>,
    ReplyingMarkupSendMessageRequest<ContentMessage<AnimationContent>>,
    TextableSendMessageRequest<ContentMessage<AnimationContent>>,
    ThumbedSendMessageRequest<ContentMessage<AnimationContent>>,
    DuratedSendMessageRequest<ContentMessage<AnimationContent>>,
    SizedSendMessageRequest<ContentMessage<AnimationContent>>
{
    init {
        text ?.let {
            if (it.length !in captionLength) {
                throw IllegalArgumentException("Caption must be in $captionLength range")
            }
        }
    }

    override fun method(): String = "sendAnimation"
    override val resultDeserializer: DeserializationStrategy<ContentMessage<AnimationContent>>
        get() = commonResultDeserializer
    override val requestSerializer: SerializationStrategy<*>
        get() = serializer()
}

data class SendAnimationFiles internal constructor(
    val animation: MultipartFile? = null,
    val thumb: MultipartFile? = null
) : Files by mapOfNotNull(
    animationField to animation,
    thumbField to thumb
)

suspend fun RequestsExecutor.sendAnimation(
    chatId: ChatIdentifier,
    animation: FileId,
    thumb: FileId? = null,
    text: String? = null,
    parseMode: ParseMode? = null,
    duration: Long? = null,
    width: Int? = null,
    height: Int? = null,
    disableNotification: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    replyMarkup: KeyboardMarkup? = null
) = execute(
    SendAnimationData(
        chatId,
        animation.fileId,
        thumb ?.fileId,
        text,
        parseMode,
        duration,
        width,
        height,
        disableNotification,
        replyToMessageId,
        replyMarkup
    )
)

suspend fun RequestsExecutor.sendAnimation(
    chatId: ChatIdentifier,
    animation: AnimationFile,
    thumb: PhotoSize? = animation.thumb,
    text: String? = null,
    parseMode: ParseMode? = null,
    disableNotification: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    replyMarkup: KeyboardMarkup? = null
) = sendAnimation(
    chatId, animation.fileId, thumb ?.fileId, text, parseMode, animation.duration, animation.width, animation.height, disableNotification, replyToMessageId, replyMarkup
)

suspend fun RequestsExecutor.sendAnimation(
    chatId: ChatIdentifier,
    animation: MultipartFile,
    thumb: FileId? = null,
    text: String? = null,
    parseMode: ParseMode? = null,
    duration: Long? = null,
    width: Int? = null,
    height: Int? = null,
    disableNotification: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    replyMarkup: KeyboardMarkup? = null
) = execute(
    MultipartRequestImpl(
        SendAnimationData(
            chatId, null, thumb ?.fileId, text, parseMode, duration, width, height, disableNotification, replyToMessageId, replyMarkup
        ),
        SendAnimationFiles(animation)
    )
)

suspend fun RequestsExecutor.sendAnimation(
    chatId: ChatIdentifier,
    animation: MultipartFile,
    thumb: MultipartFile? = null,
    text: String? = null,
    parseMode: ParseMode? = null,
    duration: Long? = null,
    width: Int? = null,
    height: Int? = null,
    disableNotification: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    replyMarkup: KeyboardMarkup? = null
) = execute(
    MultipartRequestImpl(
        SendAnimationData(
            chatId, null, null, text, parseMode, duration, width, height, disableNotification, replyToMessageId, replyMarkup
        ),
        SendAnimationFiles(animation, thumb)
    )
)

suspend fun RequestsExecutor.sendAnimation(
    chatId: ChatIdentifier,
    animation: FileId,
    thumb: MultipartFile,
    text: String? = null,
    parseMode: ParseMode? = null,
    duration: Long? = null,
    width: Int? = null,
    height: Int? = null,
    disableNotification: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    replyMarkup: KeyboardMarkup? = null
) = execute(
    MultipartRequestImpl(
        SendAnimationData(
            chatId, animation.fileId, null, text, parseMode, duration, width, height, disableNotification, replyToMessageId, replyMarkup
        ),
        SendAnimationFiles(null, thumb)
    )
)

suspend fun RequestsExecutor.sendAnimation(
    chatId: ChatIdentifier,
    animation: MultipartFile,
    thumb: PhotoSize? = null,
    text: String? = null,
    parseMode: ParseMode? = null,
    duration: Long? = null,
    width: Int? = null,
    height: Int? = null,
    disableNotification: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    replyMarkup: KeyboardMarkup? = null
) = sendAnimation(
    chatId, animation, thumb ?.fileId , text, parseMode, duration, width, height, disableNotification, replyToMessageId, replyMarkup
)

suspend fun RequestsExecutor.sendAnimation(
    chatId: ChatIdentifier,
    animation: AnimationFile,
    thumb: MultipartFile,
    text: String? = null,
    parseMode: ParseMode? = null,
    disableNotification: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    replyMarkup: KeyboardMarkup? = null
) = sendAnimation(
    chatId, animation.fileId, thumb, text, parseMode, animation.duration, animation.width, animation.height, disableNotification, replyToMessageId, replyMarkup
)
