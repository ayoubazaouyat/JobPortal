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
import teapot.collat_hbrs.backend.ChatMessage;
import teapot.collat_hbrs.backend.security.AccountService;
import teapot.collat_hbrs.backend.security.ChatMessageService;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.List;

@PermitAll
@Route(value = "inbox", layout = MainLayout.class)
@PageTitle("Inbox")
public class InboxView extends VerticalLayout {

    private final ChatMessageService chatMessageService;
    private final String username;
    private Grid<ChatMessage> messageGrid;
    private Grid<ChatMessage> spamGrid;
    private Button deleteButton;
    private Button markAsSpamButton;
    private Button markAsInnocentButton;
    private Button showSpamGridButton;
    private Button backButton;
    // Add a delete button for sent messages
    private Button deleteSentButton;
    private Label messageCountLabel;

    private List<ChatMessage> inboxMessages = new ArrayList<>();
    private List<ChatMessage> spamMessages = new ArrayList<>();

    private Grid<ChatMessage> sentGrid;
    private List<ChatMessage> sentMessages = new ArrayList<>();
    private ListDataProvider<ChatMessage> sentDataProvider;
    private Button showSentGridButton;


    private ListDataProvider<ChatMessage> inboxDataProvider;
    private ListDataProvider<ChatMessage> spamDataProvider;

    public InboxView(ChatMessageService chatMessageService, AccountService accountService) {
        this.chatMessageService = chatMessageService;
        username = accountService.getAccount().getUsername();
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
        messageGrid.addColumn(ChatMessage::getSender).setHeader("Sender");
        messageGrid.addColumn(ChatMessage::getSubject).setHeader("Subject");
        messageGrid.addColumn(ChatMessage::getMessageTime).setHeader("Timestamp");

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
            ChatMessage selectedMessage = messageGrid.asSingleSelect().getValue();
            if (selectedMessage != null) {
                showDeleteConfirmation(selectedMessage);
            }
        });

        deleteSentButton = new Button("Delete", e -> {
            ChatMessage selectedMessage = sentGrid.asSingleSelect().getValue();
            if (selectedMessage != null) {
                showDeleteConfirmation(selectedMessage);
            }
        });

        markAsSpamButton = new Button("Mark as Spam");
        markAsSpamButton.setEnabled(false); // Initially disable the button
        markAsSpamButton.addClickListener(e -> {
            ChatMessage selectedMessage = messageGrid.asSingleSelect().getValue();
            if (selectedMessage != null) {
                markAsSpam(selectedMessage);
            }
        });

        markAsInnocentButton = new Button("Mark as Innocent", e -> {
            ChatMessage selectedMessage = spamGrid.asSingleSelect().getValue();
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
        spamGrid.addColumn(ChatMessage::getSender).setHeader("Sender");
        spamGrid.addColumn(ChatMessage::getSubject).setHeader("Subject");
        spamGrid.addColumn(ChatMessage::getMessageTime).setHeader("Timestamp");
        spamGrid.setVisible(false);
        add(spamGrid);

        // Create ListDataProvider for spam messages
        spamDataProvider = DataProvider.ofCollection(spamMessages);
        spamGrid.setDataProvider(spamDataProvider);

        showSentGridButton = new Button("Sent Messages", e -> showSentGrid());
        add(showSentGridButton);

        sentGrid = new Grid<>();
        sentGrid.addColumn(ChatMessage::getRecipient).setHeader("Recipient");
        sentGrid.addColumn(ChatMessage::getSubject).setHeader("Subject");
        sentGrid.addColumn(ChatMessage::getMessageTime).setHeader("Timestamp");
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
        inboxMessages.addAll(chatMessageService.getChatMessagesForRecipient(username));
        sentMessages.addAll(chatMessageService.getChatMessagesForSender(username));
        inboxMessages.add(new ChatMessage("John Doe", "Job Application", "Hello, I'm interested in the job...", "2023-01-01 10:00"));
        inboxMessages.add(new ChatMessage("Jane Smith", "Regarding Your Job Posting", "I have a question about the job posting...", "2023-01-02 12:30"));
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

    private void markAsSpam(ChatMessage message) {
        showMarkAsSpamConfirmation(message);
    }

    private void showMarkAsSpamConfirmation(ChatMessage message) {
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

    private void markMessageAsSpam(ChatMessage message) {
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

    private void showMarkAsInnocentConfirmation(ChatMessage message) {
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

    private void markMessageAsInnocent(ChatMessage message) {
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

    private void deleteMessages(List<ChatMessage> messagesToDelete) {
        inboxMessages.removeAll(messagesToDelete);
        spamMessages.removeAll(messagesToDelete);
        sentMessages.removeAll(messagesToDelete);
        inboxDataProvider.refreshAll();
        spamDataProvider.refreshAll();
        sentDataProvider.refreshAll();
        updateMessageCount();
    }

    private void showDeleteConfirmation(ChatMessage message) {
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

    private void showMessageDialog(ChatMessage message) {
        Dialog dialog = new Dialog();

        Div senderDiv = new Div();
        senderDiv.setText("From: " + message.getSender());

        Div timestampDiv = new Div();
        timestampDiv.setText("Timestamp: " + message.getMessageTime());

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

    private void sendReply(ChatMessage originalMessage, String subject, String content) {
        System.out.println("Reply Sent to: " + originalMessage.getSender());
        System.out.println("Subject: " + subject);
        System.out.println("Content: " + content);

        // Create a new Message for the sent reply with the initial sender as the recipient
        ChatMessage sentMessage = new ChatMessage(originalMessage.getSender(), "Re: " + subject, content, "2024-01-14 15:30");

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
            ChatMessage sentMessage = new ChatMessage(sender, recipient, subject, content, timestamp);
            sentMessages.add(sentMessage);
            sentDataProvider.refreshAll();
        } else {
            // This is an incoming message, add it to the inbox
            ChatMessage newMessage = new ChatMessage(sender, recipient, subject, content, timestamp);
            inboxMessages.add(newMessage);
            inboxDataProvider.refreshAll();
        }

        updateMessageCount();
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
