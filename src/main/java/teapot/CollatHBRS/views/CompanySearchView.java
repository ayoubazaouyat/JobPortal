package teapot.CollatHBRS.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "searchcompany", layout = MainLayout.class)
@PermitAll
public class CompanySearchView extends VerticalLayout {

    /**
     * Constructor
     */
    public CompanySearchView() {
        add(new H1("Company Search"));
        initSearch();
        add(new Hr());

        add(new Paragraph("- - - Results go here - - -"));
    }

    /**
     * Creates the search fields
     */
    private void initSearch() {
        var searchLayout = new HorizontalLayout();
        searchLayout.setAlignItems(Alignment.BASELINE);

        var companyField = new TextField("Name");
        var locationField = new TextField("Location");
        var categorySelector = new ComboBox<>("Category");
        categorySelector.setClearButtonVisible(true);
        categorySelector.setItems(new String[]{"Software Developer", "Web Designer"}); // Demo values

        var untilDatePicker = new DatePicker("Until");
        var fullTimeCheckbox = new Checkbox("Full Time");

        var searchButton = new Button("Search");
        searchButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        searchButton.setIcon(new Icon(VaadinIcon.SEARCH));

        searchLayout.add(
                companyField,
                locationField,
                categorySelector,
                untilDatePicker,
                fullTimeCheckbox,
                searchButton
        );

        add(searchLayout);
    }

}
