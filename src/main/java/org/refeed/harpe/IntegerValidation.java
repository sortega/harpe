package org.refeed.harpe;

import static org.refeed.harpe.ValidationUtil.compose;

public abstract class IntegerValidation {
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

    public static RuleCheck<Integer> between(int from, int to) {
        return new IntegerRange(from, to);
    }

    public static RuleCheck<Integer> atLeast(int from) {
        return new IntegerRange(from, null);
    }

    public static RuleCheck<Integer> atMost(int to) {
        return new IntegerRange(null, to);
    }

    private static class IntegerRange extends RuleCheck<Integer> {
        private final Integer from;
        private final Integer to;

        public IntegerRange(Integer from, Integer to) {
            this.from = from;
            this.to = to;
        }

        public void checkRule(Integer value) {
            if (this.from != null && this.from > value) {
                error("must be at least %d", this.from);
            }

            if (this.to != null && this.to < value) {
                error("must be at most %d", this.to);
            }
        }
    }
}
