package ma.majrisalma.ebankchatbot.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

@Service
public class EbankAIAgent {

    private final ChatClient chatClient;

    public EbankAIAgent(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    public String chat(String query, String conversationId) {

        return chatClient.prompt(query)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
    }
}