package teapot.collat_hbrs.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends HorizontalLayout implements BeforeEnterObserver {

    private final LoginForm loginForm = new LoginForm();

    public LoginView() {
        setSizeFull();

        Image img = new Image("images/landin01.jpg", "Description of the image");
        img.setSizeFull();

        VerticalLayout loginLayout = new VerticalLayout();
        loginLayout.addClassName("login-view");
        loginLayout.setSizeFull();
        loginLayout.setAlignItems(Alignment.CENTER);
        loginLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        loginForm.setAction("login");

        loginForm.addForgotPasswordListener(forgotPasswordEvent -> UI.getCurrent().navigate(PasswordResetView.class));

        RouterLink registerLink = new RouterLink("No account? Register here", RegistrationView.class);

        loginLayout.add(new H1("ERROR 418"), loginForm, registerLink);

        add(img, loginLayout);
        expand(img);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            loginForm.setError(true);
        }
    }


}
