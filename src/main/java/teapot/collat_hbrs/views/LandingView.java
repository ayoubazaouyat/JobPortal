package teapot.collat_hbrs.views;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import javax.annotation.security.PermitAll;

@PageTitle("Landing")
@Route(value = "", layout = MainLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class LandingView extends Composite<VerticalLayout> {

    public LandingView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H1 h1 = new H1();
        Paragraph textLarge = new Paragraph();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        Button buttonPrimary = new Button();
        Button buttonSecondary = new Button();
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        HorizontalLayout layoutRow4 = new HorizontalLayout();
        RouterLink routerLink = new RouterLink();
        RouterLink routerLink2 = new RouterLink();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("120px");
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        h1.setText("Welcome to Coll@HBRS");
        h1.setWidth("max-content");
        textLarge.setText("The student-based job portal");
        textLarge.setWidth("100%");
        textLarge.getStyle().set("font-size", "var(--lumo-font-size-xl)");
        layoutRow2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.setHeight("min-content");
        buttonPrimary.setText("Log In");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary.addClickListener(buttonClickEvent -> UI.getCurrent().navigate("/login"));
        buttonSecondary.setText("Sign Up");
        buttonSecondary.setWidth("min-content");
        buttonSecondary.addClickListener(buttonClickEvent -> UI.getCurrent().navigate("/registration"));
        layoutRow3.addClassName(Gap.MEDIUM);
        layoutRow3.setWidth("100%");
        layoutRow3.setHeight("min-content");
        layoutRow4.setHeightFull();
        layoutRow3.setFlexGrow(1.0, layoutRow4);
        layoutRow4.addClassName(Gap.MEDIUM);
        layoutRow4.setWidth("100%");
        layoutRow4.setHeight("32px");
        routerLink.setText("About us");
        routerLink.setRoute(AboutUsView.class); // leite zu About Us-Seite
        layoutRow4.setAlignSelf(FlexComponent.Alignment.CENTER, routerLink);
        //routerLink.setWidth("84px");
        routerLink2.setText("Contact");
        routerLink2.setRoute(ContactView.class);
        layoutRow4.setAlignSelf(FlexComponent.Alignment.CENTER, routerLink2);
        //routerLink2.setWidth("min-content");
        getContent().add(layoutRow);
        getContent().add(layoutColumn2);
        layoutColumn2.add(h1);
        layoutColumn2.add(textLarge);
        getContent().add(layoutRow2);
        layoutRow2.add(buttonPrimary);
        layoutRow2.add(buttonSecondary);
        getContent().add(layoutRow3);
        layoutRow3.add(layoutRow4);
        layoutRow4.add(routerLink);
        layoutRow4.add(routerLink2);
    }
}