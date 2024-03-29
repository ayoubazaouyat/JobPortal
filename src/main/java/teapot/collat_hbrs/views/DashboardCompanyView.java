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
import teapot.collat_hbrs.backend.security.AccountService;
import teapot.collat_hbrs.backend.security.JobAdvertisementService;
import teapot.collat_hbrs.frontend.Format;
import teapot.collat_hbrs.views.components.JobListingWidget;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@PageTitle("Dashboard (Company)")
@Route(value = "dash_com", layout = MainLayout.class)
@Uses(Icon.class)
@RolesAllowed("COMPANY")
public class DashboardCompanyView extends Composite<VerticalLayout> {

    private final VerticalLayout results = new VerticalLayout();
    private final JobAdvertisementService jobAdvertisementService;
    private final String companyName;


    private HorizontalLayout applJobsContainer;

    public DashboardCompanyView(JobAdvertisementService jobAdvertisementService, AccountService accountService) {
        this.jobAdvertisementService = jobAdvertisementService;
        companyName = ((Company) accountService.getAccount()).getCompanyName();
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
        buttonPrimary.setText("Post a new job advertisement");
        buttonPrimary.setWidth(Format.MIN_CONTENT);
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary.addClickListener(buttonClickEvent -> UI.getCurrent().navigate("/PostJob"));
        buttonPrimary2.setText("Mailbox");
        buttonPrimary2.setWidth(Format.MIN_CONTENT);
        buttonPrimary2.setMinWidth("191px");
        buttonPrimary2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary2.addClickListener(buttonClickEvent -> UI.getCurrent().navigate("/inbox"));
        h52.setText("Current job advertisements:");
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

        List<JobAdvertisement> jobAdvertisements = jobAdvertisementService.getJobAdvertisementsForCompany(companyName);
        for (JobAdvertisement jobAdd : jobAdvertisements) {
            JobListingWidget jobWidget = new JobListingWidget(jobAdd);
            results.add(jobWidget);
        }

        // ------------------------

        Scroller scroller = new Scroller(results);
        scroller.setWidthFull();
        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);

        return scroller;
    }

}