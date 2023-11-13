package teapot.collat_hbrs.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("passwordreset")
@PageTitle("Password Reset | Coll@HBRS")
@AnonymousAllowed
public class PasswordResetView extends VerticalLayout {

    public PasswordResetView() {
        var heading = new H1("Forgot your password?");
        // Image source: https://knowyourmeme.com/memes/sad-emoji, accessed 12.11.2023
        var sadEmojiImage = new Image("https://i.kym-cdn.com/photos/images/newsfeed/002/496/274/edc.png","Sad emoji");
        var message = new H2("That's unfortunate");
        var backButton = new Button("Back to login page");

        backButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        backButton.addClickListener(buttonClickEvent -> UI.getCurrent().navigate("/login"));

        setAlignItems(Alignment.CENTER);

        add(
                heading,
                sadEmojiImage,
                message,
                backButton
        );
    }

}
