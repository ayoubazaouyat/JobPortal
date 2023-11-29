package teapot.collat_hbrs.views.components;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import teapot.collat_hbrs.backend.JobAdvertisement;

public class JobResultWidget extends HorizontalLayout {

    private final JobAdvertisement job;

    /**
     * Creates a horizontal widget which displays one job result
     *
     * @param job Job Advertisement
     */
    public JobResultWidget(JobAdvertisement job) {
        this.job = job;
        buildWidget();
        setWidthFull();
    }

    /**
     * Builds the widget
     */
    private void buildWidget() {
        Image logo = new Image("/themes/images/profile_placeholder.png", job.getCompany().getCompanyName() + " Logo");
        VerticalLayout jobInformation = buildInfo();
        Button openJobButton = new Button();

        logo.getStyle().set("margin", "0.5rem");
        logo.getStyle().set("height", "90%");
        logo.getStyle().set("border-radius", "var(--lumo-border-radius-m)");
        openJobButton.setIcon(new Icon(VaadinIcon.INFO_CIRCLE));
        openJobButton.setText("Learn more");
        openJobButton.getStyle().set("margin", "0.5rem");

        setHeight(8f, Unit.REM);
        getStyle().set("border-radius", "var(--lumo-border-radius-m)");
        getStyle().set("background", "var(--lumo-contrast-10pct)");
        setAlignItems(Alignment.CENTER);

        add(
                logo,
                jobInformation,
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
        Icon clockIcon = new Icon(VaadinIcon.CLOCK);
        Icon euroIcon = new Icon(VaadinIcon.EURO);
        Icon locationIcon = new Icon(VaadinIcon.MAP_MARKER);
        HorizontalLayout quickInfo = new HorizontalLayout(clockIcon, new Span("Full time"), euroIcon, new Span(job.getHourlywage() + "€/hour"));
        HorizontalLayout location = new HorizontalLayout(locationIcon, new Span(job.getLocation()));

        container.setHeightFull();
        container.setJustifyContentMode(JustifyContentMode.CENTER);
        quickInfo.setAlignItems(Alignment.CENTER);
        location.setAlignItems(Alignment.CENTER);

        container.add(
                title,
                quickInfo,
                location
        );

        return container;
    }

}
