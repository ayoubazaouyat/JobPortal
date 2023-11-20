package teapot.collat_hbrs.views;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("About Us")
@Route(value = "about_us", layout = MainLayout.class)
@Uses(Icon.class)
@AnonymousAllowed
public class AboutUsView extends Composite<VerticalLayout> {

    public AboutUsView() {
        H2 h2 = new H2();
        Paragraph textMedium = new Paragraph();
        H2 h22 = new H2();
        Paragraph textMedium2 = new Paragraph();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        h2.setText("What is call@hbrs?");
        h2.setWidth("max-content");
        textMedium.setText(
                "call@hbrs is a job portal designed for students, to make searching for a job easier for them, while also making it easier for companies to find new employees with a fresh mindset. Searching for a job while also studying can be a tedious process for students, so call@hbrs is intended to help students out with searching for a job while also maintaining their studies.");
        textMedium.setWidth("100%");
        textMedium.setHeight("100px");
        textMedium.getStyle().set("font-size", "var(--lumo-font-size-m)");
        h22.setText("The Team");
        h22.setWidth("max-content");
        textMedium2.setText("Aicha, Amina, Assia, Ayoub, David, Lucas, Lorin, Markus, Pascal, Rene, Samar, Yoorim");
        textMedium2.setWidth("100%");
        textMedium2.getStyle().set("font-size", "var(--lumo-font-size-m)");
        getContent().add(h2);
        getContent().add(textMedium);
        getContent().add(h22);
        getContent().add(textMedium2);
    }
}