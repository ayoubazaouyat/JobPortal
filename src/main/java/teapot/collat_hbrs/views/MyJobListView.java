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

import javax.annotation.security.RolesAllowed;
import java.util.List;

import static org.aspectj.weaver.UnresolvedType.add;
@Route(value = "MyJobs", layout = MainLayout.class)
@PageTitle("My Job List | Coll@HBRS")
@RolesAllowed("COMPANY")
public class MyJobListView extends VerticalLayout {

    private final JobAdvertisementService jobAdvertisementService;

    public MyJobListView(JobAdvertisementService jobAdvertisementService) {
        this.jobAdvertisementService = jobAdvertisementService;
        initJobList();
    }

    private void initJobList() {
        Grid<JobAdvertisement> jobGrid = new Grid<>(JobAdvertisement.class);
        jobGrid.setItems(jobAdvertisementService.getAllJobAdvertisements());

        // Definieren Sie die anzuzeigenden Spalten (Sie können dies je nach Ihren Anforderungen anpassen)
        jobGrid.setColumns("company.companyName", "textDescription", "offerAge");
        jobGrid.getColumnByKey("company.companyName").setHeader("Company Name");
        jobGrid.getColumnByKey("textDescription").setHeader("Description");
        jobGrid.getColumnByKey("offerAge").setHeader("Application Deadline");

        // Hinzufügen von Editierfunktionen zu jeder Zeile
        jobGrid.addComponentColumn(job -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(event -> {
                // Hier können Sie die Logik für die Bearbeitung implementieren
                UI.getCurrent().navigate(JobPostingView.class);
                //Notification.show("Edit button clicked for Job ID: " + job.getId());
            });
            return editButton;
        }).setHeader("Edit");

        add(jobGrid);
    }
}
