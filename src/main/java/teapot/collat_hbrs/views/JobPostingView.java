package teapot.collat_hbrs.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import teapot.collat_hbrs.backend.JobAdvertisement;
import teapot.collat_hbrs.backend.security.JobAdvertisementService;

import java.awt.*;
import java.util.Collection;


@Route("job-posting")
@AnonymousAllowed
public class JobPostingView extends VerticalLayout {
    private TextField companyName;
    private TextField positionName;
    private ComboBox<String> fullOrPartTime;
    private ComboBox<String> remoteOrInHouse;
    private TextArea textDescription;
    private TextField location;
    private DatePicker offerAge;
    private TextArea expectations ;
    private  TextArea requirements ;

    private TextField candidateCount;
    private TextArea employeeBenefits ;
    private TextArea hrContact ;
    private Button postButton;

    private final JobAdvertisementService jobAdvertisementService;

    private TextField titleField = new TextField("Title");


    public JobPostingView(JobAdvertisementService jobAdvertisementService) {
        this.jobAdvertisementService = jobAdvertisementService;
        // Set up the layout of the form
        add(new H2("Job Posting "));
        initJobPostingForm();
        add(new Hr());

    }


    private void initJobPostingForm() {
        FormLayout genForm = new FormLayout();

        companyName = new TextField("Name of company");
        companyName.setRequired(true);
        positionName = new TextField("Position name");
        positionName.setRequired(true);
        fullOrPartTime = new ComboBox<>("Full/Part-time");
        fullOrPartTime.setClearButtonVisible(true);
        fullOrPartTime.setItems("Full-time", "Part-time");
        fullOrPartTime.setRequired(true);
        remoteOrInHouse = new ComboBox<String>("Remote/Office");
        remoteOrInHouse.setClearButtonVisible(true);
        remoteOrInHouse.setItems("Remote", "Office");

        textDescription = new TextArea("Text description");
        location = new TextField("Location/Address");
        location.setRequired(true);
        offerAge = new DatePicker("Application Deadline");
        offerAge.setRequired(true);

        expectations = new TextArea("What's waiting for you");
        requirements = new TextArea("What we expect");
        candidateCount = new TextField("Number of candidates");
        employeeBenefits = new TextArea("Employee Benefits");
        hrContact = new TextArea("Contact of HR Manager (Optional)");



        postButton = new Button("Post Job");
        postButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        postButton.setIcon(new Icon(VaadinIcon.CLIPBOARD_CHECK));
        genForm.add(
                companyName, positionName, fullOrPartTime, remoteOrInHouse, textDescription, location, offerAge,
                expectations, requirements, candidateCount, employeeBenefits, hrContact, postButton
        );

        postButton.addClickListener(event -> {
            if (companyName.isEmpty() || positionName.isEmpty() ||location.isEmpty()||fullOrPartTime.isEmpty()||offerAge.isEmpty()) {
                Notification.show("Please fill in the required fields: Name of company and Position name");
            } else {

                displayEnteredInformation();
                addEditAndConfirmButtons();
                // For demonstration, using a Notification to signify successful posting.
                // Notification.show("Job posted successfully");

            }
        });

        genForm.setColspan(companyName, 4);
        genForm.setColspan(positionName, 4);
        genForm.setColspan(fullOrPartTime, 2);
        genForm.setColspan(remoteOrInHouse, 2);
        genForm.setColspan(textDescription, 4);
        genForm.setColspan(location, 2);
        genForm.setColspan(offerAge, 2);
        genForm.setColspan(expectations, 4);
        genForm.setColspan(requirements, 4);
        genForm.setColspan(candidateCount, 4);
        genForm.setColspan(employeeBenefits, 4);
        genForm.setColspan(hrContact, 4);
        genForm.setColspan(postButton, 2);
        genForm.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 4));

        add(genForm);
    }
    private void displayEnteredInformation() {
        removeAll();
        add(new H2("Are you sure of the information?"));

        add(new TextField("Name of company", companyName.getValue()));
        add(new TextField("Position name", positionName.getValue()));
        add(new TextField("Full/Part-time", fullOrPartTime.getValue()));
        add(new TextField("remoteOrInHouse", remoteOrInHouse.getValue()));
        add(new TextField("textDescription", textDescription.getValue()));
        add(new TextField("location", location.getValue()));
        add(new TextField("offerAge", String.valueOf(offerAge.getValue())));
        add(new TextField("Name of company", expectations.getValue()));
        add(new TextField("Name of company", requirements.getValue()));
        add(new TextField("Name of company", candidateCount.getValue()));
        add(new TextField("Name of company", employeeBenefits.getValue()));
        add(new TextField("Name of company", hrContact.getValue()));
        add(new TextField("Name of company", employeeBenefits.getValue()));



    }

    private void addEditAndConfirmButtons() {
        Button editButton = new Button("Edit Job Posting");
        editButton.addClickListener(e -> {
            removeAll();
            initJobPostingForm();
        });

        Button confirmButton = new Button("Confirm Job Posting");
        confirmButton.addClickListener(e -> {
            Notification.show("Job posted successfully");
        });

        add(editButton, confirmButton);
    }




    private void saveJobAdvertisement(String title) {
        JobAdvertisement jobAdvertisement = new JobAdvertisement();
        jobAdvertisement.setTitle(title);

        jobAdvertisementService.addJobAdvertisement(jobAdvertisement);
    }

    public JobAdvertisementService getJobAdvertisementService() {
        return jobAdvertisementService;
    }
}
