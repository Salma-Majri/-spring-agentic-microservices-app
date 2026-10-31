package ma.majrisalma.ebankchatbot.discord;

import com.zgamelogic.discord.annotations.DiscordController;
import com.zgamelogic.discord.annotations.DiscordMapping;
import ma.majrisalma.ebankchatbot.agents.EbankAIAgent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@DiscordController
public class DiscordBot {
    private final EbankAIAgent ebankAIAgent;

    public DiscordBot(EbankAIAgent ebankAIAgent) {
        this.ebankAIAgent = ebankAIAgent;
    }

    @DiscordMapping
    private void perform(MessageReceivedEvent event){
        if (event.getAuthor().isBot()) return;
        String query = event.getMessage().getContentRaw();

        if (query == null || query.trim().isEmpty()) {
            return;
        }

        try {
            String response = ebankAIAgent.chat(query, "discord-user");
            event.getChannel().sendMessage(response).queue();
        } catch (Exception e) {
            event.getChannel().sendMessage("Désolé, une erreur est survenue lors du traitement de ta demande.").queue();
            e.printStackTrace();
        }
    }
}