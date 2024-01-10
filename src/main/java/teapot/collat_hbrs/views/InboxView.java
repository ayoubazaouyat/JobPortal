package teapot.collat_hbrs.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.provider.DataProvider;
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
public class InboxView extends VerticalLayout {

    private final SecurityService securityService;
    private Grid<Message> messageGrid;
    private Grid<Message> spamGrid;
    private Button deleteButton;
    private Button markAsSpamButton;
    private Button showSpamGridButton;
    private Button backButton;
    private Button deleteSpamButton;
    private Label messageCountLabel;

    private List<Message> inboxMessages = new ArrayList<>();
    private List<Message> spamMessages = new ArrayList<>();

    private ListDataProvider<Message> inboxDataProvider;
    private ListDataProvider<Message> spamDataProvider;

    public InboxView(SecurityService securityService) {
        this.securityService = securityService;
        setupUI();
        updateMessageCount();
    }

    private void setupUI() {
        messageGrid = new Grid<>();
        messageGrid.addColumn(Message::getSender).setHeader("Sender");
        messageGrid.addColumn(Message::getSubject).setHeader("Subject");
        messageGrid.addColumn(Message::getTimestamp).setHeader("Timestamp");

        // Create ListDataProvider for inbox messages
        inboxDataProvider = DataProvider.ofCollection(inboxMessages);
        messageGrid.setDataProvider(inboxDataProvider);

        messageGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                showMessageDialog(event.getValue());
            }
        });

        messageCountLabel = new Label();
        add(messageCountLabel);

        deleteButton = new Button("Delete", e -> {
            Message selectedMessage = messageGrid.asSingleSelect().getValue();
            if (selectedMessage != null) {
                showDeleteConfirmation(selectedMessage);
            }
        });

        markAsSpamButton = new Button("Mark as Spam", e -> {
            Message selectedMessage = messageGrid.asSingleSelect().getValue();
            if (selectedMessage != null) {
                markAsSpam(selectedMessage);
            }
        });

        showSpamGridButton = new Button("Spam Messages", e -> showSpamGrid());

        backButton = new Button("Back to Inbox", e -> showInboxGrid());
        backButton.setVisible(false);

        deleteSpamButton = new Button("Delete Spam", e -> {
            Message selectedSpamMessage = spamGrid.asSingleSelect().getValue();
            if (selectedSpamMessage != null) {
                showDeleteSpamConfirmation(selectedSpamMessage);
            }
        });
        deleteSpamButton.setVisible(false); // Initially hidden

        add(messageGrid, deleteButton, markAsSpamButton, showSpamGridButton, backButton, deleteSpamButton);

        spamGrid = new Grid<>();
        spamGrid.addColumn(Message::getSender).setHeader("Sender");
        spamGrid.addColumn(Message::getSubject).setHeader("Subject");
        spamGrid.addColumn(Message::getTimestamp).setHeader("Timestamp");
        spamGrid.setVisible(false);
        add(spamGrid);

        // Create ListDataProvider for spam messages
        spamDataProvider = DataProvider.ofCollection(spamMessages);
        spamGrid.setDataProvider(spamDataProvider);

        // Add some sample messages to the inbox
        inboxMessages.add(new Message("John Doe", "Job Application", "Hello, I'm interested in the job...", "2023-01-01 10:00"));
        inboxMessages.add(new Message("Jane Smith", "Regarding Your Job Posting", "I have a question about the job posting...", "2023-01-02 12:30"));
    }

    private void updateMessageCount() {
        int inboxMessageCount = inboxMessages.size();
        int spamMessageCount = spamMessages.size();
        messageCountLabel.setText("Inbox Messages: " + inboxMessageCount + " | Spam Messages: " + spamMessageCount);
    }

    private void showSpamGrid() {
        messageGrid.setVisible(false);
        spamGrid.setVisible(true);
        backButton.setVisible(true);
        deleteButton.setVisible(false);
        markAsSpamButton.setVisible(false);
        deleteSpamButton.setVisible(true);
    }

    private void showInboxGrid() {
        messageGrid.setVisible(true);
        spamGrid.setVisible(false);
        backButton.setVisible(false);
        deleteButton.setVisible(true);
        markAsSpamButton.setVisible(true);
        deleteSpamButton.setVisible(false);
    }

    private void showDeleteSpamConfirmation(Message spamMessage) {
        ConfirmDialog confirmDialog = new ConfirmDialog();
        confirmDialog.setHeader("Are you sure you want to delete the spam message?");
        confirmDialog.setText("This action cannot be undone");

        confirmDialog.setConfirmButton("Yes", buttonClickEvent -> {
            deleteSpamMessages(List.of(spamMessage));
            confirmDialog.close();
        });

        confirmDialog.setCancelButton("Cancel", buttonClickEvent -> confirmDialog.close());

        confirmDialog.open();
    }

    private void deleteSpamMessages(List<Message> spamMessagesToDelete) {
        spamMessages.removeAll(spamMessagesToDelete);
        spamDataProvider.refreshAll();
        updateMessageCount();
    }

    private void markAsSpam(Message message) {
        showMarkAsSpamConfirmation(message);
    }

    private void showMarkAsSpamConfirmation(Message message) {
        ConfirmDialog confirmDialog = new ConfirmDialog();
        confirmDialog.setHeader("Are you sure you want to mark the message as spam?");
        confirmDialog.setText("This action cannot be undone");

        confirmDialog.setConfirmButton("Yes", buttonClickEvent -> {
            markMessageAsSpam(message);
            confirmDialog.close();
        });

        confirmDialog.setCancelButton("Cancel", buttonClickEvent -> confirmDialog.close());

        confirmDialog.open();
    }

    private void markMessageAsSpam(Message message) {
        // Set the message as spam
        message.setSpam(true);

        // Add the message to the spam list
        spamMessages.add(message);

        // Remove the message from the inbox
        inboxMessages.remove(message);

        // Refresh the inbox grid
        inboxDataProvider.refreshAll();

        // Refresh the spam grid
        spamDataProvider.refreshAll();

        // Update the message count label
        updateMessageCount();
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

    private void deleteMessages(List<Message> messagesToDelete) {
        inboxMessages.removeAll(messagesToDelete);
        inboxDataProvider.refreshAll();
        updateMessageCount();
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
        private boolean isSpam;

        public Message() {
            // Default constructor required for Vaadin Grid
        }

        public Message(String sender, String subject, String content, String timestamp) {
            this.sender = sender;
            this.subject = subject;
            this.content = content;
            this.timestamp = timestamp;
        }

        public boolean isSpam() {
            return isSpam;
        }

        public void setSpam(boolean spam) {
            isSpam = spam;
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
