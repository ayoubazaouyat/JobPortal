package teapot.collat_hbrs.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.progressbar.ProgressBarVariant;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.apache.commons.lang3.NotImplementedException;
import teapot.collat_hbrs.backend.AccountCreator;
import teapot.collat_hbrs.backend.security.UserService;

@Route("registration")
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {

    String password;
    String studentAdresse;
    private UserService userService;
    private static final double NUMEROFSTEPS = 4;
    private final H1 heading;
    private AccountCreator accountCreator = new AccountCreator();
    Binder<AccountCreator> binder = new Binder<>(AccountCreator.class);


    int accType;
    int step;
    ProgressBar progressBar;

    /**
     * Constructor
     */
    public RegistrationView(UserService userService) {
        // TODO Make it only accessible when the user is not logged in
        this.userService = userService;
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
                progressBar.setValue(1 / NUMEROFSTEPS);
                break;
            case 2:
                add(
                        new H2("Enter your information:"),
                        buildBasicForm()
                );
                buildNavigation(true, true);
                progressBar.setValue(2 / NUMEROFSTEPS);
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
                progressBar.setValue(3 / NUMEROFSTEPS);
                break;
            case 4:
                heading.setText("Registration successful!");
                add(buildFinishedScreen());
                buildNavigation(true, false);
                progressBar.setValue(4 / NUMEROFSTEPS);
                progressBar.addThemeVariants(ProgressBarVariant.LUMO_SUCCESS);
                break;

            default:
                throw new NotImplementedException();
        }
    }

    /**
     * Creates the selector where the user can choose between creating a stundend or company account
     *
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
        studentContainer.setJustifyContentMode(JustifyContentMode.END);
        studentContainer.setWidth("100%");
        companyContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        companyContainer.setJustifyContentMode(JustifyContentMode.END);
        companyContainer.setWidth("100%");

        var studentIcon = new Icon(VaadinIcon.USER);
        studentIcon.setSize("var(--lumo-icon-size-l)");
        var companyIcon = new Icon(VaadinIcon.BUILDING);
        companyIcon.setSize("var(--lumo-icon-size-l)");

        var studentButton = new Button("I'm a student");
        studentButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
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

        studentContainer.add(
                studentIcon,
                studentButton
        );
        companyContainer.add(
                companyIcon,
                companyButton
        );

        selContainer.add(
                studentContainer,
                companyContainer
        );

        return selContainer;
    }

    /**
     * Creates a form with text fields to retrieve information necessary for registration
     *
     * @return Basic form
     */
    private FormLayout buildBasicForm() {
        var basicForm = new FormLayout();
        var usernameField = new TextField("Username");
        var emailField = new EmailField("E-Mail");
        var passwordField = new PasswordField("Password");
        var confirmPasswordField = new PasswordField("Confirm Password");

        usernameField.setRequired(true);
        emailField.setRequired(true);
        passwordField.setRequired(true);
        confirmPasswordField.setRequired(true);

        //bind data to accountBuilder
        binder.bind(usernameField, AccountCreator::getUsername, AccountCreator::setUsername);
        binder.bind(emailField, AccountCreator::getEmail, AccountCreator::setEmail);
        //load possible previous data
        binder.readBean(accountCreator);

        basicForm.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2)
        );
        basicForm.setColspan(usernameField, 2);
        basicForm.setColspan(emailField, 2);
        basicForm.setColspan(passwordField, 1);
        basicForm.setColspan(confirmPasswordField, 1);


        passwordField.addValueChangeListener(event -> {
            password = passwordField.getValue();
        });

        basicForm.add(
                usernameField,
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
        var lastName = new TextField("Last Name");
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
        genForm.setColspan(title, 4);
        genForm.setColspan(firstName, 2);
        genForm.setColspan(lastName, 2);
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
        TextField skillsField = new TextField("Skills");
        skillsField.setPlaceholder("Enter Skills separated by commas");

        skillsField.addValueChangeListener(event -> {
            String value = event.getValue();
            String[] skills = value.split(","); // Splitting by comma

        });
        studyForm.add(
                studiesTitle,
                comboBox,
                comboBoxStudgang,
                semesterNum,
                skillsField
        );
        var contactTitle = new H4("Contact Details :");
        var contactForm = new FormLayout();
        var phoneNumber = new TextField("Phone Number");

        contactForm.add(
                contactTitle,
                phoneNumber
        );

        var terms = new Checkbox("I agree to the ToS");
        studyForm.setColspan(studiesTitle, 4);
        studyForm.setColspan(comboBox, 4);
        studyForm.setColspan(comboBoxStudgang, 4);
        studyForm.setColspan(semesterNum, 4);
        studyForm.setColspan(skillsField, 4);
        contactForm.setColspan(phoneNumber, 4);
        studentForm.add(
                genForm,
                studyForm,
                contactForm,
                terms
        );
        firstName.setRequired(true);
        lastName.setRequired(true);
        street.setRequired(true);
        houseNumber.setRequired(true);
        plz.setRequired(true);
        city.setRequired(true);
        comboBox.setRequired(true);
        comboBoxStudgang.setRequired(true);

        binder.bind(firstName, AccountCreator::getForename, AccountCreator::setForename);
        binder.bind(lastName, AccountCreator::getSurname, AccountCreator::setSurname);
        binder.bind(phoneNumber, AccountCreator::getPhoneNumber, AccountCreator::setPhoneNumber);
        binder.bind(comboBoxStudgang, AccountCreator::getStudyProgram, AccountCreator::setStudyProgram);

        //TODO combine address for binder
        street.addValueChangeListener(event -> studentAdresse = street.getValue() + " " + houseNumber.getValue() + "\n" + plz.getValue() + " " + city.getValue());
        houseNumber.addValueChangeListener(event -> studentAdresse = street.getValue() + " " + houseNumber.getValue() + "\n" + plz.getValue() + " " + city.getValue());
        plz.addValueChangeListener(event -> studentAdresse = street.getValue() + " " + houseNumber.getValue() + "\n" + plz.getValue() + " " + city.getValue());
        city.addValueChangeListener(event -> studentAdresse = street.getValue() + " " + houseNumber.getValue() + "\n" + plz.getValue() + " " + city.getValue());

        binder.readBean(accountCreator);

        return studentForm;
    }

    private VerticalLayout buildCompanyForm() {
        var companyForm = new VerticalLayout();

        var addressTitle = new H4("Address");
        var addressForm = new FormLayout();
        var companyName = new TextField("Company name");
        companyName.setRequired(true);
        var street = new TextField("Street name");
        street.setRequired(true);
        var houseNumber = new NumberField("House number");
        houseNumber.setRequired(true);
        var plz = new NumberField("PLZ");
        plz.setRequired(true);
        var city = new TextField("City");
        city.setRequired(true);
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
        industry.setRequired(true);
        var description = new TextArea("Company description");
        description.setPlaceholder("Beispiel: Unternehmen XY ist ein führender Anbieter von innovativen Lösungen für die digitale Transformation. Wir unterstützen unsere Kunden dabei, ihre Geschäftsprozesse zu optimieren, ihre Kundenbeziehungen zu stärken und ihre Wettbewerbsfähigkeit zu erhöhen. Unsere Dienstleistungen umfassen Beratung, Entwicklung, Implementierung und Betrieb von maßgeschneiderten Softwarelösungen, Cloud-Services, künstlicher Intelligenz und Internet der Dinge. Wir verfügen über langjährige Erfahrung und Expertise in verschiedenen Branchen, wie Finanzen, Gesundheit, Industrie und Handel. Unser Ziel ist es, unseren Kunden einen Mehrwert zu bieten und sie bei der Gestaltung ihrer digitalen Zukunft zu begleiten.");
        description.setRequired(true);
        informationForm.add(
                industry,
                description
        );
        informationForm.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        var contactTitle = new H4("Contact");
        var contactForm = new FormLayout();
        var phone = new TextField("Phone number");
        phone.setRequired(true);
        var fax = new TextField("Fax");
        contactForm.add(
                phone,
                fax
        );
        contactForm.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        var separator = new Hr();
        var terms = new CheckboxGroup<>("Do you agree to the Terms of Service?");
        terms.setItems("Yes, I agree");
        terms.setRequired(true);

        binder.bind(companyName, AccountCreator::getCompanyName, AccountCreator::setCompanyName);
        binder.bind(description, AccountCreator::getCompanyDescription, AccountCreator::setCompanyDescription);
        binder.bind(industry, AccountCreator::getCompanyIndustry, AccountCreator::setCompanyIndustry);
        binder.bind(phone, AccountCreator::getPhoneNumber, AccountCreator::setPhoneNumber);
        //TODO combine address for binder


        binder.readBean(accountCreator);

        companyForm.add(
                addressTitle,
                addressForm,
                informationTitle,
                informationForm,
                contactTitle,
                contactForm,
                separator,
                terms
        );
        return companyForm;
    }

    /**
     * Creates the last screen showing that the registration was successful
     *
     * @return Registration successful message
     */
    private VerticalLayout buildFinishedScreen() {
        var buildScreen = new VerticalLayout();
        var successMessage = new H2("You are ready to go!");
        var homeButton = new Button();
        if (accType == 0) {
            String message = "Hey " + accountCreator.getSurname() + ", we are happy to have you!";

            Notification.show(message, 5000, Notification.Position.TOP_CENTER);

            //ADD ACCOUNT TO DATABASE HERE.
            userService.registerAccount(accountCreator.buildStudent(), password);
        }
        else {
            userService.registerAccount(accountCreator.buildCompany(), password);

        }
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
     *
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
            saveFormData();
            step--;
            buildUI();
        });
        backButton.addClickShortcut(Key.ESCAPE);
        nextButton.addClickListener(buttonClickEvent -> {
            saveFormData();
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
     *
     * @return Login button
     */
    private Button loginButton() {
        var login = new Button("Already have an Account?");
        login.setTooltipText("Click here to go to the login page");
        login.addClickListener(buttonClickEvent -> UI.getCurrent().navigate("login"));
        return login;
    }

    private void saveFormData(){
        //save form data
        try {
            binder.writeBean(accountCreator);
        } catch (ValidationException e) {
            //TODO handle validation property maybe use binder.isValid()
            throw new RuntimeException(e);
        }
    }

}
