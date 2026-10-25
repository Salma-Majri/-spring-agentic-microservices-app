package ma.majrisalma.ebankchatbot.controllers;

import ma.majrisalma.ebankchatbot.agents.EbankAIAgent;
import org.springframework.http.MediaType; // N'oubliez pas cet import
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EbankChatbotController {

    private final EbankAIAgent ebankAIAgent;

    public EbankChatbotController(EbankAIAgent ebankAIAgent) {
        this.ebankAIAgent = ebankAIAgent;
    }

    @GetMapping(value = "/chat", produces = MediaType.TEXT_PLAIN_VALUE)
    public String chat(
            @RequestParam(name = "query", defaultValue = "Bonjour") String query,
            @RequestParam(name = "conversationId", defaultValue = "session-1") String conversationId) {

        return ebankAIAgent.chat(query, conversationId);
    }
}