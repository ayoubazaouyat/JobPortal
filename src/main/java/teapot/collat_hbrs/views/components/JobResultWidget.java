package teapot.collat_hbrs.views.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import teapot.collat_hbrs.backend.JobAdvertisement;
import teapot.collat_hbrs.views.JobSearchView;

public class JobResultWidget extends HorizontalLayout {

    private final JobSearchView view;
    private final JobAdvertisement job;

    /**
     * Creates a horizontal widget which displays one job result
     *
     * @param job Job Advertisement
     */
    public JobResultWidget(JobSearchView view, JobAdvertisement job) {
        this.view = view;
        this.job = job;
        buildWidget();
        setWidthFull();
    }

    /**
     * Builds the widget
     */
    private void buildWidget() {
        Image logo = new Image("images/profile_placeholder.png", job.getCompany().getCompanyName() + " Logo");
        VerticalLayout jobInformation = buildInfo();
        Button applyButton = new Button();
        Button openJobButton = new Button();

        logo.getStyle().set("margin", "0.5rem");
        logo.getStyle().set("height", "90%");
        logo.getStyle().set("border-radius", "var(--lumo-border-radius-m)");
        applyButton.setIcon(new Icon(VaadinIcon.CLIPBOARD_CHECK));
        applyButton.setText("Apply now");
        applyButton.getStyle()
                .set("margin", "0.5rem")
                .set("width", "15rem");
        openJobButton.setIcon(new Icon(VaadinIcon.INFO_CIRCLE));
        openJobButton.setText("Learn more");
        openJobButton.getStyle()
                .set("margin", "0.5rem")
                .set("width", "15rem");

        setHeight(8f, Unit.REM);
        getStyle().set("border-radius", "var(--lumo-border-radius-m)");
        getStyle().set("background", "var(--lumo-contrast-10pct)");
        setAlignItems(Alignment.CENTER);

        applyButton.addClickListener(buttonClickEvent -> UI.getCurrent().getPage().open("/apply/" + (job.getId() > 0 ? job.getId() : "null")));
        openJobButton.addClickListener(buttonClickEvent -> view.showJobInformation(job));

        add(
                logo,
                jobInformation,
                applyButton,
                openJobButton
        );
    }

    /**
     * Builds the information part of the widget
     *
     * @return Information container
     */
    private VerticalLayout buildInfo() {
        VerticalLayout container = new VerticalLayout();
        H3 title = new H3(job.getTitle());
        Icon locationIcon = new Icon(VaadinIcon.MAP_MARKER);
        HorizontalLayout quickInfo = new HorizontalLayout();
        HorizontalLayout location = new HorizontalLayout(locationIcon, new Span(job.getLocation()));

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
        location.setAlignItems(Alignment.CENTER);

        quickInfo.add(typeField, wageField, locationField);

        container.add(
                title,
                quickInfo
        );

        return container;
    }

    public JobAdvertisement getJob() {
        return job;
    }
}
