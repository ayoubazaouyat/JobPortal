package teapot.collat_hbrs.views.components;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import teapot.collat_hbrs.backend.JobAdvertisement;

public class JobInformationBuilder extends VerticalLayout {

    private final JobAdvertisement job;

    /**
     * Helper class to generate the job information
     *
     * @param job Job advertisement
     */
    public JobInformationBuilder(JobAdvertisement job) {
        this.job = job;
        buildJobInfo();
    }

    /**
     * Builds the different components and adds them to the layout
     */
    private void buildJobInfo() {
        H3 jobTitle = new H3(job.getTitle());
        Span companyName = new Span("by " + job.getCompany().getCompanyName());

        add(
                jobTitle,
                companyName,
                new Hr()
        );

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

        generateDescription();

        add(
                hourlyWage,
                jobType,
                homeoffice,
                requirements,
                employeeBenefits
        );
    }

    /**
     * Generates the job description
     */
    private void generateDescription() {
        Paragraph description = new Paragraph();
        String des = job.getTextDescription();
        description.setText(des != null ? des : "This job offer has no description");

        add(new H4("Description"), description);
    }

}