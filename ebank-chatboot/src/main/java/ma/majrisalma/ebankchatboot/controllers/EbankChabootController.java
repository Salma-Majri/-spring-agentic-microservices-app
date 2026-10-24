package ma.majrisalma.ebankchatboot.controllers;
import org.springframework.web.bind.annotation.*;

@RestController
public class EbankChabootController {
    private ChatClient chatClient;

    public void EbankChatbootController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    @GetMapping("/chat")
    public String chat(
            @RequestParam(name = "query", defaultValue = "Bonjour") String query){
        return chatClient.prompt(query).call().content();
    }
}
