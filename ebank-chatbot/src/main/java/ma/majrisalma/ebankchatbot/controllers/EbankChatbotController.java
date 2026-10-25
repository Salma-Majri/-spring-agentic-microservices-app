package ma.majrisalma.ebankchatbot.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EbankChatbotController {

    private final ChatClient chatClient;

    public EbankChatbotController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam(name = "query", defaultValue = "Bonjour") String query) {
        return chatClient.prompt(query).call().content();
    }
}