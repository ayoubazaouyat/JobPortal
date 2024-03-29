package teapot.collat_hbrs.backend.security;

import org.springframework.stereotype.Service;
import teapot.collat_hbrs.backend.ChatMessage;
import teapot.collat_hbrs.backend.ChatRepository;
import java.util.List;

@Service
public class ChatMessageService {

    private final ChatRepository chatRepository;

    public ChatMessageService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public List<ChatMessage> getAllChatMessages() {
        return chatRepository.findAll();
    }


    public ChatMessage addChatMessage(ChatMessage chatMessage) {
        if (chatMessage.getContent() == null || chatMessage.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Please add content to your message");
        } else if (chatMessage.getSubject() == null || chatMessage.getSubject().trim().isEmpty()) {
            throw new IllegalArgumentException("Please assign a subject");
        } else {
            return chatRepository.save(chatMessage);
        }
    }

    public void deleteChatMessage(ChatMessage chatMessage) {
        chatRepository.delete(chatMessage);
    }

    public List<ChatMessage> getChatMessagesFormSender(String sender) {
        return chatRepository.findBySender(sender);
    }
    public List<ChatMessage> getChatMessagesForRecipient(String recipient){
        return chatRepository.findByRecipient(recipient);
    }

    public List<ChatMessage> getAllChatMessagesForUsername(String username){
        List<ChatMessage> messages;
        messages = chatRepository.findByRecipient(username);
        messages.addAll(chatRepository.findBySender(username));
        return messages;
    }

}
