package teapot.collat_hbrs.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
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


@Route("job-posting")
@AnonymousAllowed
public class JobPostingView extends VerticalLayout {

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
        var genForm = new FormLayout();

        var companyName = new TextField("Name of company");
        companyName.setRequired(true);
        var positionName = new TextField("Position name");
        positionName.setRequired(true);
        var fullOrPartTime = new ComboBox<String>("Full/Part-time");
        fullOrPartTime.setClearButtonVisible(true);
        fullOrPartTime.setItems("Full-time", "Part-time");
        fullOrPartTime.setRequired(true);


        var remoteOrInHouse = new ComboBox<String>("Remote/Office");
        remoteOrInHouse.setClearButtonVisible(true);
        remoteOrInHouse.setItems("Remote", "Office");

        var textDescription = new TextArea("Text description");
        var location = new TextField("Location/Address");
        location.setRequired(true);
        var offerAge = new DatePicker("Application Deadline");
        offerAge.setRequired(true);

        var expectations = new TextArea("What's waiting for you");
        var requirements = new TextArea("What we expect");

        var candidateCount = new TextField("Number of candidates");
        var employeeBenefits = new TextArea("Employee Benefits");
        var hrContact = new TextArea("Contact of HR Manager (Optional)");

        // Button to submit the job posting
        var postButton = new Button("Post Job");
        postButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        postButton.setIcon(new Icon(VaadinIcon.CLIPBOARD_CHECK));
        genForm.add(
                companyName, positionName, fullOrPartTime, remoteOrInHouse, textDescription, location, offerAge,
                expectations, requirements, candidateCount, employeeBenefits, hrContact, postButton
        );
        // After adding the postButton to the form
        postButton.addClickListener(event -> {
            if (companyName.isEmpty() || positionName.isEmpty() || location.isEmpty() || fullOrPartTime.isEmpty() ||offerAge.isEmpty()) {
                Notification.show("Please fill in the required fields: Name of company and Position name");
            } else {
                // For demonstration, using a Notification to signify successful posting.
                // Notification.show("Job posted successfully");

                // Button to navigate back to the homepage from the confirmation message
                Button backButton = new Button("Back to Homepage");
                backButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("/")));
                removeAll();
                add(new H2("Job Posted Successfully"));
                add(backButton);
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
                new FormLayout.ResponsiveStep("500px", 4)
        );

        add(genForm);


    }

    private void showJobDetailsNotification(String company, String position, String fullTime, String remote, String description, String jobLocation, String expect,
                                            String require, String age, String count, String benefits,
                                            String hrContactInfo) {
        Notification.show("Der Job ist schon gepostet: " +
                "\nUnternehmen: " + company +
                "\nPosition: " + position +
                "\nFullTime: " + fullTime +
                "\nremote: " + remote +
                "\nDescription: " + description +
                "\nJobLocation: " + jobLocation +
                "\nexpect: " + expect +
                "\nRequire: " + require +
                "\nAge: " + age +
                "\nCount: " + count +
                "\nBenefits: " + benefits +
                "\nHrContactinfo: " + hrContactInfo +

                // ... (include other job details)
                "\nVielen Dank für Ihr Interesse!");


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
