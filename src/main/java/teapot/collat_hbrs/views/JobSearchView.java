package teapot.collat_hbrs.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import teapot.collat_hbrs.backend.JobAdvertisement;
import teapot.collat_hbrs.backend.security.JobAdvertisementService;
import teapot.collat_hbrs.views.components.JobInformationWidget;
import teapot.collat_hbrs.views.components.JobResultWidget;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;


@Route(value = "jobsearch", layout = MainLayout.class)
@PageTitle("Job Search | Coll@HBRS")
@RolesAllowed({"STUDENT"})

public class JobSearchView extends VerticalLayout {

    private TextField jobTitleField;
    private NumberField hourlyWageField;
    private final VerticalLayout results = new VerticalLayout();
    private HorizontalLayout resultsContainer;
    private final List<JobResultWidget> jobs = new ArrayList<>();
    private VerticalLayout jobInfo;

    JobAdvertisementService jobAdvertisementService;

    /**
     * Constructor
     */
    public JobSearchView(JobAdvertisementService jobAdvertisementService) {
        this.jobAdvertisementService = jobAdvertisementService;

        H1 heading = new H1("Job Search");
        FormLayout search = initSearch();
        Hr separator = new Hr();
        resultsContainer();

        add(
                heading,
                search,
                separator,
                resultsContainer
        );
    }

    /**
     * Creates the search fields
     */
    private FormLayout initSearch() {
        var searchLayout = new FormLayout();
        jobTitleField = new TextField("Name");
        hourlyWageField = new NumberField("Hourly Wage");
        var searchButton = new Button("Search");
        var resetButton = new Button("Reset");

        hourlyWageField.setSuffixComponent(new Icon(VaadinIcon.EURO));
        searchButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        searchButton.setIcon(new Icon(VaadinIcon.SEARCH));
        searchButton.addClickShortcut(Key.ENTER);
        searchButton.addClickListener(event -> searchJobs()); // Hier wird der Stundenlohnwert übergeben
        resetButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        resetButton.setIcon(new Icon(VaadinIcon.REFRESH));
        resetButton.addClickListener(buttonClickEvent -> resetResults());

        searchLayout.add(
                jobTitleField,
                hourlyWageField,
                searchButton,
                resetButton
        );

        searchLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2),
                new FormLayout.ResponsiveStep("1000px", 4)
        );

        return searchLayout;
    }

    private void resultsContainer() {
        resultsContainer = new HorizontalLayout(generateResults());
        resultsContainer.setWidthFull();
    }

    private Scroller generateResults() {


        //Add job ads from database
        List<JobAdvertisement> jobAdds = jobAdvertisementService.getAllJobAdvertisements();

        for (JobAdvertisement jobAdd : jobAdds) {
            JobResultWidget jobResultWidget = new JobResultWidget(this, jobAdd);
            jobs.add(jobResultWidget);
            results.add(jobResultWidget);
        }

        // ------------------------

        Scroller scroller = new Scroller(results);
        scroller.setWidthFull();
        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);

        return scroller;
    }

    public void showJobInformation(JobAdvertisement job) {
        closeJobInformation();

        HorizontalLayout topBar = new HorizontalLayout();
        Button closeButton = new Button("Close");
        closeButton.setIcon(new Icon(VaadinIcon.CLOSE));
        closeButton.addClickListener(buttonClickEvent -> closeJobInformation());
        topBar.add(closeButton, new H3("Information (#" + job.getId() + ")"));
        topBar.setJustifyContentMode(JustifyContentMode.BETWEEN);
        topBar.setAlignItems(Alignment.BASELINE);
        jobInfo = new VerticalLayout(topBar, new JobInformationWidget(job));
        jobInfo.getStyle()
                .set("background", "var(--lumo-contrast-10pct)")
                .set("border-radius", "var(--lumo-border-radius-m)")
                .set("width", "30%");

        resultsContainer.add(jobInfo);
    }

    private void closeJobInformation() {
        try {
            resultsContainer.remove(jobInfo);
        } catch (NullPointerException e) {
            // Yes
        }
    }

    private void searchJobs() {
        double searchedHourlyWage;
        try {
            searchedHourlyWage = hourlyWageField.getValue();
        } catch (NullPointerException e) {
            searchedHourlyWage = 0.0;
        }

        results.removeAll();
        //Filter die Jobs nach dem Stundenlohn
        for (JobResultWidget jobWidget : jobs) {
            JobAdvertisement job = jobWidget.getJob();
            // Check if job title field is empty or matches the job title
            boolean titleMatch = jobTitleField.isEmpty() || job.getTitle().toLowerCase().contains(jobTitleField.getValue().toLowerCase());

            // Check if hourly wage is greater than the searched hourly wage
            boolean wageMatch = job.getHourlywage() > searchedHourlyWage;

            // add job advertisement to results list if all criteria are met
            if (titleMatch && wageMatch) {
                results.add(jobWidget);
            }
        }
    }

    private void resetResults() {
        results.removeAll();
        jobTitleField.setValue("");
        hourlyWageField.setValue(0.0);
        for (JobResultWidget jobWidget : jobs) {
            results.add(jobWidget);
        }
    }

}
