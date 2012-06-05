package org.refeed.harpe;

import java.util.regex.Pattern;

public abstract class StringValidation {
    public static Validator<String> nonEmpty() {
        return new ValidatorSupport<String>() {
            public void checkEmptyString(String value) {
                if (value.trim().isEmpty()) {
                    error("cannot be empty");
                }
            }
        };
    }

    public static Validator<String> matches(final String regex,
                                            final String errorDescription) {
        final Pattern pattern = Pattern.compile(regex);
        return new ValidatorSupport<String>() {
            public ValidationError checkRegex(String value) {
                return pattern.matcher(value).matches()
                       ? null
                       : new ValidationError(errorDescription);
            }
        };
    }

    public static Validator<String> length(
            final Validator<Integer> lengthValidator) {
        return new ValidatorSupport<String>() {
            public void checkLength(String value) {
                int length = value.trim().length();
                for (ValidationError valErr : lengthValidator.validate(length)) {
                    error("length %s", valErr);
                }
            }
        };
    }

    public static Validator<String> integer(
            final Validator<Integer> subValidator) {
        return new ValidatorSupport<String>() {
            public void checkIfInteger(String value) {
                int intValue;
                try {
                    intValue = Integer.parseInt(value.trim());
                    errors(subValidator.validate(intValue));
                } catch (NumberFormatException ex) {
                    error("'%s' is not a valid integer", value);
                }
            }
        };
    }

    public static Validator<String> floating(final Validator<Double> subValidator) {
        return new ValidatorSupport<String>() {
            public void checkIfInteger(String value) {
                double doubleValue;
                try {
                    doubleValue = Double.parseDouble(value.trim());
                    errors(subValidator.validate(doubleValue));
                } catch (NumberFormatException ex) {
                    error("'%s' is not a valid double", value);
                }
            }
        };
    }
}
