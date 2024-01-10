package teapot.collat_hbrs.views;

import java.util.ArrayList;
import java.util.List;

public class SpamFolder {
    private List<InboxView.Message> spamMessages;

    public SpamFolder() {
        this.spamMessages = new ArrayList<>();
    }

    public void addSpamMessage(InboxView.Message message) {
        spamMessages.add(message);
    }

    public List<InboxView.Message> getSpamMessages() {
        return new ArrayList<>(spamMessages);
    }

    public void removeSpamMessage(InboxView.Message message) {
        spamMessages.remove(message);
    }
}
