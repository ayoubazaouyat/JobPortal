package teapot.collat_hbrs.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import teapot.collat_hbrs.backend.Account;
import teapot.collat_hbrs.backend.AccountRepository;
import teapot.collat_hbrs.backend.security.UserService;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Route("passwordreset")
@PageTitle("Password Reset | Coll@HBRS")
@AnonymousAllowed
public class PasswordResetView extends VerticalLayout implements HasUrlParameter<String> {

    private final UserService userService;
    private final AccountRepository accountRepository;
    private final TextField username;
    private final TextField email;
    private final SecureRandom rng;

    public PasswordResetView(UserService userService, AccountRepository accountRepository) {
        this.userService = userService;
        this.accountRepository = accountRepository;
        rng = new SecureRandom();

        var heading = new H1("Forgot your password?");

        username = new TextField("Username");
        username.setWidth("500px");

        email = new TextField("Email");
        email.setWidth("500px");


        Button resetButton = new Button("Reset Password");
        resetButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        resetButton.addClickListener(this::resetPassword);
        resetButton.setWidth("500px");


        var backButton = new Button("Back to login page");
        backButton.addClickListener(buttonClickEvent -> UI.getCurrent().navigate(LoginView.class));
        backButton.setWidth("500px");

        setAlignItems(Alignment.CENTER);


        add(heading, username, email, resetButton, backButton);

    }


    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {

        Location location = event.getLocation();
        QueryParameters queryParameters = location.getQueryParameters();
        Map<String, List<String>> parametersMap = queryParameters.getParameters();
        if (parametersMap.containsKey("token")) {
            List<String> list = parametersMap.get("token");
            if (list.size() != 1) {
                throw new IllegalArgumentException("URL contained multiple or no token");
            } else {
                String token = list.get(0);

            }
        }
    }

    private void resetPassword(ClickEvent<Button> buttonClickEvent) {

        Optional<Account> optionalAccount = accountRepository.findByUsername(username.getValue());
        if (optionalAccount.isEmpty()) {
            //user not found
            return;
        }
        Account account = optionalAccount.get();

        //check if username and email match
        if (account.getUsername().equals(username.getValue()) && account.getEmail().equals(email.getValue())) {
            //Set new password
            char[] allowedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

            char[] password = new char[8];
            for (int i = 0; i < 8; i++) {
                password[i] = allowedCharacters[rng.nextInt(allowedCharacters.length)];
            }

            userService.changePassword(username.getValue(), new String(password));

            Dialog dialog = new Dialog();
            dialog.setHeaderTitle("New Password");
            Text text = new Text(new String(password));
            dialog.add(text);
            Button closeButton = new Button("Ok", e -> dialog.close());
            dialog.getFooter().add(closeButton);

            dialog.open();
        }

    }


}
