package org.refeed.harpe;

import java.util.Collection;

/**
 * Base class for conversion validators.
 *
 * @param <From>
 * @param <To>
 */
public abstract class Conversion<From, To> implements Validation<From, To> {

    protected ValidationResult<From, To> success(From original, To converted) {
        return new ValidationSuccess<From, To>(original, converted);
    }

    protected ValidationResult<From, To> conversionError(From original,
                                                         String format,
                                                         Object... args) {
        return new ValidationFailure<From, To>(original,
                new ValidationError(format, args));
    }

    protected ValidationResult<From, To> conversionErrors(From original,
            Collection<ValidationError> errors) {
        return new ValidationFailure<From, To>(original, errors);
    }
}
