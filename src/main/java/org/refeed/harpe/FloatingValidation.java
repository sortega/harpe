package org.refeed.harpe;

public abstract class FloatingValidation {
    public static Validator<Double> about(final double requiredValue,
                                          final double tolerance) {
        return new ValidatorSupport<Double>() {
            public void checkValue(double value) {
                if (Math.abs(requiredValue - value) > tolerance) {
                    error("must be about %f (margin of %f)", requiredValue,
                          tolerance);
                }
            }
        };
    }

    public static Validator<Double> between(final double from,
                                            final double to,
                                            final double tolerance) {
        return new ValidatorSupport<Double>() {
            public void checkLowerBound(double value) {
                if (from - tolerance > value) {
                    error("must be at least %f (margin of %f)", from, tolerance);
                }
            }

            public void checkUpperBound(double value) {
                if (to + tolerance < value) {
                    error("must be at most %f (margin of %f)", to, tolerance);
                }
            }
        };
    }
}
