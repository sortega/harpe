package org.refeed.harpe;

public abstract class FloatingValidation {
    public static RuleCheck<Double> about(final double requiredValue,
                                          final double tolerance) {
        return new RuleCheck<Double>() {
            @Override
            public void checkRule(Double value) {
                if (Math.abs(requiredValue - value) > tolerance) {
                    error("must be about %f (margin of %f)", requiredValue,
                          tolerance);
                }
            }
        };
    }

    public static RuleCheck<Double> between(final double from,
                                            final double to,
                                            final double tolerance) {
        return new RuleCheck<Double>() {
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
