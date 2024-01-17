package teapot.collat_hbrs.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
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
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.BeanValidator;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import teapot.collat_hbrs.backend.AccountCreator;
import teapot.collat_hbrs.backend.security.UserService;
import teapot.collat_hbrs.backend.AccountRepository;
import teapot.collat_hbrs.frontend.Format;
import teapot.collat_hbrs.frontend.PasswordValidator;


@Route(value = "registration", layout = MainLayout.class)
@PageTitle("Registration | Coll@HBRS")
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {

    private static final String STREETLABEL = "Street name";
    private static final String HOUSELABEL = "House number";
    private static final String PASSWORDLABEL = "Password is Required";
    private static final String PASSWORDMATCHLABEL = "Passwords must match.";
    private static final String LAYOUTSIZE = "500px";
    // User account fields
    private TextField usernameField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    private TextField emailField;

    // Student-specific address fields
    private String studentAdresse;
    private TextField studentStreet;
    private TextField studentHouseNumber = new TextField(HOUSELABEL);
    private TextField studentPlz = new TextField("PLZ");
    private TextField studentCity = new TextField("City");

    // Company-specific fields
    private TextField companyName;
    private TextField street;
    private TextField houseNumber;
    private TextField plz;
    private TextField city;
    private TextField industry;
    private TextArea description;
    private TextField phone;

    // Services and utilities
    private final UserService userService;
    private final H1 heading;
    private final AccountCreator accountCreator = new AccountCreator();
    private final Binder<AccountCreator> binder = new Binder<>(AccountCreator.class);

    // Constants and process control
    private static final double NUMEROFSTEPS = 4;
    private int accType;
    private int step;
    private ProgressBar progressBar;
    private AccountRepository accountRepository;


    /**
     * Constructor for RegistrationView.
     */
    public RegistrationView(UserService userService) {
        this.userService = userService;
        heading = new H1("Registration");
        step = 1;
        buildUI();
    }

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Builds the user interface for each step of the registration process.
     */
    private void buildUI() {
        // Clean up the view and set up layout settings
        this.removeAll();
        this.add(heading);
        setAlignItems(Alignment.CENTER);
        setWidth("100%");
        setHeight("100vh");

        Button viewTermsButton = new Button("View the terms of service", event -> showTermsDialog());
        add(viewTermsButton);

        // Build UI based on the current step
        switch (step) {
            case 1 -> {
                // Account type selection
                add(new H2("Choose your account type:"), buildAccountTypeSelector(), loginButton());
                buildNavigation(false, false);
                progressBar.setValue(1 / NUMEROFSTEPS);
            }
            case 2 -> {
                // Basic information form
                add(new H2("Enter your information:"), buildBasicForm());
                buildNavigation(true, true);
                progressBar.setValue(2 / NUMEROFSTEPS);
            }
            case 3 -> {
                // Detailed form based on account type
                if (accType == 0) {
                    add(new H2("Student"), buildStudentForm());
                } else {
                    add(new H2("Company Information"), buildCompanyForm());
                }
                buildNavigation(true, true);
                progressBar.setValue(3 / NUMEROFSTEPS);
            }
            case 4 -> {
                // Registration completion
                heading.setText("Registration successful!");
                add(buildFinishedScreen());
                buildNavigation(false, false);
                progressBar.setValue(4 / NUMEROFSTEPS);
                progressBar.addThemeVariants(ProgressBarVariant.LUMO_SUCCESS);
            }
            default -> throw new NotImplementedException();
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
        selContainer.setHeight(Format.MAX_CONTENT);
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
        usernameField = new TextField("Username");
        emailField = new TextField("E-Mail");
        passwordField = new PasswordField("Password");
        confirmPasswordField = new PasswordField("Confirm Password");

        //bind form data to accountBuilder and mark it as required
        binder.forField(usernameField)
                .asRequired("Username is required.")
                .bind(AccountCreator::getUsername, AccountCreator::setUsername);

        binder.forField(emailField)
                .asRequired("Email is required.")
                .withValidator(new EmailValidator("Not a valid Email"))
                .bind(AccountCreator::getEmail, AccountCreator::setEmail);

        binder.forField(passwordField).asRequired(PASSWORDLABEL)
                .withValidator(new PasswordValidator())
                .withValidator(password -> confirmPasswordField.getValue().equals(password), PASSWORDMATCHLABEL)
                .bind(AccountCreator::getPassword, AccountCreator::setPassword);

        binder.forField(confirmPasswordField).asRequired(PASSWORDLABEL)
                .withValidator(new PasswordValidator())
                .withValidator(password -> passwordField.getValue().equals(password), PASSWORDMATCHLABEL)
                .bind(AccountCreator::getPassword, AccountCreator::setPassword);



        //load possible previous data
        binder.readBean(accountCreator);

        basicForm.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep(LAYOUTSIZE, 2)
        );
        basicForm.setColspan(usernameField, 2);
        basicForm.setColspan(emailField, 2);
        basicForm.setColspan(passwordField, 1);
        basicForm.setColspan(confirmPasswordField, 1);


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
        studentStreet = new TextField(STREETLABEL);
        studentStreet.setAllowedCharPattern("^[a-zA-Z]*$");
        studentHouseNumber = new TextField(HOUSELABEL);
        studentPlz = new TextField("PLZ");
        studentPlz.setAllowedCharPattern("[0-9]");
        studentPlz.setMaxLength(5);
        studentCity = new TextField("City");
        studentCity.setAllowedCharPattern("^[a-zA-Z]*$");

        genForm.add(
                title,
                firstName,
                lastName,
                studentStreet,
                studentHouseNumber,
                studentPlz,
                studentCity
        );
        genForm.setColspan(title, 4);
        genForm.setColspan(firstName, 2);
        genForm.setColspan(lastName, 2);
        genForm.setColspan(studentStreet, 3);
        genForm.setColspan(studentHouseNumber, 1);
        genForm.setColspan(studentPlz, 2);
        genForm.setColspan(studentCity, 2);
        genForm.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep(LAYOUTSIZE, 4)
        );
        var studiesTitle = new H4("Studies : ");
        var studyForm = new FormLayout();

        //
        ComboBox<String> comboBox = new ComboBox<>("Choose an option");
        comboBox.setItems("FB 01 - Wirtschaftswissenschaften",
                "FB 02 - Informatik",
                "FB 03 - Ingenieurwissenschaften und Kommunikation",
                "FB 05 - Angewandte Naturwissenschaften",
                "FB 06 - Sozialpolitik und Soziale Sicherung");
        comboBox.setPlaceholder("Select one");
        comboBox.setLabel("choose your Fachbereich:");

        ComboBox<String> comboBoxStudgang = new ComboBox<>("Choose an option");
        comboBoxStudgang.setPlaceholder("Select one");
        comboBoxStudgang.setLabel("choose your Studiengang:");

        // Handle the value change event for Fachbereich-Wahl
        comboBox.addValueChangeListener(event -> {
            String selectedFachbereich = event.getValue();
            comboBoxStudgang.clear();

            if ("FB 01 - Wirtschaftswissenschaften".equals(selectedFachbereich)) {
                comboBoxStudgang.setItems("Betriebswirtschaft (B.Sc.)",
                        "International Business (B.Sc.)",
                        "Wirtschaftspsychologie (B.Sc.)",
                        "Controlling und Management (M.Sc.)",
                        "Innovations- und Informationsmanagement (M.Sc.)");
            } else if ("FB 02 - Informatik".equals(selectedFachbereich)) {
                comboBoxStudgang.setItems("Cybersecurity & Privacy (B.Sc.)",
                        "Informatik (B.Sc.)",
                        "Wirtschaftsinformatik (B.Sc.)",
                        "Informatik (M.Sc.)",
                        "Autonomous Systems (M.Sc.)");
            } else if ("FB 03 - Ingenieurwissenschaften und Kommunikation".equals(selectedFachbereich)) {
                comboBoxStudgang.setItems("Elektrotechnik (B.Eng.)",
                        "Maschinenbau (B.Eng.)",
                        "Technikjournalismus (B.Sc.)",
                        "Elektrotechnik (M.Eng.)",
                        "Maschinenbau (M.Eng.)");
            } else if ("FB 05 - Angewandte Naturwissenschaften".equals(selectedFachbereich)) {
                comboBoxStudgang.setItems("Applied Biology (B.Sc.)",
                        "Chemie mit Materialwissenschaften (B.Sc.)",
                        "Nachhaltige Chemie und Materialien (B.Sc.)",
                        "Analytische Chemie und Qualitätssicherung (M.Sc.)",
                        "Biomedical Sciences (M.Sc.)");
            } else if ("FB 06 - Sozialpolitik und Soziale Sicherung".equals(selectedFachbereich)) {
                comboBoxStudgang.setItems("Nachhaltige Sozialpolitik (B.A.)",
                        "Sozialversicherung, Schwerpunkt Unfallversicherung (B.A.)",
                        "Social Protection (M.Sc.)");
            }
        });

        // Handle the value change event for Studiengang-Wahl
        comboBoxStudgang.addValueChangeListener(event ->
                Notification.show("Selected: " + event.getValue())
        );

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
        studentStreet.setRequired(true);
        studentCity.setRequired(true);
        comboBox.setRequired(true);
        comboBoxStudgang.setRequired(true);
        binder.forField(studentHouseNumber)
                .asRequired()
                .bind(AccountCreator::getHouseNr, AccountCreator::setHouseNr);
        binder.forField(studentStreet)
                .asRequired()
                .bind(AccountCreator::getStreet, AccountCreator::setStreet);
        binder.forField(studentCity)
                .asRequired()
                .bind(AccountCreator::getCity, AccountCreator::setCity);
        binder.forField(studentPlz)
                .asRequired()
                .bind(AccountCreator::getPlz, AccountCreator::setPlz);
        binder.forField(firstName)
                .asRequired("Please enter your first name")
                .bind(AccountCreator::getForename, AccountCreator::setForename);
        binder.forField(lastName)
                .asRequired("Please enter your last name")
                .bind(AccountCreator::getSurname, AccountCreator::setSurname);
        binder.forField(phoneNumber)
                .asRequired("Please enter your phone number")
                .bind(AccountCreator::getPhoneNumber, AccountCreator::setPhoneNumber);
        binder.forField(comboBoxStudgang)
                .asRequired("Please choose your field of study")
                .bind(AccountCreator::getStudyProgram, AccountCreator::setStudyProgram);


        studentStreet.addValueChangeListener(event -> studentAdresse = studentStreet.getValue() + " " + studentHouseNumber.getValue() + "\n" + studentPlz.getValue() + " " + studentCity.getValue());
        studentHouseNumber.addValueChangeListener(event -> studentAdresse = studentStreet.getValue() + " " + studentHouseNumber.getValue() + "\n" + studentPlz.getValue() + " " + studentCity.getValue());
        studentPlz.addValueChangeListener(event -> studentAdresse = studentStreet.getValue() + " " + studentHouseNumber.getValue() + "\n" + studentPlz.getValue() + " " + studentCity.getValue());
        studentCity.addValueChangeListener(event -> studentAdresse = studentStreet.getValue() + " " + studentHouseNumber.getValue() + "\n" + studentPlz.getValue() + " " + studentCity.getValue());

        binder.readBean(accountCreator);

        return studentForm;
    }

    private VerticalLayout buildCompanyForm() {
        var companyForm = new VerticalLayout();

        var addressTitle = new H4("Address");
        var addressForm = new FormLayout();
        companyName = new TextField("Company name");
        companyName.setRequired(true);
        street = new TextField(STREETLABEL);
        street.setRequired(true);
        studentStreet.setAllowedCharPattern("^[a-zA-Z]*$");
        houseNumber = new TextField(HOUSELABEL);
        binder.forField(houseNumber).asRequired().bind(AccountCreator::getHouseNr, AccountCreator::setHouseNr);
        plz = new TextField("PLZ");
        plz.setAllowedCharPattern("[0-9]");
        plz.setMaxLength(5);
        city = new TextField("City");
        city.setAllowedCharPattern("^[a-zA-Z]*$");
        binder.forField(plz).asRequired().bind(AccountCreator::getPlz, AccountCreator::setPlz);
        binder.forField(street).asRequired().bind(AccountCreator::getStreet, AccountCreator::setStreet);
        binder.forField(city).asRequired().bind(AccountCreator::getCity, AccountCreator::setCity);
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
                new FormLayout.ResponsiveStep(LAYOUTSIZE, 4)
        );

        var informationTitle = new H4("Information");
        var informationForm = new FormLayout();
        industry = new TextField("Industry");
        industry.setRequired(true);
        description = new TextArea("Company description");
        description.setPlaceholder("Example: Company XY is a leading provider of innovative solutions for digital transformation. We assist our clients in optimizing their business processes, strengthening customer relationships, and enhancing their competitiveness. Our services encompass consulting, development, implementation, and operation of customized software solutions, cloud services, artificial intelligence, and the Internet of Things. With years of experience and expertise in various industries such as finance, healthcare, industry, and commerce, our goal is to provide value to our customers and guide them in shaping their digital future.");
        description.setRequired(true);
        informationForm.add(
                industry,
                description
        );
        informationForm.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        var contactTitle = new H4("Contact");
        var contactForm = new FormLayout();
        phone = new TextField("Phone number");
        phone.setRequired(true);
        var fax = new TextField("Fax");
        contactForm.add(
                phone,
                fax
        );
        contactForm.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        var separator = new Hr();

        var terms = new CheckboxGroup<>("Do you agree to the Terms of Service?");
        var termsButton = new Button("View the terms of service", event -> showTermsDialog());
        terms.add(
                termsButton
        );
        terms.setItems("Yes, I agree");
        terms.setRequired(true);

        binder.bind(companyName, AccountCreator::getCompanyName, AccountCreator::setCompanyName);
        binder.bind(description, AccountCreator::getCompanyDescription, AccountCreator::setCompanyDescription);
        binder.bind(industry, AccountCreator::getCompanyIndustry, AccountCreator::setCompanyIndustry);
        binder.bind(phone, AccountCreator::getPhoneNumber, AccountCreator::setPhoneNumber);


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
            String message = "Hey " + accountCreator.getForename() + ", we are happy to have you!";

            Notification.show(message, 5000, Notification.Position.TOP_CENTER);

            //ADD ACCOUNT TO DATABASE HERE.
            userService.registerAccount(accountCreator.buildStudent(), accountCreator.getPassword());
        } else {
            userService.registerAccount(accountCreator.buildCompany(), accountCreator.getPassword());

        }
        // layout settings
        buildScreen.setAlignItems(FlexComponent.Alignment.CENTER);
        buildScreen.setJustifyContentMode(JustifyContentMode.CENTER);
        buildScreen.setWidth("100%");

        homeButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        homeButton.setIcon(new Icon(VaadinIcon.HOME));
        homeButton.setHeight("50px");
        homeButton.setWidth("50px");
        homeButton.addClickListener(buttonClickEvent -> UI.getCurrent().navigate(LoginView.class));

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
            step--;
            buildUI();
        });
        backButton.addClickShortcut(Key.ESCAPE);
        nextButton.addClickListener(buttonClickEvent -> {
            //Test if fields are correct
            if (validateInput()) {
                binder.writeBeanIfValid(accountCreator);
                step++;
                buildUI();
            }
        });
        nextButton.addClickShortcut(Key.ENTER);

        navigation.add(
                backButton,
                progressBar,
                nextButton
        );

        this.add(navigation);
    }

    private boolean validateInput() {
        switch (step) {
            case 1 -> {
                if (accType == 0 || accType == 1) {
                    return true;
                } else {
                    Notification.show("Please choose an account type", 3000, Notification.Position.TOP_CENTER);
                    return false;
                }
            }
            case 2 -> {
                boolean isUsernameValid = binder.forField(usernameField)
                        .asRequired("Username is required.")
                        .bind(AccountCreator::getUsername, AccountCreator::setUsername)
                        .validate().isError();
                boolean isEmailValid = binder.forField(emailField)
                        .asRequired("Email is required.")
                        .withValidator(new EmailValidator("Not a valid Email"))
                        .bind(AccountCreator::getEmail, AccountCreator::setEmail)
                        .validate().isError();
                boolean isPasswordValid = binder.forField(passwordField)
                        .asRequired(PASSWORDLABEL)
                        .withValidator(new PasswordValidator())
                        .withValidator(password -> confirmPasswordField.getValue().equals(password), PASSWORDMATCHLABEL)
                        .bind(AccountCreator::getPassword, AccountCreator::setPassword)
                        .validate().isError();
                boolean isConfirmPasswordValid = binder.forField(confirmPasswordField)
                        .asRequired(PASSWORDLABEL)
                        .withValidator(new PasswordValidator())
                        .withValidator(password -> passwordField.getValue().equals(password), PASSWORDMATCHLABEL)
                        .bind(AccountCreator::getPassword, AccountCreator::setPassword)
                        .validate().isError();
                if (!userUnique(usernameField.getValue())) {
                    Notification.show("Username already exists, please choose an other one", 3000, Notification.Position.TOP_CENTER);
                    return false;
                }
                if (isUsernameValid || isEmailValid || isPasswordValid || isConfirmPasswordValid) {
                    Notification.show("Please fill in the required fields correctly", 3000, Notification.Position.TOP_CENTER);
                    return false;
                }
                return true;
            }
            case 3 -> {
                if (accType == 0) {
                    return validateStudentForm();
                } else {
                    return validateCompanyForm();
                }
            }
            case 4 -> {
                // No validation required for the final step
                return true;
            }
            default -> throw new NotImplementedException();
        }

    }
    private void showTermsDialog() {
        Dialog termsDialog = new Dialog();
        VerticalLayout dialogLayout = new VerticalLayout();

        // Create a TextArea with the terms of service text
        TextArea termsTextArea = new TextArea();
        termsTextArea.setValue("Effective Date: 16.01.2024\n\n" +
                "Welcome to coll@hbrs! Before using our platform, please carefully read and understand the following Terms of Use. By accessing or using our platform, you agree to comply with and be bound by these terms. If you do not agree with any part of these terms, please do not use our platform.\n\n" +
                "1. Acceptance of Terms\n\n" +
                "By using coll@hbrs, you agree to be bound by these Terms of Use. If you do not agree to these terms, please refrain from using our platform.\n\n" +
                "2. User Eligibility\n\n" +
                "You must be 18 years or older to use this platform. By using coll@hbrs, you represent and warrant that you have the right, authority, and capacity to enter into these Terms of Use.\n\n" +
                "3. Account Registration\n\n" +
                "a. To access certain features of the platform, you may need to create an account. You agree to provide accurate, current, and complete information during the registration process.\n\n" +
                "b. You are responsible for maintaining the confidentiality of your account and password and for restricting access to your account. You agree to accept responsibility for all activities that occur under your account.\n\n" +
                "4. Job Postings\n\n" +
                "a. Companies may post job offers on coll@hbrs. By posting a job, companies agree to provide accurate and current information about the job position, requirements, and application process.\n\n" +
                "b. Companies are responsible for the content of their job postings, ensuring they comply with applicable laws and regulations. coll@hbrs reserves the right to remove any job posting that violates these terms.\n\n" +
                "5. Student Applications\n\n" +
                "a. Students may apply for job positions posted on coll@hbrs. By applying, students agree to provide accurate and current information in their applications.\n\n" +
                "b. Students are responsible for the accuracy of the information in their applications. coll@hbrs is not responsible for any inaccuracies in student applications.\n\n" +
                "6. Communication\n\n" +
                "a. coll@hbrs may use the provided contact information to communicate with users regarding their accounts, job applications, or platform-related updates.\n\n" +
                "b. Users may opt out of non-essential communication but understand that certain communications are necessary for the proper functioning of the platform.\n\n" +
                "7. Privacy Policy\n\n" +
                "The use of coll@hbrs is also governed by our Privacy Policy. By using our platform, you agree to the terms outlined in the Privacy Policy.\n\n" +
                "8. Termination of Accounts\n\n" +
                "coll@hbrs reserves the right to terminate or suspend user accounts, remove job postings, or take other appropriate actions if users violate these Terms of Use.\n\n" +
                "9. Changes to Terms\n\n" +
                "coll@hbrs may update these Terms of Use from time to time. Users will be notified of any significant changes. Continued use of the platform after such modifications constitutes acceptance of the revised terms.\n\n" +
                "10. Governing Law\n\n" +
                "These Terms of Use shall be governed by and construed in accordance with the laws of coll@hbrs. Any disputes arising out of or in connection with these terms shall be subject to the exclusive jurisdiction of the courts of coll@hbrs.\n\n" +
                "By using coll@hbrs, you acknowledge that you have read, understood, and agree to be bound by these Terms of Use. If you have any questions, please contact us at support@collhbrs.de.\n\n" +
                "Thank you for using coll@hbrs!");
        termsTextArea.setWidth("600px"); // Set desired width
        termsTextArea.setHeight("400px"); // Set desired height

        // Close button
        Button closeButton = new Button("Close", event -> termsDialog.close());

        dialogLayout.add(termsTextArea, closeButton);
        termsDialog.add(dialogLayout);

        termsDialog.open();
    }
    private boolean validateStudentForm() {
        final String requiredMessage = "This field is required";
        if (studentStreet.isEmpty()
                || studentHouseNumber.isEmpty()
                || studentPlz.isEmpty()
                || studentCity.isEmpty()) {
            // Validation for required fields
            if (studentStreet.isEmpty()) {
                studentStreet.setInvalid(true);
                studentStreet.setErrorMessage(requiredMessage);
            }
            if (studentHouseNumber.isEmpty()) {
                studentHouseNumber.setInvalid(true);
                studentHouseNumber.setErrorMessage(requiredMessage);
            }
            if (studentPlz.isEmpty()) {
                studentPlz.setInvalid(true);
                studentPlz.setErrorMessage(requiredMessage);
            }
            if (studentCity.isEmpty()) {
                studentCity.setInvalid(true);
                studentCity.setErrorMessage(requiredMessage);
            }
            return false;
        }

        // Clear validation errors if fields are filled
        studentStreet.setInvalid(false);
        studentHouseNumber.setInvalid(false);
        studentPlz.setInvalid(false);
        studentCity.setInvalid(false);

        // Additional validation logic can be added here

        return true;
    }

    private boolean validateCompanyForm() {
        String companyNameValue = companyName.getValue();
        String streetValue = street.getValue();
        String houseNumberValue = houseNumber.getValue();
        String plzValue = plz.getValue();
        String cityValue = city.getValue();
        String industryValue = industry.getValue();
        String descriptionValue = description.getValue();
        String phoneValue = phone.getValue();

        // Check if any required fields are empty
        if (companyNameValue.isEmpty() || streetValue.isEmpty() || houseNumberValue.isEmpty()
                || plzValue.isEmpty() || cityValue.isEmpty() || industryValue.isEmpty()
                || descriptionValue.isEmpty() || phoneValue.isEmpty()) {
            Notification.show("Please fill in all required fields", 3000, Notification.Position.TOP_CENTER);
            if (companyNameValue.isEmpty()) {
                companyName.setInvalid(true);
                companyName.setErrorMessage("Company name is required");
            }
            if (streetValue.isEmpty()) {
                street.setInvalid(true);
                street.setErrorMessage("Street name is required");
            }
            if (houseNumberValue.isEmpty()) {
                houseNumber.setInvalid(true);
                houseNumber.setErrorMessage("House number is required");
            }
            if (plzValue.isEmpty()) {
                plz.setInvalid(true);
                plz.setErrorMessage("PLZ is required");
            }
            if (cityValue.isEmpty()) {
                city.setInvalid(true);
                city.setErrorMessage("City is required");
            }
            if (industryValue.isEmpty()) {
                industry.setInvalid(true);
                industry.setErrorMessage("Industry is required");
            }
            if (descriptionValue.isEmpty()) {
                description.setInvalid(true);
                description.setErrorMessage("Company description is required");
            }
            if (phoneValue.isEmpty()) {
                phone.setInvalid(true);
                phone.setErrorMessage("Phone number is required");
            }
            return false;
        }

        // Clear validation errors if fields are filled
        companyName.setInvalid(false);
        street.setInvalid(false);
        houseNumber.setInvalid(false);
        plz.setInvalid(false);
        city.setInvalid(false);
        industry.setInvalid(false);
        description.setInvalid(false);
        phone.setInvalid(false);

        // Additional validation logic can be added here

        return true;
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

    private boolean userUnique(String username) {
        return accountRepository.findByUsername(username).isEmpty();
    }

}
