package teapot.collat_hbrs.views.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import teapot.collat_hbrs.backend.ChatMessage;
import teapot.collat_hbrs.backend.Company;
import teapot.collat_hbrs.views.InboxView;

import java.util.ArrayList;
import java.util.List;

public class CompanyInformationDialog extends Dialog {

    private final Company company;


    private List<ContactListener> contactListeners = new ArrayList<>();

    private Div rating;
    private boolean hasUserRated = false; // Flag to track whether the user has already rated


    public CompanyInformationDialog(Company company) {
        this.company = company;
        buildDialog();
    }

    public interface ContactListener {
        void contact(String message);
    }

    public void addContactListener(ContactListener listener) {
        contactListeners.add(listener);
    }

    private void buildDialog() {
        setHeaderTitle(company.getCompanyName());

        VerticalLayout layout = new VerticalLayout();

        Span industry = new Span("Industry: " + company.getIndustry());

        rating = new Div(new Span("Rating: " + company.getAverageRating() +" "), generateStars());

        TextArea descriptionField = new TextArea();
        descriptionField.setLabel("Description");
        descriptionField.setValue(company.getCompanyDescription());
        descriptionField.setReadOnly(true);
        descriptionField.setWidthFull();

        TextArea addressField = new TextArea();
        addressField.setLabel("Address");
        addressField.setValue(company.getAddress());
        addressField.setReadOnly(true);
        addressField.setWidthFull();

        Div emailField = new Div(new Icon(VaadinIcon.ENVELOPE), new Text(" " + company.getEmail()));
        Div phoneField = new Div(new Icon(VaadinIcon.PHONE_LANDLINE), new Text(" " + company.getPhoneNumber()));
        HorizontalLayout contactInfoFields = new HorizontalLayout(emailField, phoneField);

        layout.add(
                industry,
                rating,
                descriptionField,
                addressField,
                contactInfoFields
        );

        add(layout);

        Button reportButton = new Button("Report", new Icon(VaadinIcon.MEGAPHONE));
        reportButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        reportButton.getStyle().set("color", "red");
        reportButton.addClickListener(buttonClickEvent -> reportUser());

        Button rateButton = new Button("Rate", new Icon(VaadinIcon.STAR));
        rateButton.addClickListener(buttonClickEvent -> showRatingDialog());
        
        Button contactButton = new Button("Contact", new Icon(VaadinIcon.ENVELOPE));
        contactButton.addClickListener(e -> showContactDialog());

        Button closeButton = new Button("Close");
        closeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        closeButton.addClickListener(buttonClickEvent -> close());
        getFooter().add(reportButton, rateButton, contactButton, closeButton);

        setWidth("30%");
    }
    private void updateRating(double newRating) {


        // Update the rating Div with the new rating and regenerate stars
        Div newRatingDiv = new Div(new Span("Rating: " +  company.getAverageRating() + " "), generateStars());
        rating.removeAll(); // Remove existing components
        rating.add(newRatingDiv); // Add the updated rating Div

    }
    private void showRatingDialog() {
        Dialog ratingDialog = new Dialog();

        // Check if the user has already rated
        if (hasUserRated) {
            Notification.show("You have already submitted a rating. Thank you for your feedback!");
            return;
        }

        // Create a text field for the rating
        TextField ratingField = new TextField("Your Rating");
        ratingField.setPlaceholder("Enter a number (1-5)");
        ratingField.setWidth("100%");

        // Create buttons for sending and canceling
        Button sendButton = new Button("Send", event -> {
            try {
                double ratingValue = Double.parseDouble(ratingField.getValue());
                if (ratingValue >= 0 && ratingValue <= 5) {
                    company.addRating(ratingValue);

                    updateRating(ratingValue);
                    // Set the flag indicating the user has rated
                    hasUserRated = true;
                    // Show a thank you notification
                    Notification.show("Thank you for your feedback!");


                    ratingDialog.close();
                } else {
                    Notification.show("Please enter a rating between 0 and 5");
                }
            } catch (NumberFormatException e) {
                Notification.show("Please enter a valid number for the rating");
            }
        });

        Button cancelButton = new Button("Cancel", event -> ratingDialog.close());


        ratingDialog.add(ratingField, new Div(sendButton, cancelButton));



        // Set the dialog size
        ratingDialog.setWidth("300px");
        ratingDialog.setHeight("200px");

        // Open the dialog
        ratingDialog.open();
    }

    private void reportUser() {

        Notification.show("Your report has been received. Our team will investigate promptly.", 3000, Notification.Position.MIDDLE);
    }


    private void errorNotification() {
        Notification notification = Notification.show("Not implemented yet!", 3000, Notification.Position.MIDDLE);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
    }

    private void showContactDialog() {
        Dialog contactDialog = new Dialog();
        ChatMessage chatMessage = new ChatMessage();

        // Create subject and message text areas
        TextArea subjectTextArea = new TextArea("Subject");
        subjectTextArea.setWidth("100%");

        TextArea messageTextArea = new TextArea("Message");
        messageTextArea.setWidth("100%");
        messageTextArea.setHeight("200px"); // Set the desired height

        // Create buttons for sending and canceling
        Button sendButton = new Button("Send", event -> {
            String subject = subjectTextArea.getValue();
            String message = messageTextArea.getValue();

            if (!subject.isEmpty() && !message.isEmpty()) {
                notifyContactListeners(subject, message);
                // TODO: Chat Backend-functionality needs to go here
                chatMessage.setSubject(subject);
                chatMessage.setContent(message);
                // chatMessageService.addChatMessage(chatMessage); // ChatMessageService needs to be called somewhere

                Notification.show("Message sent!");
                contactDialog.close();
            } else {
                Notification.show("Please enter a subject and message");
            }
        });


        Button cancelButton = new Button("Cancel", event -> contactDialog.close());

        // Add components to the dialog
        contactDialog.add(subjectTextArea, messageTextArea, new Div(sendButton, cancelButton));

        // Set the dialog size
        contactDialog.setWidth("400px"); // Set the desired width

        // Open the dialog
        contactDialog.open();
    }


    private void notifyContactListeners(String subject, String message) {
        for (ContactListener listener : contactListeners) {
            listener.contact(subject + ": " + message);
        }

        // Pass the message to the InboxView for sent messages
        if (getParent().isPresent() && getParent().get() instanceof InboxView) {
            InboxView inboxView = (InboxView) getParent().get();
            String timestamp = "Now";  // Set the timestamp for sent messages
            inboxView.handleIncomingMessage("You", "Message to " + company.getCompanyName(), company.getCompanyName(), message, timestamp, true);
        }
    }


    private Div generateStars() {
        Div stars = new Div();
        double rating = company.getAverageRating();

        for (int i = 0; i < Math.floor(rating); i++) {
            stars.add(new Icon(VaadinIcon.STAR));
        }

        if (rating % 1 != 0) {
            // Use a Unicode symbol for half-star
            stars.add(new Text("\u00BD")); // Unicode for half (1/2) symbol
        }

        int emptyStars = 5 - (int) Math.ceil(rating);
        for (int i = 0; i < emptyStars; i++) {
            stars.add(new Icon(VaadinIcon.STAR_O));
        }

        return stars;
    }

}
