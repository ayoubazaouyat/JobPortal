package teapot.collat_hbrs.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import teapot.collat_hbrs.backend.security.SecurityService;

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

    private void showMessageDialog(Message message) {
        Dialog dialog = new Dialog();

        // Verwenden Sie Div-Elemente, um Texte hinzuzufügen
        Div senderDiv = new Div();
        senderDiv.setText(message.getSender());

        Div timestampDiv = new Div();
        timestampDiv.setText(message.getTimestamp());

        Div contentDiv = new Div();
        contentDiv.setText(message.getContent());

        dialog.add(new Div(senderDiv, timestampDiv, contentDiv));

        Button close = new Button("Close", e -> dialog.close());
        dialog.add(close);

        dialog.open();
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