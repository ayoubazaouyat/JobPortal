package teapot.collat_hbrs.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Coll@HBRS")
@Route(value = "", layout = MainLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class LandingView extends VerticalLayout {

    public LandingView() {
        VerticalLayout container = new VerticalLayout();

        H1 welcomeTitle = new H1("Welcome to Coll@HBRS");
        welcomeTitle.getStyle()
                .set("margin-bottom", "0.5rem")
                .set("margin-top", "0.5rem");

        H3 subtitle = new H3("The student-based job portal");
        subtitle.getStyle().set("margin-top", "0");

        HorizontalLayout buttons = new HorizontalLayout();
        Button loginButton = new Button("Log In");
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        loginButton.addClickListener(buttonClickEvent -> UI.getCurrent().navigate("/login"));
        Button registerButton = new Button("Sign Up");
        registerButton.addClickListener(buttonClickEvent -> UI.getCurrent().navigate("/registration"));
        buttons.add(loginButton, registerButton);

        HorizontalLayout links = new HorizontalLayout();
        RouterLink aboutLink = new RouterLink();
        aboutLink.setText("About us");
        aboutLink.setRoute(AboutUsView.class);
        RouterLink contactLink = new RouterLink();
        contactLink.setText("Contact");
        contactLink.setRoute(ContactView.class);
        links.add(aboutLink, contactLink);

        container.add(
                welcomeTitle,
                subtitle,
                buttons,
                links
        );

        container.setAlignItems(Alignment.CENTER);
        container.setWidth("50%");
        container.getStyle()
                .set("background", "white")
                .set("border-radius", "var(--lumo-border-radius-m)");

        add(container);

        setAlignItems(Alignment.CENTER);

        getStyle()
                .set("background-image", "url(images/hbrs_sankt_augustin.jpg)")
                .set("background-size", "cover")
                .set("background-position", "center")
                .set("height", "100%")
                .set("width", "100%");
    }
}