package ma.majrisalma.ebankchatbot.telegram;

import jakarta.annotation.PostConstruct;
import ma.majrisalma.ebankchatbot.agents.EbankAIAgent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    // ⚠️ Attention : Vérifie bien la clé dans ton .env ou application.properties !
    @Value("${telegram.token}")
    private String telegramBotToken;

    private final EbankAIAgent aiAgent;

    public TelegramBot(EbankAIAgent aiAgent) {
        this.aiAgent = aiAgent;
    }

    @PostConstruct
    public void registerTelegramBot() {
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(this);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpdateReceived(Update telegraRequest) {
        try {
            if (!telegraRequest.hasMessage()) return;

            Long chatId = telegraRequest.getMessage().getChatId();
            String messageText = telegraRequest.getMessage().getText();
            List<PhotoSize> photos = telegraRequest.getMessage().getPhoto();

            // --- CAS 1 : TEXTE ---
            if (messageText != null && !messageText.isEmpty()) {
                String response = aiAgent.chat(messageText, chatId.toString());

                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId.toString());
                sendMessage.setText(response);
                execute(sendMessage);
            }

            // --- CAS 2 : IMAGE ---
            if (photos != null && !photos.isEmpty()) {
                String caption = telegraRequest.getMessage().getCaption();
                if (caption == null) caption = "Que vois-tu sur cette image ?";

                PhotoSize largestPhoto = photos.get(photos.size() - 1);
                String fileId = largestPhoto.getFileId();

                GetFile getFile = new GetFile();
                getFile.setFileId(fileId);

                File file = execute(getFile);
                String filePath = file.getFilePath();
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId.toString());
                sendMessage.setText("J'ai bien reçu ton image ! (L'analyse d'image avec Gemini arrive bientôt).");
                execute(sendMessage);
            }

        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "Ebank18Bot";
    }

    @Override
    public String getBotToken() {
        return telegramBotToken;
    }
}