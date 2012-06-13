package org.refeed.harpe;

/**
 * Represents a validation process that takes a raw value of type From and
 * ends with a clean or valid value of type To or a list of validation errors.
 *
 * @param <From>  Type of the raw input
 * @param <To>    Type of the clean output
 */
public interface Validation<From, To> {
    ValidationResult<From, To> run(From value);
}
