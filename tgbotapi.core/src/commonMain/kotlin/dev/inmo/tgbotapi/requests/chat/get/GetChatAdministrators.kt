package dev.inmo.tgbotapi.requests.chat.get

import dev.inmo.tgbotapi.CommonAbstracts.types.ChatRequest
import dev.inmo.tgbotapi.requests.abstracts.SimpleRequest
import dev.inmo.tgbotapi.types.ChatIdentifier
import dev.inmo.tgbotapi.types.ChatMember.abstracts.AdministratorChatMember
import dev.inmo.tgbotapi.types.ChatMember.abstracts.AdministratorChatMemberSerializerWithoutDeserialization
import dev.inmo.tgbotapi.types.chatIdField
import kotlinx.serialization.*
import kotlinx.serialization.builtins.ListSerializer

private val chatMembersListSerializer = ListSerializer(
    AdministratorChatMemberSerializerWithoutDeserialization
)

@Serializable
data class GetChatAdministrators(
    @SerialName(chatIdField)
    override val chatId: ChatIdentifier
): ChatRequest, SimpleRequest<List<AdministratorChatMember>> {
    override fun method(): String = "getChatAdministrators"
    override val resultDeserializer: DeserializationStrategy<List<AdministratorChatMember>>
        get() = chatMembersListSerializer
    override val requestSerializer: SerializationStrategy<*>
        get() = serializer()
}
