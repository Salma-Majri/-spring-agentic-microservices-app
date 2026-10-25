package ma.majrisalma.ebankchatbot.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.stereotype.Service;

@Service
public class EbankAIAgent {

    private final ChatClient chatClient;

    public EbankAIAgent(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, SyncMcpToolCallbackProvider mcpToolProvider) {
        this.chatClient = chatClientBuilder
                .defaultSystem("""
                         Vous êtes un assistant qui se charge de répondre aux questions 
                         de l'utilisateur a propos des clients et ses comptes bancaires , en fonction du contexte fourni.
                         Si aucun contexte n'est fourni, répondez avec JE NE SAIS PAS
                """)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultTools(mcpToolProvider)
                .build();
    }

    public String chat(String query, String conversationId) {

        return chatClient.prompt(query)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
    }
}