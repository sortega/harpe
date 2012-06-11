package org.refeed.harpe;

import java.util.regex.Pattern;

import static org.refeed.harpe.ValidationUtil.compose;

public abstract class StringValidation {

    private static class TrimConversion extends Conversion<String, String> {
        @Override
        public ValidationResult<String, String> convert(String value) {
            return success(value.trim());
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
        return new Check<String>() {
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
    public static Check<String> matches(final Pattern regex,
                                            final String errorDescription) {
        return new Check<String>() {
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
    public static Check<String> matches(final String regex,
                                            final String errorDescription) {
        return matches(Pattern.compile(regex), errorDescription);
    }

    public static Check<String> length(
            final Check<Integer> lengthValidation) {
        return new PropertyCheck<String, Integer>("length", lengthValidation) {
            @Override
            protected Integer getProperty(String value) {
                return value.length();
            }
        };
    }

}
