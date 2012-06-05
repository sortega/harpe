package org.refeed.harpe;

import java.util.ArrayList;
import java.util.List;

public abstract class ValidatorUtil {

    public static <T> Validator<T> all(final Validator<T>... validators) {
        if (validators.length == 1)
            return validators[0];
        return new Validator<T>() {
            @Override
            public List<ValidationError> validate(T value) {
                List<ValidationError> errors = new ArrayList<ValidationError>();
                for (Validator<T> validator: validators) {
                    errors.addAll(validator.validate(value));
                }
                return errors;
            }
        };
    }

    public static <T> Validator<T> chain(final Validator<T>... validators) {
        if (validators.length == 1)
            return validators[0];
        return new Validator<T>() {
            @Override
            public List<ValidationError> validate(T value) {
                List<ValidationError> errors = new ArrayList<ValidationError>();
                for (Validator<T> validator: validators) {
                    errors.addAll(validator.validate(value));
                    if (!errors.isEmpty()) {
                        break;
                    }
                }
                return errors;
            }
        };
    }
}
