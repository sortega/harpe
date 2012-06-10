package org.refeed.harpe;

import java.util.Collection;
import java.util.Collections;

public class ValidationSuccess<From, To> implements ValidationResult<From, To> {
    private From original;
    private To clean;

    public ValidationSuccess(From original, To clean) {
        this.original = original;
        this.clean = clean;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public From getOriginalValue() {
        return this.original;
    }

    @Override
    public To getCleanValue() {
        return this.clean;
    }

    @Override
    public Collection<ValidationError> getValidationErrors() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return String.format("valid <%s>", clean.toString());
    }
}
