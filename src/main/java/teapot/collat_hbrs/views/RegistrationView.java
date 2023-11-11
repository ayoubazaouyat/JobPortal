package teapot.collat_hbrs.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.progressbar.ProgressBarVariant;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.apache.commons.lang3.NotImplementedException;

@Route("registration")
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {

    private final H1 heading;

    int accType;
    int step;
    ProgressBar progressBar;

    /**
     * Constructor
     */
    public RegistrationView() {
        // TODO Make it only accessible when the user is not logged in
        heading = new H1("Registration");
        step = 1;
        buildUI();
    }

    /**
     * Creates the UI (from scratch)
     */
    private void buildUI() {
        // first clean everything
        this.removeAll();

        // then rebuild the view
        this.add(heading);

        // layout settings
        setAlignItems(Alignment.CENTER);
        setWidth("100%");
        setHeight("100vh");

        switch (step) {
            case 1:
                add(
                        new H2("Choose your account type:"),
                        buildAccountTypeSelector(),
                        loginButton()
                );
                buildNavigation(false, false);
                progressBar.setValue(0.3333);
                break;
            case 2:
                add(
                        new H2("Enter your information:"),
                        buildBasicForm()
                );
                buildNavigation(true, true);
                progressBar.setValue(0.6666);
                break;
            case 3:
                if (accType == 0) {
                    add(
                            new H2("Student"),
                            buildStudentForm()
                    );
                } else {
                    add(
                            new H2("Information for your profile"),
                            buildCompanyForm()
                    );
                }
                buildNavigation(true, true);
                progressBar.setValue(0.8);
                break;
            case 4:
                heading.setText("Registration successful!");
                add(buildFinishedScreen());
                buildNavigation(false, false);
                progressBar.setValue(1);
                progressBar.addThemeVariants(ProgressBarVariant.LUMO_SUCCESS);
                break;

            default:
                throw new NotImplementedException();
        }
    }

    /**
     * Creates the selector where the user can choose between creating a stundend or company account
     * @return Account Selector
     */
    private HorizontalLayout buildAccountTypeSelector() {
        var selContainer = new HorizontalLayout();
        var studentContainer = new VerticalLayout();
        var companyContainer = new VerticalLayout();

        // layout settings
        selContainer.setHeight("max-content");
        selContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        selContainer.setJustifyContentMode(JustifyContentMode.CENTER);
        selContainer.setWidth("100%");
        studentContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        studentContainer.setJustifyContentMode(JustifyContentMode.CENTER);
        studentContainer.setWidth("100%");
        companyContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        companyContainer.setJustifyContentMode(JustifyContentMode.CENTER);
        companyContainer.setWidth("100%");

        var studentAvatar = new Avatar();
        var studentButton = new Button("I'm a student");
        studentButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        var companyAvatar = new Avatar();
        var companyButton = new Button("I represent a company");
        companyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        studentButton.addClickListener(buttonClickEvent -> {
            accType = 0;
            step = 2;
            buildUI();
        });
        companyButton.addClickListener(buttonClickEvent -> {
            accType = 1;
            step = 2;
            buildUI();
        });

        studentContainer.add(studentAvatar);
        studentContainer.add(studentButton);
        companyContainer.add(companyAvatar);
        companyContainer.add(companyButton);

        selContainer.add(studentContainer);
        selContainer.add(companyContainer);

        return selContainer;
    }

    /**
     * Creates a form with text fields to retrieve information necessary for registration
     * @return Basic form
     */
    private VerticalLayout buildBasicForm() {
        var basicForm = new VerticalLayout();
        var emailField = new TextField("E-Mail");
        var passwordField = new PasswordField("Password");
        var confirmPasswordField = new PasswordField("Confirm Password");

        // layout settings
        basicForm.setHeight("max-content");
        basicForm.setAlignItems(FlexComponent.Alignment.CENTER);
        basicForm.setJustifyContentMode(JustifyContentMode.CENTER);
        basicForm.setWidth("100%");

        basicForm.add(
                emailField,
                passwordField,
                confirmPasswordField
        );

        return basicForm;
    }

    private VerticalLayout buildStudentForm() {
        var studentForm = new VerticalLayout();

        var title = new H4("General Informations :");
        var genForm = new FormLayout();
        var firstName = new TextField("First Name");
        var lastName= new TextField("Last Name");
        var street = new TextField("Street name");
        var houseNumber = new NumberField("House number");
        var plz = new NumberField("PLZ");
        var city = new TextField("City");
        genForm.add(
                title,
                firstName,
                lastName,
                street,
                houseNumber,
                plz,
                city
        );
        genForm.setColspan(title,4);
        genForm.setColspan(firstName,2);
        genForm.setColspan(lastName,2);
        genForm.setColspan(street, 3);
        genForm.setColspan(houseNumber, 1);
        genForm.setColspan(plz, 2);
        genForm.setColspan(city, 2);
        genForm.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 4)
        );
        var studiesTitle = new H4("Studies : ");
        var studyForm = new FormLayout();
        ComboBox<String> comboBox = new ComboBox<>("Choose an option");
        comboBox.setItems("FB 01 - Wirtschaftswissenschaften",
                "FB 02 - Informatik",
                "FB 03 - Ingenieurwissenschaften und Kommunikation",
                "FB 05 - Angewandte Naturwissenschaften",
                "FB 06 - Sozialpolitik und Soziale Sicherung");
        comboBox.setPlaceholder("Select one");
        //comboBox.setRequired(true);
        comboBox.setLabel("choose your Fachbereich:");
        // Handle the value change event
        comboBox.addValueChangeListener(event -> {
            // event.getValue() gives you the selected value
            Notification.show("Selected: " + event.getValue());
        });
        ComboBox<String> comboBoxStudgang = new ComboBox<>("Choose an option");
        comboBoxStudgang.setItems("To be Filled from Datenbank",
                "To be Filled from Datenbank");
        comboBoxStudgang.setPlaceholder("Select one");
        //comboBox.setRequired(true);
        comboBoxStudgang.setLabel("choose your Studiengang:");
        var semesterNum = new NumberField("Current Semester");
        studyForm.add(
                studiesTitle,
                comboBox,
                comboBoxStudgang,
                semesterNum
        );
        var contactTitle = new H4("Contact Details :");
        var contactForm = new FormLayout();
        var phoneNumber = new TextField("Phone Number");
        contactForm.add(
                contactTitle,
                phoneNumber
        );
        var terms = new Checkbox("I agree to the ToS");
        studyForm.setColspan(studiesTitle,4);
        studyForm.setColspan(comboBox,4);
        studyForm.setColspan(comboBoxStudgang,4);
        studyForm.setColspan(semesterNum,4);
        contactForm.setColspan(phoneNumber,4);
        studentForm.add(
                genForm,
                studyForm,
                contactForm,
                terms
        );


        // TODO Registrierung Student @Ayub


        return studentForm;
    }

    private VerticalLayout buildCompanyForm() {
        var companyForm = new VerticalLayout();

        var addressTitle = new H4("Address");
        var addressForm = new FormLayout();
        var companyName = new TextField("Company name");
        var street = new TextField("Street name");
        var houseNumber = new NumberField("House number");
        var plz = new NumberField("PLZ");
        var city = new TextField("City");
        addressForm.add(
                addressTitle,
                companyName,
                street,
                houseNumber,
                plz,
                city
        );
        addressForm.setColspan(companyName, 4);
        addressForm.setColspan(street, 3);
        addressForm.setColspan(houseNumber, 1);
        addressForm.setColspan(plz, 2);
        addressForm.setColspan(city, 2);
        addressForm.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 4)
        );

        var informationTitle = new H4("Information");
        var informationForm = new FormLayout();
        var industry = new TextField("Industry");
        var description = new TextField("Company description");
        informationForm.add(
                industry,
                description
        );
        informationForm.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        var contactTitle = new H4("Contact");
        var contactForm = new FormLayout();
        var phone = new TextField("Phone number");
        var fax = new TextField("Fax");
        var mobile = new TextField("Mobile");
        var terms = new Checkbox("I agree to the ToS");
        contactForm.add(
                phone,
                fax,
                mobile,
                terms
        );
        contactForm.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        companyForm.add(
                addressTitle,
                addressForm,
                informationTitle,
                informationForm,
                contactTitle,
                contactForm
        );
        return companyForm;
    }

    /**
     * Creates the last screen showing that the registration was successful
     * @return Registration successful message
     */
    private VerticalLayout buildFinishedScreen() {
        var buildScreen = new VerticalLayout();
        var successMessage = new H2("You are ready to go!");
        var homeButton = new Button();

        // layout settings
        buildScreen.setAlignItems(FlexComponent.Alignment.CENTER);
        buildScreen.setJustifyContentMode(JustifyContentMode.CENTER);
        buildScreen.setWidth("100%");

        homeButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        homeButton.setIcon(new Icon(VaadinIcon.HOME));
        homeButton.setHeight("50px");
        homeButton.setWidth("50px");

        buildScreen.add(
                successMessage,
                homeButton
        );

        return buildScreen;
    }

    /**
     * Creates buttons and a progress bar to navigate between registration steps
     * @param backEnabled User is able to go a step back
     * @param nextEnabled User is able to go a step forward
     */
    private void buildNavigation(boolean backEnabled, boolean nextEnabled) {
        var navigation = new HorizontalLayout();

        // layout settings
        navigation.setAlignItems(FlexComponent.Alignment.CENTER);
        navigation.setJustifyContentMode(JustifyContentMode.CENTER);
        navigation.setWidth("100%");

        var backButton = new Button("Back");
        var nextButton = new Button("Next");

        progressBar = new ProgressBar();
        backButton.setEnabled(backEnabled);
        nextButton.setEnabled(nextEnabled);
        nextButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        backButton.addClickListener(buttonClickEvent -> {
            step--;
            buildUI();
        });
        backButton.addClickShortcut(Key.ESCAPE);
        nextButton.addClickListener(buttonClickEvent -> {
            step++;
            buildUI();
        });
        nextButton.addClickShortcut(Key.ENTER);

        navigation.add(
                backButton,
                progressBar,
                nextButton
        );

        this.add(navigation);
    }

    /**
     * Creates a button to navigate to the login page if the user already has an account
     * @return Login button
     */
    private Button loginButton() {
        var login = new Button("Already have an Account?");
        login.setTooltipText("Click here to go to the login page");
        login.addClickListener(buttonClickEvent -> UI.getCurrent().navigate("login"));
        return login;
    }

}
