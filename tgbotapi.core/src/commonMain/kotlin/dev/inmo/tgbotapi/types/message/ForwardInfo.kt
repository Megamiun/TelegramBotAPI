package dev.inmo.tgbotapi.types.message

import dev.inmo.tgbotapi.types.*
import dev.inmo.tgbotapi.types.chat.abstracts.ChannelChat
import dev.inmo.tgbotapi.types.chat.abstracts.SupergroupChat

sealed class ForwardInfo {
    abstract val dateOfOriginal: TelegramDate
}

data class AnonymousForwardInfo(
    override val dateOfOriginal: TelegramDate,
    val senderName: String
) : ForwardInfo()

data class UserForwardInfo(
    override val dateOfOriginal: TelegramDate,
    val from: User
) : ForwardInfo()

data class ForwardFromChannelInfo(
    override val dateOfOriginal: TelegramDate,
    val messageId: MessageIdentifier,
    val channelChat: ChannelChat,
    val signature: String? = null
) : ForwardInfo()

data class ForwardFromSupergroupInfo(
    override val dateOfOriginal: TelegramDate,
    val group: SupergroupChat
) : ForwardInfo()
