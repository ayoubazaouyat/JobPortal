package teapot.collat_hbrs.views.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import teapot.collat_hbrs.backend.JobAdvertisement;

public class JobInformationDialog extends Dialog {

    private final JobAdvertisement job;
    private boolean applyEnabled = true;

    private boolean modal = true;

    /**
     * Popup dialog showing the detailed job information
     * using default values (modal and apply button enabled)
     *
     * @param job Job advertisement
     */
    public JobInformationDialog(JobAdvertisement job) {
        this.job = job;
        buildDialog();
    }

    /**
     * Popup dialog showing the detailed job information
     *
     * @param job          Job advertisement
     * @param applyEnabled If the apply button is enabled
     * @param modal        If the dialog is modal
     */
    public JobInformationDialog(JobAdvertisement job, boolean applyEnabled, boolean modal) {
        this.job = job;
        this.applyEnabled = applyEnabled;
        this.modal = modal;
        buildDialog();
    }

    /**
     * Builds the actual dialog
     */
    private void buildDialog() {
        setHeaderTitle("Job Info");
        if (!modal) {
            setModal(false);
            setDraggable(true);
        }

        add(new JobInformationBuilder(job));

        Button companyInformationButton = new Button("Company Info", new Icon(VaadinIcon.BUILDING));
        companyInformationButton.addClickListener(buttonClickEvent -> new CompanyInformationDialog(job.getCompany()).open());

        Button applyButton = new Button("Apply", new Icon(VaadinIcon.CLIPBOARD_CHECK));
        applyButton.addClickListener(buttonClickEvent -> UI.getCurrent().getPage().open("/apply/" + (job.getJobAdvertisementId() > 0 ? job.getJobAdvertisementId() : "null")));
        applyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        if (!applyEnabled) applyButton.setEnabled(false);

        Button closeButton = new Button("Close");
        closeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        closeButton.addClickListener(buttonClickEvent -> close());

        getFooter().add(companyInformationButton, applyButton, closeButton);

        setWidth("40%");
    }

}