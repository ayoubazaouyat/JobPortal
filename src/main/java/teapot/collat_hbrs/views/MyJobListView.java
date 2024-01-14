package teapot.collat_hbrs.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import teapot.collat_hbrs.backend.JobAdvertisement;
import teapot.collat_hbrs.backend.security.JobAdvertisementService;

import java.util.List;

import static org.aspectj.weaver.UnresolvedType.add;
@Route(value = "MyJobs", layout = MainLayout.class)
@PageTitle("My Job List | Coll@HBRS")
public class MyJobListView extends VerticalLayout {

    private final JobAdvertisementService jobAdvertisementService;
    private final JobPostingView jobPostingView;  // Referenz auf die JobPostingView
    private Grid<JobAdvertisement> jobGrid = new Grid<>(JobAdvertisement.class);

    public MyJobListView(JobAdvertisementService jobAdvertisementService, JobPostingView jobPostingView) {
        this.jobAdvertisementService = jobAdvertisementService;
        this.jobPostingView = jobPostingView;

        add(new Button("Back to Landing Page", VaadinIcon.ARROW_LEFT.create(), e -> navigateToMain()));

        jobGrid.setColumns("company.companyName", "textDescription", "offerAge");
        jobGrid.getColumnByKey("company.companyName").setHeader("Company Name");
        jobGrid.getColumnByKey("textDescription").setHeader("Description");
        jobGrid.getColumnByKey("offerAge").setHeader("Application Deadline");

        populateJobGrid();

        jobGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                navigateToJobEditView(event.getValue().getId());
            }
        });

        add(jobGrid);
    }

    private void navigateToMain() {
        UI.getCurrent().navigate("");
    }

    private void navigateToJobEditView(Long jobId) {
        jobPostingView.displayFormWithPreviousData();  // Hier die gewünschte Logik zur Anzeige des ausgewählten Jobs in JobPostingView
        //UI.getCurrent().navigate(JobEditView.class, jobId.toString());
    }

    private void populateJobGrid() {
        jobGrid.setItems(jobAdvertisementService.getJobAdvertisementsForCompany("YOUR_COMPANY_NAME"));
    }
}
