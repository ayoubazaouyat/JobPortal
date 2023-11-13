package teapot.collat_hbrs.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route("job-posting")
@AnonymousAllowed
public class JobPostingView extends VerticalLayout {

    public JobPostingView() {
        var genForm = new FormLayout();
        // Set up the layout of the form
        add(new H2("Job Posting "));
        initJobPostingForm();
        add(new Hr());
    }
    private void initJobPostingForm() {
        var genForm = new FormLayout();

        var companyName = new TextField("Name of company");
        var positionName = new TextField("Position name");
        var fullOrPartTime = new ComboBox<>("Full/Part-time");
        fullOrPartTime.setClearButtonVisible(true);
        fullOrPartTime.setItems(new String[]{"Full-time", "Part-time"});



        var remoteOrInHouse = new ComboBox<>("Remote/Office");
        remoteOrInHouse.setClearButtonVisible(true);
        remoteOrInHouse.setItems(new String[]{"Remote", "Office"});

        var textDescription = new TextArea("Text description");
        var location = new TextField("Location/Address");
        var offerAge = new DatePicker("Application Deadline");

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

        add(genForm, postButton);


    }


}

