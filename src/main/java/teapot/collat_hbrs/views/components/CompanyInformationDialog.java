package teapot.collat_hbrs.views.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import teapot.collat_hbrs.backend.Company;

public class CompanyInformationDialog extends Dialog {

    private final Company company;

    public CompanyInformationDialog(Company company) {
        this.company = company;

        buildDialog();
    }

    /**
     * Builds the information dialog
     */
    private void buildDialog() {
        setHeaderTitle(company.getCompanyName());

        VerticalLayout layout = new VerticalLayout();

        Span industry = new Span("Industry: " + company.getIndustry());

        Div rating = new Div(new Span("Rating: " + 5.0 + " "), generateStars()); // TODO add company rating

        TextArea descriptionField = new TextArea();
        descriptionField.setLabel("Description");
        descriptionField.setValue(company.getCompanyDescription());
        descriptionField.setReadOnly(true);
        descriptionField.setWidthFull();

        TextArea addressField = new TextArea();
        addressField.setLabel("Address");
        addressField.setValue(company.getAddress());
        addressField.setReadOnly(true);
        addressField.setWidthFull();

        Div emailField = new Div(new Icon(VaadinIcon.ENVELOPE), new Text(" " + company.getEmail()));
        Div phoneField = new Div(new Icon(VaadinIcon.PHONE_LANDLINE), new Text(" " + company.getPhoneNumber()));
        HorizontalLayout contactInfoFields = new HorizontalLayout(emailField, phoneField);

        layout.add(
                industry,
                rating,
                descriptionField,
                addressField,
                contactInfoFields
        );

        add(layout);

        Button reportButton = new Button("Report", new Icon(VaadinIcon.MEGAPHONE));
        reportButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        reportButton.getStyle().set("color", "red");

        Button rateButton = new Button("Rate", new Icon(VaadinIcon.STAR)); // TODO implement rating company

        Button contactButton = new Button("Contact", new Icon(VaadinIcon.ENVELOPE)); // TODO implement contacting company

        Button closeButton = new Button("Close");
        closeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        closeButton.addClickListener(buttonClickEvent -> close());
        getFooter().add(reportButton, rateButton, contactButton, closeButton);

        setWidth("30%");
    }

    /**
     * Generates the stars based on the rating
     *
     * @return Stars as icons
     */
    private Div generateStars() {
        Div stars = new Div();
        float rating = 5.0f;  // TODO add company rating
        for (int i = 0; i < rating; i++) {
            stars.add(new Icon(VaadinIcon.STAR));
        }
        for (int i = 0; i < 5 - Float.floatToIntBits(rating); i++) {
            stars.add(new Icon(VaadinIcon.STAR_O));
        }

        return stars;
    }

}
