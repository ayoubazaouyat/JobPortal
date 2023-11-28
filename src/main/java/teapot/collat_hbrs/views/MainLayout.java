package teapot.collat_hbrs.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.dom.Style;
import teapot.collat_hbrs.backend.security.SecurityService;


/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private final SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        Image logo = new Image("/themes/images/logo.svg", "Logo");
        Anchor logoLink = new Anchor("/", logo);

        addToNavbar(true, toggle, logoLink);

        //Check if the user is logged in and add login/logout button accordingly
        if (securityService.isAuthenticated()) {
            String username = securityService.getAuthenticatedUser().getUsername();
            Button logout = new Button("Log out " + username, e -> securityService.logout());
            logout.addClickListener(buttonClickEvent -> UI.getCurrent().navigate(LandingView.class));
            VerticalLayout verticalLayout = new VerticalLayout(logout);
            verticalLayout.setAlignItems(FlexComponent.Alignment.END);
            addToNavbar(verticalLayout);
        } else {
            Button login = new Button("Log in");
            login.addClickListener(buttonClickEvent -> UI.getCurrent().navigate(LoginView.class));
            VerticalLayout verticalLayout = new VerticalLayout(login);
            verticalLayout.setAlignItems(FlexComponent.Alignment.END);
            addToNavbar(verticalLayout);
        }
    }

    private void addDrawerContent() {
        H2 navTitle = new H2("Navigation");
        navTitle.getStyle().setTextAlign(Style.TextAlign.CENTER);
        navTitle.getStyle().setColor("var(--lumo-secondary-text-color)");
        Header header = new Header(navTitle);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();
        nav.addItem(new SideNavItem("Post a Job", JobPostingView.class, new Icon(VaadinIcon.ACADEMY_CAP)));
        nav.addItem(new SideNavItem("Job Search", JobSearchView.class, new Icon(VaadinIcon.NOTEBOOK)));
        nav.addItem(new SideNavItem("Company Search", CompanySearchView.class, new Icon(VaadinIcon.BUILDING)));
        nav.addItem(new SideNavItem("About Us", AboutUsView.class, new Icon(VaadinIcon.INFO_CIRCLE)));
        nav.addItem(new SideNavItem("Contact", ContactView.class, new Icon(VaadinIcon.ENVELOPE)));

        return nav;
    }

    private Footer createFooter() {
        return new Footer();
    }

}
