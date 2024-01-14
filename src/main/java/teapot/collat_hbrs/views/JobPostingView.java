package teapot.collat_hbrs.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import teapot.collat_hbrs.backend.JobAdvertisement;
import teapot.collat_hbrs.backend.security.JobAdvertisementService;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDate;

@Route(value = "PostJob", layout = MainLayout.class)
@PageTitle("Job posten | Coll@HBRS")
@RolesAllowed({"COMPANY"})


public class JobPostingView extends VerticalLayout {

    private final JobAdvertisementService jobAdvertisementService;

    private String previousCompanyName;
    private String previousAddress;
    private String timeType;
    private String previousRemoteHouse;
    private String previousDescription;
    private String previousLocation;
    private String previousAge;
    private String previousExpect;
    private String previousRequ;
    private String previouscandidateCount;
    private String previousBenefits;
    private  String previoushrContact;

    private TextField companyName;
    private TextField address;
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
    private Button confirmButton;


    private Button editButton;



    public JobPostingView(JobAdvertisementService jobAdvertisementService) {
        this.jobAdvertisementService = jobAdvertisementService;
        // Set up the layout of the form
        add(new H2("Job posten "));
        initJobPostingForm();
        add(new Hr());

    }


    private void initJobPostingForm() {
        FormLayout genForm = new FormLayout();

        companyName = new TextField("Name of company");
        companyName.setRequired(true);
        address = new TextField("Position name");
        address.setRequired(true);
        fullOrPartTime = new ComboBox<>("Type");
        fullOrPartTime.setClearButtonVisible(true);
        fullOrPartTime.setItems("Full-time", "Part-time","Working Student","internship");
        fullOrPartTime.setRequired(true);
        remoteOrInHouse = new ComboBox<>("Remote/Office");
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


        Button postButton = new Button("Post Job");
        postButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        postButton.setIcon(new Icon(VaadinIcon.CLIPBOARD_CHECK));
        genForm.add(
                companyName, address, fullOrPartTime, remoteOrInHouse, textDescription, location, offerAge,
                expectations, requirements, candidateCount, employeeBenefits, hrContact, postButton
        );

        postButton.addClickListener(event -> {
            if (companyName.isEmpty() || address.isEmpty() ||location.isEmpty()||fullOrPartTime.isEmpty()||offerAge.isEmpty()) {
                Notification.show("Please fill in the required fields");
            } else if (offerAge.getValue().isBefore(LocalDate.now())) {
                Notification.show("Application Deadline cannot be in the past");
            } else {

                storeEnteredData();
                displayEnteredInformation();
                addEditAndConfirmButtons();
                // For demonstration, using a Notification to signify successful posting.
                // Notification.show("Job posted successfully");

            }
        });

        genForm.setColspan(companyName, 4);
        genForm.setColspan(address, 4);
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

    private void storeEnteredData() {
        previousCompanyName = companyName.getValue();
        previousAddress = address.getValue();
        timeType = fullOrPartTime.getValue();
        previousRemoteHouse= remoteOrInHouse.getValue();
        previousDescription= textDescription.getValue();
        previousLocation= location.getValue();
       // previousAge= offerAge.getValue();
        previousExpect= expectations.getValue();
        previousRequ=requirements.getValue();
        previouscandidateCount=candidateCount.getValue();
        previousBenefits=employeeBenefits.getValue();
        previoushrContact= hrContact.getValue();

    }
    // Function to create a bold label with value
    private Component createBoldLabel(String labelText, String value) {
        Div container = new Div();

        // Create a span for the label (bold text)
        Span labelSpan = new Span(labelText);
        labelSpan.getStyle().set("font-weight", "bold");

        // Create a span for the value
        Span valueSpan = new Span(value);

        // Add the label and value spans to the container
        container.add(labelSpan, valueSpan);

        return container;
    }

    private void displayEnteredInformation() {
        removeAll();
        add(new H2("Are you sure of the information?"));

        // Create a flexible layout to center and display entered information in two columns
        FlexLayout enteredInfoLayout = new FlexLayout();
        enteredInfoLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        // Create two vertical layouts for each column
        VerticalLayout leftColumn = new VerticalLayout();
        VerticalLayout rightColumn = new VerticalLayout();



        // Add entered information as paragraphs to the respective columns
        // Add entered information as paragraphs to the respective columns
        leftColumn.add(
                createBoldLabel("Name of company: ", companyName.getValue()),
                createBoldLabel("Position name: ", address.getValue()),
                createBoldLabel("Full/Part-time: ", fullOrPartTime.getValue()),
                createBoldLabel("Remote/Office: ", remoteOrInHouse.getValue()),
                createBoldLabel("Text description: ", textDescription.getValue())
                // Add more information as needed
        );

        // Set the style for the right column to prevent text wrapping and add a left margin
        rightColumn.getStyle().set("white-space", "nowrap").set("margin-left", "20px");


        rightColumn.add(
                createBoldLabel("Location: ", location.getValue()),
                createBoldLabel("Application Deadline: ", offerAge.getValue().toString()),
                createBoldLabel("What's waiting for you: ", expectations.getValue()),
                createBoldLabel("What we expect: ", requirements.getValue()),
                createBoldLabel("Number of candidates: ", candidateCount.getValue())
                // Add more information as needed
        );

        // Add the columns to the flexible layout
        enteredInfoLayout.add(leftColumn, rightColumn);


        // Add the entered information layout to the main view
        add(enteredInfoLayout);
    }


    private void addEditAndConfirmButtons() {
        editButton = new Button("Edit ");
        editButton.addClickListener(e -> {
            // Remove current content and display form with previous entered data
            removeAll();
            displayFormWithPreviousData();

        });

        confirmButton = new Button("Confirmation");
        confirmButton.addClickListener(e -> {


            // Display a confirmation message
            Notification.show("Job posted successfully");

            // Ask if the user wants to post another job
            getUI().ifPresent(ui -> ui.access(this::askForAnotherJob));
            confirmButton.setEnabled(false);
            editButton.setEnabled(false);
            UI.getCurrent().navigate(MyJobListView.class);

            saveJobAdvertisement();
        });

        Button backButton = new Button("Cancel and Return to Landing");
        backButton.addClickListener(e -> UI.getCurrent().navigate(""));

        add(editButton, confirmButton, backButton);
    }
    private void askForAnotherJob() {
        Dialog addMoreDialog = new Dialog();
        addMoreDialog.setHeaderTitle("Additional Job Post");
        Paragraph text = new Paragraph("Do you want to post another Job Advertisement?");
        addMoreDialog.add(text);

        Button yesButton = new Button("Yes");
        yesButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        yesButton.addClickListener(buttonClickEvent -> UI.getCurrent().getPage().executeJs("window.location.reload();"));
        Button noButton = new Button("No");
        noButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        noButton.addClickListener(buttonClickEvent -> {
            addMoreDialog.close();
            UI.getCurrent().navigate("/");
        });

        addMoreDialog.getFooter().add(yesButton, noButton);

        addMoreDialog.open();
    }



    void displayFormWithPreviousData() {
        initJobPostingForm();
        // Set previously entered data in the form fields
        companyName.setValue(previousCompanyName);
        address.setValue(previousAddress);
        fullOrPartTime.setValue(timeType);
        remoteOrInHouse.setValue(previousRemoteHouse);
        textDescription.setValue(previousDescription);
        location.setValue(previousLocation);
        // previousAge= offerAge.getValue();
        expectations.setValue(previousExpect);
        requirements.setValue(previousRequ);
        candidateCount.setValue(previouscandidateCount);
        employeeBenefits.setValue(previousBenefits);
        hrContact.setValue(previoushrContact);

        // Set other field values similarly...
    }


    private void saveJobAdvertisement() {
        JobAdvertisement jobAdvertisement = new JobAdvertisement();
        jobAdvertisement.getCompany().setCompanyName(companyName.getValue());
        jobAdvertisement.getCompany().setAddress(address.getValue());
        jobAdvertisement.setFullOrPartTime(fullOrPartTime.getValue());
        jobAdvertisement.setRemoteOrInHouse(remoteOrInHouse.getValue());
        jobAdvertisement.setTextDescription(textDescription.getValue());
        jobAdvertisement.setLocation(location.getValue());
        jobAdvertisement.setOfferAge(offerAge.getValue());
        jobAdvertisement.setExpectations(expectations.getValue());
        jobAdvertisement.setRequirements(requirements.getValue());
        jobAdvertisement.setCandidateCount(candidateCount.getValue());
        jobAdvertisement.setEmployeeBenefits(employeeBenefits.getValue());

        jobAdvertisementService.addJobAdvertisement(jobAdvertisement);
    }

}
