package org.refeed.harpe;

public class ValidationError {
    private static final int HASH_SEED = 3;
    private static final int HASH_FACTOR = 97;
    private final String message;

    public ValidationError(String formatString, Object... args) {
        this.message = String.format(formatString, args);
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ValidationError other = (ValidationError) obj;
        return this.message.equals(other.message);
    }

    @Override
    public int hashCode() {
        int hash = HASH_SEED;
        hash = HASH_FACTOR * hash + this.message.hashCode();
        return hash;
    }

}
