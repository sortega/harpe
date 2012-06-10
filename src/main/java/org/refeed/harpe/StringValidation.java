package org.refeed.harpe;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.refeed.harpe.ValidationUtil.compose;

public abstract class StringValidation {

    private static class TrimConversion extends Conversion<String, String> {
        @Override
        public ValidationResult<String, String> run(String value) {
            return success(value, value.trim());
        }
    }
    private static final TrimConversion TRIM = new TrimConversion();

    /**
     * Removes spaces at the beginning and end of an string.
     * @return Conversion
     */
    public static Conversion<String, String> trimmed() {
        return TRIM;
    }

    /**
     * Removes spaces at the beginning and end of an string and
     * validates the trimmed string.
     *
     * @param nestedValidation  Validation on the trimmed string
     * @param <T>               Type of the clean value
     * @return Conversion
     */
    public static <T> Validation<String, T> trimmed(
            Validation<String, T> nestedValidation) {
        return compose(nestedValidation, trimmed());
    }

    /**
     * Checks if an string is non empty.
     * @return Validation
     * @see #trimmed()
     */
    public static Validation<String, String> nonEmpty() {
        return new RuleCheck<String>() {
            public void checkEmptyString(String value) {
                if (value.isEmpty()) {
                    error("cannot be empty");
                }
            }
        };
    }

    /**
     * Checks if an string conforms to a regular expression.
     *
     * @param regex             Pattern to match
     * @param errorDescription  Custom error
     * @return                  Validation
     */
    public static RuleCheck<String> matches(final Pattern regex,
                                            final String errorDescription) {
        return new RuleCheck<String>() {
            public ValidationError checkRegex(String value) {
                return regex.matcher(value).matches()
                        ? null
                        : new ValidationError(errorDescription);
            }
        };
    }

    /**
     * Checks if an string conforms to a regular expression.
     *
     * @param regex              Regex to be fed to Pattern.compile
     * @param errorDescription   Custom error
     * @return                   Validation
     */
    public static RuleCheck<String> matches(final String regex,
                                            final String errorDescription) {
        return matches(Pattern.compile(regex), errorDescription);
    }


    private static class IntegerConversion extends Conversion<String, Integer> {
        @Override
        public ValidationResult<String, Integer> run(String value) {
            try {
                return success(value, Integer.parseInt(value));
            } catch(NumberFormatException ex) {
                return conversionError(value, "'%s' is not a valid integer",
                                       value);
            }
        }
    }

    private static class DoubleConversion extends Conversion<String, Double> {
        @Override
        public ValidationResult<String, Double> run(String value) {
            try {
                return success(value, Double.parseDouble(value));
            } catch(NumberFormatException ex) {
                return conversionError(value, "'%s' is not a valid double",
                                       value);
            }
        }
    }


    public static<T> Conversion<String, T> length(
            final Validation<Integer, T> lengthValidation) {

        /* TODO: extract this pattern as PropertyCheck, a validation on a
           property of a raw object */
        return new Conversion<String, T>() {
            @Override
            public ValidationResult<String, T> run(String value) {
                ValidationResult<Integer, T> innerResult =
                        lengthValidation.run(value.length());
                if (innerResult.isValid()) {
                    return success(value, innerResult.getCleanValue());
                }
                List<ValidationError> errors = new ArrayList<ValidationError>();
                for (ValidationError innerError: innerResult.getValidationErrors()) {
                    errors.add(new ValidationError("length %s", innerError));
                }
                return conversionErrors(value, errors);
            }
        };
    }

    public static Validation<String, Integer> integer() {
        return new IntegerConversion();
    }

    public static <T> Validation<String, T> integer(
            Validation<Integer, T> nestedConversion) {
        return compose(nestedConversion, integer());
    }

    public static Validation<String, Double> floating() {
        return new DoubleConversion();
    }

    public static <T> Validation<String, T> floating(
            Validation<Double, T> nestedConversion) {
        return compose(nestedConversion, floating());
    }
}
