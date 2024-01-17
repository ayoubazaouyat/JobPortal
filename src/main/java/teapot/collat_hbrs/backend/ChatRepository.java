package teapot.collat_hbrs.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findBySender(String sender);
    List<ChatMessage> findByRecipient(String sender);
}
