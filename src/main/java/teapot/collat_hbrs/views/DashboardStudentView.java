package teapot.collat_hbrs.views;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import teapot.collat_hbrs.backend.Company;
import teapot.collat_hbrs.backend.JobAdvertisement;
import teapot.collat_hbrs.backend.Student;
import teapot.collat_hbrs.backend.security.AccountService;
import teapot.collat_hbrs.backend.security.JobAdvertisementService;
import teapot.collat_hbrs.frontend.Format;
import teapot.collat_hbrs.views.components.AppliedJobWidget;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@PageTitle("Dashboard (Student)")
@Route(value = "dash_st", layout = MainLayout.class)
@Uses(Icon.class)
@RolesAllowed({"STUDENT"})
public class DashboardStudentView extends Composite<VerticalLayout> {
    private final Random random = new Random();
    private final VerticalLayout results = new VerticalLayout();
    private final JobAdvertisementService jobAdvertisementService;

    private HorizontalLayout applJobsContainer;

    private final String username;

    //private final List<String> skills;

    public DashboardStudentView(JobAdvertisementService jobAdvertisementService, AccountService accountService) {
        //skills = (((Student) accountService.getAccount()).getSkills());
        //System.out.println("" + ((Student) accountService.getAccount()).getSkills());
        username = (accountService.getAccount()).getUsername();
        this.jobAdvertisementService = jobAdvertisementService;
        functionApplJobsContainer();
        H2 h2 = new H2();
        Hr hr = new Hr();
        H5 h5 = new H5();
        HorizontalLayout layoutRow = new HorizontalLayout();
        Button buttonPrimary = new Button();
        Button buttonPrimary2 = new Button();
        Hr hr2 = new Hr();
        H5 h52 = new H5();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        getContent().setWidth("100%");
        getContent().getStyle().set(Format.FLEX_GROW, "1");
        h2.setText("Welcome!");
        h2.setWidth(Format.MAX_CONTENT);
        h5.setText("Options");
        h5.setWidth(Format.MAX_CONTENT);
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set(Format.FLEX_GROW, "1");
        buttonPrimary.setText("Search for Job Listings");
        buttonPrimary.setWidth(Format.MIN_CONTENT);
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary.addClickListener(buttonClickEvent -> UI.getCurrent().navigate("/jobsearch"));
        buttonPrimary2.setText("Mailbox");
        buttonPrimary2.setWidth(Format.MIN_CONTENT);
        buttonPrimary2.setMinWidth("191px");
        buttonPrimary2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary2.addClickListener(buttonClickEvent -> UI.getCurrent().navigate("/inbox"));
        h52.setText("Quick Selection:");
        h52.setWidth(Format.MAX_CONTENT);
        layoutRow2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set(Format.FLEX_GROW, "1");
        getContent().add(h2);
        getContent().add(hr);
        getContent().add(h5);
        getContent().add(layoutRow);
        layoutRow.add(buttonPrimary);
        layoutRow.add(buttonPrimary2);
        getContent().add(hr2);
        getContent().add(h52);
        getContent().add(layoutRow2);
        layoutRow2.add(applJobsContainer);
    }

    private void functionApplJobsContainer() {
        applJobsContainer = new HorizontalLayout(generateResults());
        applJobsContainer.setWidthFull();
    }

    private Scroller generateResults() {
        //Not working yet as method is not implemented

        /*
        List<JobAdvertisement> jobAdvertisements = jobAdvertisementService.getJobAdvertisementsForApplicant(username);
        for (JobAdvertisement jobAdd : jobAdvertisements) {
            JobListingWidget jobWidget = new JobListingWidget(jobAdd);
            results.add(jobWidget);
        }

        // ------------------------

        Scroller scroller = new Scroller(results);
        scroller.setWidthFull();
        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);

        return scroller;
        */

        // get three random jobs for demonstration purposes
        //int found = 0;
        List<JobAdvertisement> jobAdvertisements = jobAdvertisementService.getAllJobAdvertisements();

        //for(int z = 0; z < jobAdvertisements.size(); z++) {
            //System.out.println("a," + jobAdvertisements.get(z).getExpectations());
            //System.out.println("b," + skills);
            /*for(int j = 0; j < skills.size(); j++) {
                if(jobAdvertisements.get(z).getExpectations() == null || skills.get(z) == null) {
                    break;
                }

                if(jobAdvertisements.get(z).getExpectations().equals(skills.get(z))) {
                    AppliedJobWidget jobWidget = new AppliedJobWidget(jobAdvertisements.get(z));
                    jobAdvertisements.remove(z);

                    results.add(jobWidget);
                    found++;
                }
                if(found > 2) {
                    break;
                }
            }
            if(found > 2) {
                break;
            }*/
        //}

        for (int i = 0; i < 3; i++) {
            if (jobAdvertisements.isEmpty()) break;
            //if (found > 2) {
            //    break;
            //}

            int pos = random.nextInt(jobAdvertisements.size());
            AppliedJobWidget jobWidget = new AppliedJobWidget(jobAdvertisements.get(pos));
            jobAdvertisements.remove(pos);

            results.add(jobWidget);
            //found++;
        }

        // ------------------------

        Scroller scroller = new Scroller(results);
        scroller.setWidthFull();
        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);

        return scroller;
    }

}