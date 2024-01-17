package teapot.collat_hbrs.views;

import teapot.collat_hbrs.backend.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class SpamFolder {
    private final List<ChatMessage> spamMessages;

    public SpamFolder() {
        this.spamMessages = new ArrayList<>();
    }

    public void addSpamMessage(ChatMessage message) {
        spamMessages.add(message);
    }

    public List<ChatMessage> getSpamMessages() {
        return new ArrayList<>(spamMessages);
    }

    public void removeSpamMessage(ChatMessage message) {
        spamMessages.remove(message);
    }
}
