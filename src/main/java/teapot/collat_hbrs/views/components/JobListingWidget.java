package teapot.collat_hbrs.views.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import teapot.collat_hbrs.backend.JobAdvertisement;

public class JobListingWidget extends ResultsWidget {

    private final JobAdvertisement job;

    /**
     * Creates a horizontal widget which displays one job listing
     *
     * @param job Job Advertisement
     */
    public JobListingWidget(JobAdvertisement job) {
        super(null);
        this.job = job;
        super.setInfo(buildInfo());
        super.addButtons(buildButton());
    }

    /**
     * Builds the information part of the widget
     *
     * @return Information container
     */
    private VerticalLayout buildInfo() {
        VerticalLayout container = new VerticalLayout();

        Span title = new Span(job.getTitle());
        title.getStyle()
                .set("font-weight", "bold")
                .set("font-size", "20px");

        container.setHeightFull();
        container.setJustifyContentMode(JustifyContentMode.CENTER);

        container.add(title);

        return container;
    }

    /**
     * Builds the buttons for the widget
     *
     * @return Buttons
     */
    private Button[] buildButton() {
        Button jobInformationButton = new Button();

        jobInformationButton.setIcon(new Icon(VaadinIcon.INFO_CIRCLE));
        jobInformationButton.setText("Detailed Information");
        jobInformationButton.getStyle()
                .set("margin", "0.5rem")
                .set("width", "15rem");

        jobInformationButton.addClickListener(buttonClickEvent -> new JobInformationDialog(job, false, true).open());

        return new Button[]{jobInformationButton};
    }

    /**
     * Returns the job advertisement
     *
     * @return Job advertisement
     */
    public JobAdvertisement getJob() {
        return job;
    }

}
