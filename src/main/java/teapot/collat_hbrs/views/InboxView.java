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
    private Button markAsInnocentButton;
    private Button showSpamGridButton;
    private Button backButton;
    // Add a delete button for sent messages
    private Button deleteSentButton;
    private Label messageCountLabel;

    private List<Message> inboxMessages = new ArrayList<>();
    private List<Message> spamMessages = new ArrayList<>();

    private Grid<Message> sentGrid;
    private List<Message> sentMessages = new ArrayList<>();
    private ListDataProvider<Message> sentDataProvider;
    private Button showSentGridButton;


    private ListDataProvider<Message> inboxDataProvider;
    private ListDataProvider<Message> spamDataProvider;

    public InboxView(SecurityService securityService) {
        this.securityService = securityService;
        setupUI();
        updateMessageCount();

        // Add a listener to update the "Delete" button based on the spam folder status
        spamDataProvider.addDataProviderListener(event -> updateDeleteButtonStatus());
    }

    private void updateDeleteButtonStatus() {
        // Disable "Delete" button if the spam folder is empty
        deleteButton.setEnabled(!spamMessages.isEmpty());
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
                // Disable "Mark as Spam" button if the selected message is already marked as spam
                markAsSpamButton.setEnabled(!event.getValue().isSpam());
            } else {
                // If no message is selected, enable the "Mark as Spam" button
                markAsSpamButton.setEnabled(true);
            }
        });

        messageCountLabel = new Label();
        add(messageCountLabel);

        deleteButton = new Button ("Delete", e -> {
            Message selectedMessage = messageGrid.asSingleSelect().getValue();
            if (selectedMessage != null) {
                showDeleteConfirmation(selectedMessage);
            }
        });

        deleteSentButton = new Button("Delete", e -> {
            Message selectedMessage = sentGrid.asSingleSelect().getValue();
            if (selectedMessage != null) {
                showDeleteConfirmation(selectedMessage);
            }
        });

        markAsSpamButton = new Button("Mark as Spam");
        markAsSpamButton.setEnabled(false); // Initially disable the button
        markAsSpamButton.addClickListener(e -> {
            Message selectedMessage = messageGrid.asSingleSelect().getValue();
            if (selectedMessage != null) {
                markAsSpam(selectedMessage);
            }
        });

        markAsInnocentButton = new Button("Mark as Innocent", e -> {
            Message selectedMessage = spamGrid.asSingleSelect().getValue();
            if (selectedMessage != null) {
                showMarkAsInnocentConfirmation(selectedMessage);
            }
        });
        markAsInnocentButton.setVisible(false); // Initially hidden
        add(markAsInnocentButton);

        showSpamGridButton = new Button("Spam Messages", e -> showSpamGrid());

        backButton = new Button("Back to Inbox", e -> showInboxGrid());
        backButton.setVisible(false);

        add(messageGrid, deleteButton, markAsSpamButton, markAsInnocentButton, showSpamGridButton, backButton);

        spamGrid = new Grid<>();
        spamGrid.addColumn(Message::getSender).setHeader("Sender");
        spamGrid.addColumn(Message::getSubject).setHeader("Subject");
        spamGrid.addColumn(Message::getTimestamp).setHeader("Timestamp");
        spamGrid.setVisible(false);
        add(spamGrid);

        // Create ListDataProvider for spam messages
        spamDataProvider = DataProvider.ofCollection(spamMessages);
        spamGrid.setDataProvider(spamDataProvider);

        showSentGridButton = new Button("Sent Messages", e -> showSentGrid());
        add(showSentGridButton);

        sentGrid = new Grid<>();
        sentGrid.addColumn(Message::getSender).setHeader("Recipient");
        sentGrid.addColumn(Message::getSubject).setHeader("Subject");
        sentGrid.addColumn(Message::getTimestamp).setHeader("Timestamp");
        sentGrid.setVisible(false);
        add(sentGrid, deleteSentButton);



        // Create ListDataProvider for sent messages
        sentDataProvider = DataProvider.ofCollection(sentMessages);
        sentGrid.setDataProvider(sentDataProvider);

        // Add a value change listener to the sent messages grid
        sentGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                showMessageDialog(event.getValue());
            }
        });


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
        markAsInnocentButton.setVisible(true);
        backButton.setVisible(true);
        markAsSpamButton.setEnabled(false);

        // Disable "Delete" button if the spam folder is empty
        updateDeleteButtonStatus();

        // Disable "Mark as Spam" button when switching to spam view
        markAsSpamButton.setEnabled(false);
    }

    private void markAsSpam(Message message) {
        showMarkAsSpamConfirmation(message);
    }

    private void showMarkAsSpamConfirmation(Message message) {
        ConfirmDialog confirmDialog = new ConfirmDialog();
        confirmDialog.setHeader("Are you sure you want to mark the message as spam?");

        confirmDialog.setConfirmButton("Yes", buttonClickEvent -> {
            markMessageAsSpam(message);
            confirmDialog.close();
        });

        confirmDialog.setCancelButton("Cancel", buttonClickEvent -> confirmDialog.close());

        confirmDialog.open();
        // Update the delete button status after marking as spam
        updateDeleteButtonStatus();
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

    private void showMarkAsInnocentConfirmation(Message message) {
        ConfirmDialog confirmDialog = new ConfirmDialog();
        confirmDialog.setHeader("Are you sure you want to mark the message as innocent?");
        confirmDialog.setText("This will move this message back to your inbox");

        confirmDialog.setConfirmButton("Yes", buttonClickEvent -> {
            markMessageAsInnocent(message);
            confirmDialog.close();
        });

        confirmDialog.setCancelButton("Cancel", buttonClickEvent -> confirmDialog.close());

        confirmDialog.open();
    }

    private void markMessageAsInnocent(Message message) {
        // Set the message as not spam
        message.setSpam(false);

        // Add the message to the inbox
        inboxMessages.add(message);

        // Remove the message from the spam list
        spamMessages.remove(message);

        // Refresh the inbox grid
        inboxDataProvider.refreshAll();

        // Refresh the spam grid
        spamDataProvider.refreshAll();

        // Update the message count label
        updateMessageCount();
    }

    private void deleteMessages(List<Message> messagesToDelete) {
        inboxMessages.removeAll(messagesToDelete);
        spamMessages.removeAll(messagesToDelete);
        sentMessages.removeAll(messagesToDelete);
        inboxDataProvider.refreshAll();
        spamDataProvider.refreshAll();
        sentDataProvider.refreshAll();
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
            sendReply(message, message.getSubject(), replyContent);
            dialog.close();
        });

        Button deleteButton = new Button("Delete", e -> showDeleteConfirmation(message));

        Button close = new Button("Close", e -> dialog.close());

        dialog.add(new Div(replyButton, deleteButton, close));

        dialog.open();
    }

    private void sendReply(Message originalMessage, String subject, String content) {
        System.out.println("Reply Sent to: " + originalMessage.getSender());
        System.out.println("Subject: " + subject);
        System.out.println("Content: " + content);

        // Create a new Message for the sent reply with the initial sender as the recipient
        Message sentMessage = new Message(originalMessage.getSender(), "Re: " + subject, content, "2024-01-14 15:30");

        // Add the sent reply message to the list of sent messages
        sentMessages.add(sentMessage);

        // Refresh the data provider for the sent messages grid
        sentDataProvider.refreshAll();

        // Show a notification on the left side that the message has been sent
        Notification.show("Message sent to: " + originalMessage.getSender(), 3000, Notification.Position.BOTTOM_START);
    }




    public void handleIncomingMessage(String sender, String subject, String recipient, String content, String timestamp, boolean isSent) {
        if (isSent) {
            // This is a sent message, add it to the sent messages
            Message sentMessage = new Message(sender, recipient, subject, content, timestamp);
            sentMessages.add(sentMessage);
            sentDataProvider.refreshAll();
        } else {
            // This is an incoming message, add it to the inbox
            Message newMessage = new Message(sender, recipient, subject, content, timestamp);
            inboxMessages.add(newMessage);
            inboxDataProvider.refreshAll();
        }

        updateMessageCount();
    }






    public static class Message {
        private String recipient;
        private String sender;
        private String subject;
        private String content;
        private String timestamp;
        private boolean isSpam;
        private boolean isSent;  // Add a flag to indicate whether the message is sent or received


        public Message() {
            // Default constructor required for Vaadin Grid
        }



        // Constructor for sent messages
        public Message(String sender, String subject, String content, String timestamp) {
            this.sender = sender;
            this.recipient = sender; // Set recipient initially to sender for sent messages
            this.subject = subject;
            this.content = content;
            this.timestamp = timestamp;
            this.isSent = true;  // Set the flag for sent messages
        }

        // Add another constructor for received messages
        public Message(String sender, String recipient, String subject, String content, String timestamp) {
            this.sender = sender;
            this.recipient = recipient;
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

        public String getRecipient() {
            return recipient;
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
    private void showSentGrid() {
        messageGrid.setVisible(false);
        spamGrid.setVisible(false);
        sentGrid.setVisible(true);
        backButton.setVisible(true);
        deleteButton.setVisible(false); // Hide delete button for inbox messages
        deleteSentButton.setVisible(true); // Show delete button for sent messages
    }

    private void showInboxGrid() {
        messageGrid.setVisible(true);
        spamGrid.setVisible(false);
        sentGrid.setVisible(false);
        markAsInnocentButton.setVisible(false);
        backButton.setVisible(false);
        markAsSpamButton.setEnabled(true);
        deleteButton.setVisible(true); // Show delete button for inbox messages
        deleteSentButton.setVisible(false); // Hide delete button for sent messages
    }



}
