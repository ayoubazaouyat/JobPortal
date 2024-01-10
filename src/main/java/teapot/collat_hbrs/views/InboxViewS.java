package teapot.collat_hbrs.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import teapot.collat_hbrs.backend.security.SecurityService;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.List;

@PermitAll
@Route(value = "inbox", layout = MainLayout.class)
@PageTitle("Inbox")
public class InboxViewS extends VerticalLayout {

    private final SecurityService securityService;
    private Grid<Message> messageGrid;
    private Button deleteButton;
    private Label messageCountLabel;

    public InboxViewS(SecurityService securityService) {
        this.securityService = securityService;
        setupUI();
        updateMessageCountLabel();
    }

    private void setupUI() {
        messageGrid = new Grid<>(Message.class);
        messageGrid.setColumns("sender", "subject", "timestamp");

        List<Message> messages = new ArrayList<>();
        messages.add(new Message("John Doe", "Job Application", "Hello, I'm interested in the job...", "2023-12-01 10:00"));
        messages.add(new Message("Jane Smith", "Regarding Your Job Posting", "I have a question about the job posting...", "2023-12-02 09:30"));

        messageGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                showMessageDialog(event.getValue());
            }
        });

        messageGrid.setItems(messages);

        // Add a label to display the message count
        messageCountLabel = new Label();
        add(messageCountLabel);

        deleteButton = new Button("Delete", e -> {
            Message selectedMessage = messageGrid.asSingleSelect().getValue();
            if (selectedMessage != null) {
                showDeleteConfirmation(selectedMessage);
            }
        });

        add(messageGrid, deleteButton);
    }
    private void updateMessageCountLabel() {
        int messageCount = getMessageCount();
        messageCountLabel.setText("Messages: " + messageCount);
    }
    private int getMessageCount() {
        ListDataProvider<Message> dataProvider = (ListDataProvider<Message>) messageGrid.getDataProvider();
        return dataProvider.getItems().size();
    }

    private void deleteMessages(List<Message> messagesToDelete) {
        ListDataProvider<Message> dataProvider = (ListDataProvider<Message>) messageGrid.getDataProvider();
        List<Message> currentItems = new ArrayList<>(dataProvider.getItems());

        currentItems.removeAll(messagesToDelete);

        dataProvider.getItems().clear();
        dataProvider.getItems().addAll(currentItems);

        dataProvider.refreshAll();
        updateMessageCountLabel(); // Update the message count after deletion

    }

    private void showDeleteConfirmation(Message message) {
        ConfirmDialog confirmDialog = new ConfirmDialog();
        confirmDialog.setHeader("Are you sure you want to delete the message?");
        confirmDialog.setText("This action cannot be undone");

        confirmDialog.setConfirmButton("Yes", buttonClickEvent -> {
            deleteMessages(List.of(message));
            confirmDialog.close();
        });

        confirmDialog.setCancelButton("Cancel", buttonClickEvent -> confirmDialog.close());

        confirmDialog.open();
    }

    private void showMessageDialog(Message message) {
        Dialog dialog = new Dialog();

        Div senderDiv = new Div();
        senderDiv.setText("From: " + message.getSender());

        Div timestampDiv = new Div();
        timestampDiv.setText("Timestamp: " + message.getTimestamp());

        Div contentDiv = new Div();
        contentDiv.setText("Message: " + message.getContent());

        TextArea replyTextArea = new TextArea("Compose Reply");

        dialog.add(new Div(senderDiv, timestampDiv, contentDiv, replyTextArea));

        Button replyButton = new Button("Reply", e -> {
            String replyContent = replyTextArea.getValue();
            sendReply(message.getSender(), message.getSubject(), replyContent);
            dialog.close();
        });

        Button deleteButton = new Button("Delete", e -> showDeleteConfirmation(message));

        Button close = new Button("Close", e -> dialog.close());

        dialog.add(new Div(replyButton, deleteButton, close));

        dialog.open();
    }

    private void sendReply(String recipient, String subject, String content) {
        System.out.println("Reply Sent to: " + recipient);
        System.out.println("Subject: " + subject);
        System.out.println("Content: " + content);
    }

    public static class Message {
        private String sender;
        private String subject;
        private String content;
        private String timestamp;

        public Message() {
            // Default constructor required for Vaadin Grid
        }

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
