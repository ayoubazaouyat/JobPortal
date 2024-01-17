package teapot.collat_hbrs.views.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import teapot.collat_hbrs.backend.JobAdvertisement;
import teapot.collat_hbrs.views.DashboardStudentView;

public class AppliedJobWidget extends ResultsWidget {

    private final DashboardStudentView view;
    private final JobAdvertisement job;

    /**
     * Creates a horizontal widget which displays one job result
     *
     * @param job Job Advertisement
     */
    public AppliedJobWidget(DashboardStudentView view, JobAdvertisement job) {
        super(null);
        this.view = view;
        this.job = job;
        super.setInfo(buildInfo());
        super.addButtons(buildButtons());
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

        HorizontalLayout quickInfo = new HorizontalLayout();

        TextField typeField = new TextField();
        typeField.setPrefixComponent(new Icon(VaadinIcon.CLOCK));
        typeField.setReadOnly(true);
        String type = job.getFullOrPartTime();
        typeField.setValue(type != null ? type : "Not specified");

        TextField wageField = new TextField();
        wageField.setPrefixComponent(new Icon(VaadinIcon.EURO));
        wageField.setReadOnly(true);
        wageField.setValue(job.getHourlywage() + "€/hour");

        TextField locationField = new TextField();
        locationField.setPrefixComponent(new Icon(VaadinIcon.MAP_MARKER));
        locationField.setReadOnly(true);
        locationField.setValue(job.getLocation());

        container.setHeightFull();
        container.setJustifyContentMode(JustifyContentMode.CENTER);
        quickInfo.setAlignItems(Alignment.CENTER);

        quickInfo.add(typeField, wageField, locationField);

        container.add(
                title,
                quickInfo
        );

        return container;
    }

    /**
     * Builds the buttons for the widget
     *
     * @return Buttons
     */
    private Button[] buildButtons() {
        Button openJobButton = new Button();

        openJobButton.setIcon(new Icon(VaadinIcon.INFO_CIRCLE));
        openJobButton.setText("Detailed Information");
        openJobButton.getStyle()
                .set("margin", "0.5rem")
                .set("width", "15rem");

        openJobButton.addClickListener(buttonClickEvent -> view.showJobInformation(job));

        return new Button[]{openJobButton};
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
