package teapot.collat_hbrs.backend;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@DiscriminatorValue("M")
public class ChatMessage implements Serializable {

    @Id
    private long messageId;
    private String recipient;
    private String sender;
    private String subject;
    private String content;
    private Date messageTime;
    private boolean isSpam;


    public void setMessageId(Long messageId) { this.messageId = messageId; }

    public Long getMessageId() { return messageId; }

    public String getSubject() { return subject; }

    public void setSubject(String subject) { this.subject = subject; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public Date getMessageTime() { return messageTime; }

    public void setMessageTime(Date messageTime) { this.messageTime = messageTime; }

    public String getRecipient() { return recipient; }

    public void setRecipient(String recipient) { this.recipient = recipient; }

    public String getSender() { return sender; }

    public void setSender(String sender) { this.sender = sender; }

    public boolean isSpam() { return isSpam; }

    public void setSpam(boolean spam) { isSpam = spam; }
}
