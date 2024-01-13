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
import teapot.collat_hbrs.frontend.Format;

@PageTitle("Contact")
@Route(value = "contact", layout = MainLayout.class)
@Uses(Icon.class)
@AnonymousAllowed // unangemeldete haben Zugriff auf diese Seite
public class ContactView extends Composite<VerticalLayout> {

    public ContactView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        VerticalLayout layoutColumn3 = new VerticalLayout();

        H2 h2 = new H2();
        H2 h23 = new H2();
        H4 h4 = new H4();
        H4 h42 = new H4();

        Paragraph textMedium = new Paragraph();
        getContent().setWidth("100%");
        getContent().setHeight("130px");

        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set(Format.FLEX_GROW, "1");

        layoutColumn2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set(Format.FLEX_GROW, "1");

        layoutColumn3.setWidthFull();
        getContent().setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.setWidth("100%");
        layoutColumn3.getStyle().set(Format.FLEX_GROW, "1");

        Button buttonPrimary3 = new Button();
        buttonPrimary3.setText("Back");
        buttonPrimary3.setWidth(Format.MIN_CONTENT);
        buttonPrimary3.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary3.addClickListener(buttonClickEvent -> UI.getCurrent().navigate("/"));

        h2.setText("Customer Hotline");
        h2.setWidth(Format.MAX_CONTENT);
        h4.setText("01234 / 567890");
        h4.setWidth(Format.MAX_CONTENT);

        h23.setText("Contact");
        h23.setWidth(Format.MAX_CONTENT);
        h42.setText("coll@hbrs");
        h42.setWidth(Format.MAX_CONTENT);
        textMedium.setText("Musterstraße 1 12345 Musterstadt Phone: 01234 / 567890 E-Mail: max@mustermann.de");
        textMedium.setWidth("100%");
        textMedium.setHeight("100px");
        textMedium.getStyle().set("font-size", "var(--lumo-font-size-m)");

        getContent().add(layoutRow);
        getContent().add(layoutColumn2);
        layoutColumn2.add(h2);
        layoutColumn2.add(h4);
        getContent().add(layoutColumn3);
        layoutColumn3.add(h23);
        layoutColumn3.add(h42);
        layoutColumn3.add(textMedium);
        layoutColumn3.add(buttonPrimary3);
    }
}