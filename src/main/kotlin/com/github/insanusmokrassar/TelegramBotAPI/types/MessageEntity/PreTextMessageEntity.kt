package com.github.insanusmokrassar.TelegramBotAPI.types.MessageEntity

data class PreTextMessageEntity(
    override val offset: Int,
    override val length: Int,
    override val sourceString: String
) : TextMessageEntity() {
    override val formatSymbol: String = "```"
}
