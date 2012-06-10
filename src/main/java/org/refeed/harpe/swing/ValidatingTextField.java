package org.refeed.harpe.swing;

import org.refeed.harpe.Validation;
import org.refeed.harpe.ValidationResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ValidatingTextField<T> extends JTextField {

    private final Validation<String, T> validation;

    public ValidatingTextField(Validation<String, T> validation) {
        this.validation = validation;
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

    private void setValid(boolean valid){
        this.setBackground(valid ? Color.white : Color.red);
    }

    public ValidationResult<String, T> validateContent() {
        ValidationResult<String, T> result = validation.run(this.getText());
        this.setValid(result.isValid());
        return result;
    }
}
