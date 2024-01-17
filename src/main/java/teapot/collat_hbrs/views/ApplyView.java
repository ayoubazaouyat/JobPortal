package teapot.collat_hbrs.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.*;
import teapot.collat_hbrs.backend.Account;
import teapot.collat_hbrs.backend.ChatMessage;
import teapot.collat_hbrs.backend.JobAdvertisement;
import teapot.collat_hbrs.backend.security.AccountService;
import teapot.collat_hbrs.backend.security.ChatMessageService;
import teapot.collat_hbrs.backend.security.JobAdvertisementService;

import javax.annotation.security.PermitAll;
import java.io.InputStream;

@Route(value = "apply")
@PermitAll
@PageTitle("Apply for job | Coll@HBRS")
public class ApplyView extends VerticalLayout implements HasUrlParameter<String> {

    private JobAdvertisement job;
    private MultiFileMemoryBuffer buffer;
    private Button applyButton;
    private TextArea messageArea;
    private final JobAdvertisementService jobAdvertisementService;
    private final ChatMessageService chatMessageService;
    private final Account account;

    public ApplyView(JobAdvertisementService jobAdvertisementService, ChatMessageService chatMessageService, AccountService accountService) {
        this.jobAdvertisementService = jobAdvertisementService;
        this.chatMessageService = chatMessageService;
        account = accountService.getAccount();
        buildForm();
        this.setJustifyContentMode(JustifyContentMode.CENTER);
    }

    /**
     * Reading url parameter
     *
     * @param beforeEvent Event
     * @param s           URL parameter
     */
    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String s) {
        // check, if an ID was provided
        if (s == null || s.equals("null")) {
            Notification errorMsg = new Notification();
            errorMsg.addThemeVariants(NotificationVariant.LUMO_ERROR);
            Button closeButton = new Button("Close Application Form", new Icon(VaadinIcon.BAN));
            closeButton.addClickListener(buttonClickEvent -> closeTab());
            errorMsg.add(new Text("Error: No valid Job Advertisement ID was provided! "), closeButton);
            errorMsg.setPosition(Notification.Position.BOTTOM_STRETCH);
            errorMsg.open();
            applyButton.setEnabled(false);
            applyButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        } else {
            job = jobAdvertisementService.getJobAdvertisementById(Integer.parseInt(s));
        }
    }

    /**
     * Builds the application form
     */
    private void buildForm() {
        H2 title = new H2("Apply now for "); // TODO add job title
        Upload uploadField = fileUploader();
        messageArea = new TextArea("Message");
        FormLayout applyForm = new FormLayout(uploadField, messageArea);
        applyForm.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1)
        );

        applyButton = new Button("Apply", new Icon(VaadinIcon.CLIPBOARD_CHECK));
        applyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        applyButton.addClickListener(buttonClickEvent -> this.sendApplyMessage());
        Button cancelButton = new Button("Cancel", new Icon(VaadinIcon.BAN));
        cancelButton.addClickListener(buttonClickEvent -> closeTab());
        HorizontalLayout buttons = new HorizontalLayout(applyButton, cancelButton);

        add(
                title,
                applyForm,
                buttons
        );
    }

    private void sendApplyMessage() {
        if (messageArea.isEmpty()) Notification.show("Message must not be empty.");
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRecipient(job.getCompany().getUsername());
        chatMessage.setSender(account.getUsername());
        chatMessage.setSubject("Application from " + account.getUsername());
        chatMessage.setContent(messageArea.getValue());

        chatMessageService.addChatMessage(chatMessage);
        Notification.show("Application send.");
    }

    /**
     * Initiates the upload component
     *
     * @return Upload component
     */
    private Upload fileUploader() {
        buffer = new MultiFileMemoryBuffer();
        Upload upload = new Upload(buffer);

        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            InputStream inputStream = buffer.getInputStream(fileName);

            // TODO save file(s)
            Notification.show("Not implemented yet!");
            // Do something with the file data
            // processFile(inputStream, fileName);
        });

        return upload;
    }

    /**
     * Closes the application form tab
     */
    private void closeTab() {
        UI.getCurrent().getPage().executeJs("close()");
    }

}
