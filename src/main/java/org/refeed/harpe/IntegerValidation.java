package org.refeed.harpe;

public abstract class IntegerValidation {
    public static Validator<Integer> between(int from, int to) {
        return new IntegerRange(from, to);
    }

    public static Validator<Integer> atLeast(int from) {
        return new IntegerRange(from, null);
    }

    public static Validator<Integer> atMost(int to) {
        return new IntegerRange(null, to);
    }

    private static class IntegerRange extends ValidatorSupport<Integer> {
        private final Integer from;
        private final Integer to;

        public IntegerRange(Integer from, Integer to) {
            this.from = from;
            this.to = to;
        }

        public void checkLowerBound(int value) {
            if (this.from == null) {
                return;
            }
            if (this.from > value) {
                error("must be at least %d", this.from);
            }
        }

        public void checkUpperBound(int value) {
            if (this.to == null) {
                return;
            }
            if (this.to < value) {
                error("must be at most %d", this.to);
            }
        }
    }
}
