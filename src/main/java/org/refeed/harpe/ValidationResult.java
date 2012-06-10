package org.refeed.harpe;

import java.util.Collection;

/**
 * The result of a validation.
 *
 * @param <From>
 * @param <To>
 * @see Validation
 */
public interface ValidationResult<From, To> {
    boolean isValid();
    From getOriginalValue();
    To getCleanValue();
    Collection<ValidationError> getValidationErrors();
}
