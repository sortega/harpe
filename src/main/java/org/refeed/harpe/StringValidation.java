package org.refeed.harpe;

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
            public void checkRule(String value) {
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
            public void checkRule(String value) {
                if(!regex.matcher(value).matches()) {
                    error(errorDescription);
                }
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
    private static final IntegerConversion TO_INTEGER = new IntegerConversion();

    public static Validation<String, Integer> integer() {
        return TO_INTEGER;
    }

    public static <T> Validation<String, T> integer(
            Validation<Integer, T> nestedConversion) {
        return compose(nestedConversion, integer());
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

    private static final DoubleConversion TO_DOUBLE = new DoubleConversion();

    public static Validation<String, Double> floating() {
        return TO_DOUBLE;
    }

    public static <T> Validation<String, T> floating(
            Validation<Double, T> nestedConversion) {
        return compose(nestedConversion, floating());
    }

    public static RuleCheck<String> length(
            final RuleCheck<Integer> lengthValidation) {

        /* TODO: extract this pattern as PropertyCheck, a validation on a
           property of a raw object */
        return new RuleCheck<String>() {
            @Override
            protected void checkRule(String value) {
                ValidationResult<Integer, Integer> innerResult =
                        lengthValidation.run(value.length());
                for (ValidationError innerError: innerResult.getValidationErrors()) {
                    error("length %s", innerError);
                }
            }
        };
    }

}
