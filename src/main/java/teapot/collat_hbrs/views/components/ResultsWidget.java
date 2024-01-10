package teapot.collat_hbrs.views.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import javax.annotation.Nullable;

public abstract class ResultsWidget extends HorizontalLayout {

    private final Image picture;
    private VerticalLayout info;
    private final Button[] buttons;

    /**
     * Creates a horizontal widget which displays one result
     *
     * @param picture Picture on the left
     * @param buttons Buttons to perform actions
     */
    public ResultsWidget(@Nullable Image picture, @Nullable Button... buttons) {
        this.picture = picture != null ? picture : new Image("images/profile_placeholder.png", "");
        this.info = new VerticalLayout(new Text("N/A"));
        this.buttons = buttons;

        buildWidget();
    }

    /**
     * Sets the style of the widget and adds the content
     */
    public void buildWidget() {
        picture.getStyle().set("margin-right", "0.5rem");
        picture.getStyle().set("height", "100%");
        picture.getStyle().set("border-radius", "var(--lumo-border-radius-m)");

        setHeight(8f, Unit.REM);
        getStyle()
                .set("border-radius", "var(--lumo-border-radius-m)")
                .set("background", "var(--lumo-contrast-10pct)")
                .set("padding", "0.5rem");
        setAlignItems(Alignment.CENTER);
        setWidthFull();

        add(picture, info);
        add(buttons);
    }

    /**
     * Sets the information
     *
     * @param info Information
     */
    void setInfo(VerticalLayout info) {
        replace(this.info, info);
        this.info = info;
    }

    /**
     * Adds buttons to the widget
     *
     * @param buttons Buttons
     */
    void addButtons(Button... buttons) {
        add(buttons);
    }

}
