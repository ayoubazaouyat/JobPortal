package teapot.collat_hbrs.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import teapot.collat_hbrs.backend.security.SecurityService;
import com.vaadin.flow.component.textfield.TextArea;
import javax.annotation.security.PermitAll;
import javax.mail.Message;
import java.awt.*;

@PermitAll
@Route(value = "inbox", layout = MainLayout.class)
public class InboxView extends VerticalLayout {

    private final SecurityService securityService;

    public InboxView(SecurityService securityService) {
        this.securityService = securityService;
        setupUI();
    }

    private void setupUI() {
        Grid<Message> messageGrid = new Grid<>(Message.class);
        messageGrid.setColumns("sender", "subject", "timestamp");

        // Populate the grid with sample data (replace with your actual data)
        messageGrid.setItems(
                new Message("John Doe", "Job Application", "Hello, I'm interested in the job...", "2023-12-01 10:00"),
                new Message("Jane Smith", "Regarding Your Job Posting", "I have a question about the job posting...", "2023-12-02 09:30")
        );

        messageGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                showMessageDialog(event.getValue());
            }
        });

        add(messageGrid);
    }

    // Inside the showMessageDialog method

    private void showMessageDialog(Message message) {
        Dialog dialog = new Dialog();

        Div senderDiv = new Div();
        senderDiv.setText("From: " + message.getSender());

        Div timestampDiv = new Div();
        timestampDiv.setText("Timestamp: " + message.getTimestamp());

        Div contentDiv = new Div();
        contentDiv.setText("Message: " + message.getContent());

        // Create a text area for composing replies
        TextArea replyTextArea = new TextArea("Compose Reply");

        dialog.add(new Div(senderDiv, timestampDiv, contentDiv, replyTextArea));

        Button replyButton = new Button("Reply", e -> {
            String replyContent = replyTextArea.getValue();
            // Send the reply (You can implement this functionality)
            sendReply(message.getSender(), message.getSubject(), replyContent);
            dialog.close();
        });

        Button close = new Button("Close", e -> dialog.close());
        dialog.add(new Div(replyButton, close));

        dialog.open();
    }

    // Method to simulate sending a reply (You can replace this with your actual sending logic)
    private void sendReply(String recipient, String subject, String content) {
        // This is a placeholder for sending the reply
        System.out.println("Reply Sent to: " + recipient);
        System.out.println("Subject: " + subject);
        System.out.println("Content: " + content);
    }

    public static class Message {
        private String sender;
        private String subject;
        private String content;
        private String timestamp;

        public Message(String sender, String subject, String content, String timestamp) {
            this.sender = sender;
            this.subject = subject;
            this.content = content;
            this.timestamp = timestamp;
        }

        public String getSender() {
            return sender;
        }

        public String getSubject() {
            return subject;
        }

        public String getContent() {
            return content;
        }

        public String getTimestamp() {
            return timestamp;
        }
    }
}