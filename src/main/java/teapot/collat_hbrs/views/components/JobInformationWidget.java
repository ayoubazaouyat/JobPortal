package teapot.collat_hbrs.views.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import teapot.collat_hbrs.backend.JobAdvertisement;

import java.time.LocalDate;


public class JobInformationWidget extends VerticalLayout {

    private final JobAdvertisement job;

    public JobInformationWidget(JobAdvertisement job) {
        this.job = job;

        generateHeader();
        generateDescription();
        generateDetails();
        generateApplyButton();
        generateCompanyInformationButton();
    }

    private void generateHeader() {
        Image logo = new Image("images/profile_placeholder.png", job.getName() + " Logo");
        logo.setHeight("80%");
        logo.getStyle().set("border-radius", "var(--lumo-border-radius-m)");

        H3 jobTitle = new H3(job.getTitle() + " (#" + job.getJobAdvertisementId() + ")");
        Span subTitle = new Span("by " + job.getName() + " in " + job.getLocation());
        Span candidates = new Span(job.getCandidateCount() + " students already applied for this job");

        VerticalLayout shortInfo = new VerticalLayout();
        shortInfo.getStyle().set("height", "fit-content");
        shortInfo.add(jobTitle, subTitle, candidates);

        HorizontalLayout header = new HorizontalLayout(logo, shortInfo);
        header.getStyle().set("height", "15rem");

        add(header, new Hr());
    }

    private void generateDescription() {
        Paragraph description = new Paragraph();
        String des = job.getTextDescription();
        description.setText(des != null ? des : "This job offer has no description");

        add(new H4("Description"), description);
    }

    private void generateDetails() {
        TextField hourlyWage = new TextField("Hourly Wage");
        hourlyWage.setSuffixComponent(new Icon(VaadinIcon.EURO));
        hourlyWage.setReadOnly(true);
        hourlyWage.setWidthFull();
        hourlyWage.setValue(String.valueOf(job.getHourlywage()));

        TextField jobType = new TextField("Type");
        jobType.setReadOnly(true);
        jobType.setWidthFull();
        String type = job.getFullOrPartTime();
        jobType.setValue(type != null ? type : "Not specified");

        DatePicker startTime = new DatePicker("Start time");
        startTime.setReadOnly(true);
        startTime.setWidthFull();
        try {
            LocalDate time = job.getStarttime().toLocalDate();
            if (time != null) {
                startTime.setValue(time);
            }
        } catch (Exception e) {
            // Start time field will be left empty when not specified
        }

        TextField homeoffice = new TextField("Remote/Office");
        homeoffice.setReadOnly(true);
        homeoffice.setWidthFull();
        String remote = job.getRemoteOrInHouse();
        homeoffice.setValue(remote != null ? remote : "Not specified");

        TextField requirements = new TextField("Requirements");
        requirements.setReadOnly(true);
        requirements.setWidthFull();
        String req = job.getRequirements();
        requirements.setValue(req != null ? req : "This job offer has no requirements");

        TextArea employeeBenefits = new TextArea("Employee Benefits");
        employeeBenefits.setReadOnly(true);
        employeeBenefits.setWidthFull();
        String benefits = job.getEmployeeBenefits();
        employeeBenefits.setValue(benefits != null ? benefits : "This job offer does not have any benefits for the employees!");

        add(
                new H4("Details"),
                hourlyWage,
                jobType,
                startTime,
                homeoffice,
                requirements,
                employeeBenefits
        );
    }

    private void generateApplyButton() {
        Button applyButton = new Button("Apply now");
        applyButton.setIcon(new Icon(VaadinIcon.CLIPBOARD_CHECK));
        applyButton.addClickListener(buttonClickEvent -> UI.getCurrent().getPage().open("/apply/" + (job.getJobAdvertisementId() > 0 ? job.getJobAdvertisementId() : "null")));
        applyButton.getStyle().set("align-self", "center");
        applyButton.setWidthFull();
        applyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(applyButton);
    }

    private void generateCompanyInformationButton() {
        Button companyInfoButton = new Button("Company Information", new Icon(VaadinIcon.BUILDING));
        companyInfoButton.addClickListener(buttonClickEvent -> new CompanyInformationDialog(job.getCompany()).open());
        companyInfoButton.getStyle().set("align-self", "center");
        companyInfoButton.setWidthFull();

        add(companyInfoButton);
    }

}
