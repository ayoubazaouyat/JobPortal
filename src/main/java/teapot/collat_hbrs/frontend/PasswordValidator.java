package teapot.collat_hbrs.frontend;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;

public class PasswordValidator implements Validator<String> {
    private static final int MINPASSWORDLENGTH = 4;


    @Override
    public ValidationResult apply(String password, ValueContext valueContext) {
        if(password.length() < MINPASSWORDLENGTH) return ValidationResult.error("Password must be at least " + MINPASSWORDLENGTH + " characters long");
        return ValidationResult.ok();
    }
}
