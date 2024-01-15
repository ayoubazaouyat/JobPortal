package teapot.collat_hbrs.views;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import teapot.collat_hbrs.backend.JobAdvertisement;
import teapot.collat_hbrs.backend.security.JobAdvertisementService;
import teapot.collat_hbrs.frontend.Format;
import teapot.collat_hbrs.views.components.JobInformationWidget;
import teapot.collat_hbrs.views.components.JobListingWidget;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Dashboard (Company)")
@Route(value = "dash_com", layout = MainLayout.class)
@Uses(Icon.class)
@RolesAllowed("COMPANY")
public class DashboardCompanyView extends Composite<VerticalLayout> {
    private VerticalLayout jobInfo;
    private final List<JobListingWidget> jobs = new ArrayList<>();
    private final VerticalLayout results = new VerticalLayout();
    private final JobAdvertisementService jobAdvertisementService;

    private HorizontalLayout applJobsContainer;
    public DashboardCompanyView(JobAdvertisementService jobAdvertisementService) {
        this.jobAdvertisementService = jobAdvertisementService;
        functionApplJobsContainer();
        H2 h2 = new H2();
        Hr hr = new Hr();
        H5 h5 = new H5();
        HorizontalLayout layoutRow = new HorizontalLayout();
        Button buttonPrimary = new Button();
        Button buttonPrimary2 = new Button();
        Hr hr2 = new Hr();
        H5 h5_2 = new H5();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        MultiSelectListBox avatarItems = new MultiSelectListBox();
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
        h5_2.setText("Current job advertisements:");
        h5_2.setWidth(Format.MAX_CONTENT);
        layoutRow2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set(Format.FLEX_GROW, "1");
        avatarItems.setWidth(Format.MIN_CONTENT);
        //setAvatarItemsSampleData(avatarItems);
        getContent().add(h2);
        getContent().add(hr);
        getContent().add(h5);
        getContent().add(layoutRow);
        layoutRow.add(buttonPrimary);
        layoutRow.add(buttonPrimary2);
        getContent().add(hr2);
        getContent().add(h5_2);
        getContent().add(layoutRow2);
        layoutRow2.add(applJobsContainer);
    }
    private void functionApplJobsContainer() {
        applJobsContainer = new HorizontalLayout(generateResults());
        applJobsContainer.setWidthFull();
    }

    private Scroller generateResults() {

        List<JobAdvertisement> jobAdvertisements = jobAdvertisementService.getJobAdvertisementsForCompany("Microsoft");
        for (JobAdvertisement jobAdd: jobAdvertisements) {
            JobListingWidget jobWidget = new JobListingWidget(this, jobAdd);
            jobs.add(jobWidget);
            results.add(jobWidget);
        }

        // ------------------------

        Scroller scroller = new Scroller(results);
        scroller.setWidthFull();
        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);

        return scroller;
    }

    /*private void setAvatarItemsSampleData(MultiSelectListBox multiSelectListBox) {
        record Person(String name, String profession) {
        }
        ;
        List<Person> data = List.of(new Person("Aria Bailey", "Endocrinologist"), new Person("Aaliyah Butler", "Nephrologist"), new Person("Eleanor Price", "Ophthalmologist"), new Person("Allison Torres", "Allergist"), new Person("Madeline Lewis", "Gastroenterologist"));
        multiSelectListBox.setItems(data);
        multiSelectListBox.setRenderer(new ComponentRenderer(item -> {
            AvatarItem avatarItem = new AvatarItem();
            avatarItem.setHeading(((Person) item).name);
            avatarItem.setDescription(((Person) item).profession);
            avatarItem.setAvatar(new Avatar(((Person) item).name));
            return avatarItem;
        }));
    }*/
    public void showJobInformation(JobAdvertisement job) {
        closeJobInformation();

        HorizontalLayout topBar = new HorizontalLayout();
        Button closeButton = new Button("Close");
        closeButton.setIcon(new Icon(VaadinIcon.CLOSE));
        closeButton.addClickListener(buttonClickEvent -> closeJobInformation());
        topBar.add(closeButton, new H3("Information"));
        topBar.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        topBar.setAlignItems(FlexComponent.Alignment.BASELINE);
        jobInfo = new VerticalLayout(topBar, new JobInformationWidget(job));
        jobInfo.getStyle()
                .set("background", "var(--lumo-contrast-10pct)")
                .set("border-radius", "var(--lumo-border-radius-m)");

        applJobsContainer.add(jobInfo);
    }
    private void closeJobInformation() {
        try {
            applJobsContainer.remove(jobInfo);
        } catch (NullPointerException e) {
            // Yes
        }
    }
}