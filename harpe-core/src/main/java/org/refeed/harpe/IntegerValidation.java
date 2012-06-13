package org.refeed.harpe;

public abstract class IntegerValidation {
    private static class IntegerConversion extends Conversion<String, Integer> {
        @Override
        public ValidationResult<String, Integer> convert(String value) {
            try {
                return success(Integer.parseInt(value));
            } catch(NumberFormatException ex) {
                return conversionError("is not a valid integer");
            }
        }
    }

    private static final IntegerConversion TO_INTEGER = new IntegerConversion();

    public static Validation<String, Integer> integer() {
        return TO_INTEGER;
    }

    public static <T> Validation<String, T> integer(
            Validation<Integer, T> nestedConversion) {
        return ValidationUtil.compose(nestedConversion, integer());
    }

    public static Check<Integer> between(int from, int to) {
        return new IntegerRange(from, to);
    }

    public static Check<Integer> atLeast(int from) {
        return new IntegerRange(from, null);
    }

    public static Check<Integer> atMost(int to) {
        return new IntegerRange(null, to);
    }

    private static class IntegerRange extends Check<Integer> {
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
