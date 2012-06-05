package validation.swing;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JTextField;

import org.refeed.harpe.ValidationError;
import org.refeed.harpe.Validator;

public class ValidatingTextField extends JTextField {

    private List<Validator<String>> validators =
            new ArrayList<Validator<String>>();

    public ValidatingTextField() {
        this.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                validateContent();
            }
        });
    }

    public void addValidators(Validator<String>... validators){
        this.validators.addAll(Arrays.asList(validators));
    }

    private void mark(boolean fail){
        this.setBackground(fail ? Color.red : Color.white);
    }

    public List<ValidationError> validateContent() {
        List<ValidationError> errors = new ArrayList<ValidationError>();
        for (Validator<String> validator: validators) {
            errors.addAll(validator.validate(this.getText()));
        }
        this.mark(!errors.isEmpty());
        return errors;
    }
}
