package org.refeed.harpe;

public abstract class FloatingValidation {
    private static class DoubleConversion extends Conversion<String, Double> {
        @Override
        public ValidationResult<String, Double> convert(String value) {
            try {
                return success(Double.parseDouble(value));
            } catch(NumberFormatException ex) {
                return conversionError("is not a valid double");
            }
        }
    }

    private static final DoubleConversion TO_DOUBLE = new DoubleConversion();

    public static Validation<String, Double> floating() {
        return TO_DOUBLE;
    }

    public static <T> Validation<String, T> floating(
            Validation<Double, T> nestedConversion) {
        return ValidationUtil.compose(nestedConversion, floating());
    }

    public static Check<Double> about(final double requiredValue,
                                          final double tolerance) {
        return new Check<Double>() {
            @Override
            public void checkRule(Double value) {
                if (Math.abs(requiredValue - value) > tolerance) {
                    error("must be about %f (margin of %f)", requiredValue,
                          tolerance);
                }
            }
        };
    }

    public static Check<Double> between(final double from,
                                            final double to,
                                            final double tolerance) {
        return new Check<Double>() {
            @Override
            public void checkRule(Double value) {
                if (from - tolerance > value) {
                    error("must be at least %f (margin of %f)", from, tolerance);
                }
                if (to + tolerance < value) {
                    error("must be at most %f (margin of %f)", to, tolerance);
                }
            }
        };
    }
}
