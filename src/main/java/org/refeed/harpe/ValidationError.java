package org.refeed.harpe;

public class ValidationError {
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
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ValidationError other = (ValidationError) obj;
        if ((this.message == null) ? (other.message != null)
                : !this.message.equals(other.message)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.message != null ? this.message.hashCode() : 0);
        return hash;
    }

}
