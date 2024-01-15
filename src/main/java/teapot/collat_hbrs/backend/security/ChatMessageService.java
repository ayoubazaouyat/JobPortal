package teapot.collat_hbrs.backend.security;

import org.springframework.stereotype.Service;
import teapot.collat_hbrs.backend.ChatMessage;
import teapot.collat_hbrs.backend.ChatRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatMessageService {

    private final ChatRepository chatRepository;
    public static List<ChatMessage> allChatMessages = new ArrayList<>();

    public ChatMessageService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public List<ChatMessage> getAllChatMessages() {
        return chatRepository.findAll();
    }

    public void addToAllChatMessages(ChatMessage chatMessage) {
        allChatMessages.add(chatMessage);
    }

    public ChatMessage addChatMessage(ChatMessage chatMessage) {
        if (chatMessage.getRecipient() == null || chatMessage.getRecipient().trim().isEmpty()) {
            throw new IllegalArgumentException("Please assign a recipient");
        } else if (chatMessage.getSubject() == null || chatMessage.getSubject().trim().isEmpty()) {
            throw new IllegalArgumentException("Please assign a subject");
        } else {
            return chatRepository.save(chatMessage);
        }
    }

    public void deleteChatMessage(ChatMessage chatMessage) {
        chatRepository.delete(chatMessage);
    }

    public List<ChatMessage> getChatMessageForSender(String sender) {
        return chatRepository.findBySender(sender);
    }
}
