package org.refeed.harpe;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class ValidationFailure<From, To> implements ValidationResult<From, To> {
    private From original;
    private Collection<ValidationError> errors;

    public ValidationFailure(From original, Collection<ValidationError> errors) {
        this.original = original;
        this.errors = Collections.unmodifiableCollection(errors);
    }

    public ValidationFailure(From original, ValidationError... errors) {
        this(original, Arrays.asList(errors));
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public From getOriginalValue() {
        return this.original;
    }

    @Override
    public To getCleanValue() {
        throw new IllegalArgumentException(
                "ValidationResult error, there is no clean value");
    }

    @Override
    public Collection<ValidationError> getValidationErrors() {
        return this.errors;
    }

    @Override
    public String toString() {
        return String.format("invalid <%s>: %s", this.original,
                             StringUtils.join(this.errors, "> <"));
    }
}
