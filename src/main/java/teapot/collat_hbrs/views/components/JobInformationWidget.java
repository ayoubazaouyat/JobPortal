package teapot.collat_hbrs.views.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouteParameters;
import teapot.collat_hbrs.backend.JobAdvertisement;
import teapot.collat_hbrs.views.ApplyView;


public class JobInformationWidget extends VerticalLayout {

    private final JobAdvertisement job;

    public JobInformationWidget(JobAdvertisement job) {
        this.job = job;

        generateInfo();
        generateApplyButton();
        generateCompanyInformationButton();
    }

    private void generateInfo() {
        add(new JobInformationBuilder(job));
    }

    private void generateApplyButton() {
        Button applyButton = new Button("Apply now");
        applyButton.setIcon(new Icon(VaadinIcon.CLIPBOARD_CHECK));
        applyButton.addClickListener(buttonClickEvent -> UI.getCurrent().navigate(ApplyView.class, job.getJobAdvertisementId().toString()));
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
