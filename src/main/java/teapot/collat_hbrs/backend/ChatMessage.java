package teapot.collat_hbrs.backend;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("M")
public class ChatMessage implements Serializable {

    @Id
    private long messageId;
    private String recipient;
    private String sender;
    private String subject;
    private String content;
    private LocalDateTime messageTime;
    private boolean isSpam;

    public ChatMessage() {
        //default constructor
    }

    public ChatMessage(String sender, String recipient, String subject, String content) {
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
        //this.messageTime = timestamp;
    }

    public ChatMessage(String sender, String recipient, String subject, String content, String timestamp) {
        this(sender, recipient, subject, content);
    }



    public void setMessageId(Long messageId) { this.messageId = messageId; }

    public Long getMessageId() { return messageId; }

    public String getSubject() { return subject; }

    public void setSubject(String subject) { this.subject = subject; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public LocalDateTime getMessageTime() { return messageTime; }

    public void setMessageTime(LocalDateTime messageTime) { this.messageTime = messageTime; }

    public String getRecipient() { return recipient; }

    public void setRecipient(String recipient) { this.recipient = recipient; }

    public String getSender() { return sender; }

    public void setSender(String sender) { this.sender = sender; }

    public boolean isSpam() { return isSpam; }

    public void setSpam(boolean spam) { isSpam = spam; }
}
