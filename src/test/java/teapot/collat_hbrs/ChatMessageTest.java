package teapot.collat_hbrs;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import teapot.collat_hbrs.backend.ChatMessage;
import teapot.collat_hbrs.backend.ChatRepository;
import teapot.collat_hbrs.backend.security.ChatMessageService;

public class ChatMessageTest {

    @Test
    public void testAddChatMessage() {
        ChatRepository chatRepository = Mockito.mock(ChatRepository.class);
        ChatMessageService chatMessageService = new ChatMessageService(chatRepository);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRecipient("John");
        chatMessage.setSubject("Hello");
        chatMessage.setContent("Hi John, how are you?");

        Mockito.when(chatRepository.save(chatMessage)).thenReturn(chatMessage);

        ChatMessage result = chatMessageService.addChatMessage(chatMessage);

        Mockito.verify(chatRepository, Mockito.times(1)).save(chatMessage);
        assert(result.equals(chatMessage));
    }
}