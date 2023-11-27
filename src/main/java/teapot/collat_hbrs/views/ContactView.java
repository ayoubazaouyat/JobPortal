package teapot.collat_hbrs.views;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Contact")
@Route(value = "contact", layout = MainLayout.class)
@Uses(Icon.class)
@AnonymousAllowed // unangemeldete haben Zugriff auf diese Seite
public class ContactView extends Composite<VerticalLayout> {

    public ContactView() {
        H2 h2 = new H2();
        HorizontalLayout layoutRow = new HorizontalLayout();
        Button buttonPrimary = new Button();
        Button buttonPrimary2 = new Button();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H2 h22 = new H2();
        H4 h4 = new H4();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        H2 h23 = new H2();
        H4 h42 = new H4();
        Paragraph textMedium = new Paragraph();
        getContent().setWidth("100%");
        getContent().setHeight("130px");
        h2.setText("Online Contact");
        h2.setWidth("max-content");
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        buttonPrimary.setText("for Students");
        buttonPrimary.setWidth("153px");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary2.setText("for Companies");
        buttonPrimary2.setWidth("153px");
        buttonPrimary2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layoutColumn2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        h22.setText("Customer Hotline");
        h22.setWidth("max-content");
        h4.setText("01234 / 567890");
        h4.setWidth("max-content");
        layoutColumn3.setWidthFull();
        getContent().setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.setWidth("100%");
        layoutColumn3.getStyle().set("flex-grow", "1");
        h23.setText("Contact");
        h23.setWidth("max-content");
        h42.setText("coll@hbrs");
        h42.setWidth("max-content");
        textMedium.setText(
                "Musterstraße 1                                                                                                                                                                                                                              12345 Musterstadt                                                                                                                                                                                                                    Phone: 01234 / 567890                                                                                                                                                                                                              E-Mail: max@mustermann.de");
        textMedium.setWidth("100%");
        textMedium.setHeight("100px");
        textMedium.getStyle().set("font-size", "var(--lumo-font-size-m)");
        Button buttonPrimary3 = new Button();
        buttonPrimary3.setText("Back");
        buttonPrimary3.setWidth("min-content");
        buttonPrimary3.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary3.addClickListener(buttonClickEvent -> UI.getCurrent().navigate("/"));
        getContent().add(h2);
        getContent().add(layoutRow);
        layoutRow.add(buttonPrimary);
        layoutRow.add(buttonPrimary2);
        getContent().add(layoutColumn2);
        layoutColumn2.add(h22);
        layoutColumn2.add(h4);
        getContent().add(layoutColumn3);
        layoutColumn3.add(h23);
        layoutColumn3.add(h42);
        layoutColumn3.add(textMedium);
        layoutColumn3.add(buttonPrimary3);
    }
}