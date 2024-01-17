package teapot.collat_hbrs.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import teapot.collat_hbrs.backend.security.SecurityService;

import java.util.ArrayList;
import java.util.Collection;


/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private final SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        addHeaderContent();
    }

    private void addHeaderContent() {
        Image logo = new Image("images/logo.svg", "Logo");
        Anchor logoLink = new Anchor("/", logo);
        logoLink.getStyle().set("padding", "0 1rem");

        Tabs tabs = getTabs();
        tabs.setWidthFull();

        addToNavbar(true, logoLink, tabs);

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

    private Tabs getTabs() {
        Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
        if (securityService.isAuthenticated()) {
            authorities = securityService.getAuthenticatedUser().getAuthorities();
        }
        Tabs tabs = new Tabs();
        tabs.getStyle().set("margin", "auto");
        tabs.add(
                createTab("Home", LandingView.class, new Icon(VaadinIcon.HOME))
        );
        if (authorities.contains(new SimpleGrantedAuthority("ROLE_COMPANY"))) {
            tabs.add(
                    createTab("Post a Job", JobPostingView.class, new Icon(VaadinIcon.PLUS)),
                    createTab("Jobs List", MyJobListView.class, new Icon(VaadinIcon.EDIT)),
                    createTab("Inbox", InboxView.class, new Icon(VaadinIcon.INBOX))
            );
        }
        if (authorities.contains(new SimpleGrantedAuthority("ROLE_STUDENT"))) {
            tabs.add(
                    createTab("Job Search", JobSearchView.class, new Icon(VaadinIcon.SEARCH)),
                    createTab("Inbox", InboxView.class, new Icon(VaadinIcon.INBOX))
            );
        }

        tabs.add(createTab("About Us", AboutUsView.class, new Icon(VaadinIcon.INFO_CIRCLE)),
                createTab("Contact", ContactView.class, new Icon(VaadinIcon.QUESTION_CIRCLE)));


        return tabs;
    }

    private Tab createTab(String viewName, Class<? extends Component> view, Icon icon) {
        Tab tab = new Tab();

        RouterLink link = new RouterLink(view);
        link.add(viewName);
        link.setTabIndex(-1);

        tab.add(icon, link);

        return tab;
    }

}
