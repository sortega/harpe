package org.refeed.harpe;

import java.util.Collection;
import java.util.Collections;

import static java.util.Arrays.asList;

/**
 * Base class for conversion validators.
 *
 * @param <From>  Type of original value
 * @param <To>    Type of converted value
 */
public abstract class Conversion<From, To> implements Validation<From, To> {
    private From originalValue;

    @Override
    public final ValidationResult<From, To> run(From value) {
        this.originalValue = value;
        return convert(value);
    }

    /**
     * Perform conversion.
     *
     * Derived classes are expected to implement this method with the support
     * of protected methods #success, #conversionError and #conversionErrors.
     *
     * @param value  Original value
     * @return       Value returned by #success, #conversionError or
     *               #conversionErrors
     * @see #success(Object)
     * @see #conversionError(String, Object...)
     * @see #conversionErrors(java.util.Collection)
     */
    protected abstract ValidationResult<From, To> convert(From value);

    /**
     * Emits a valid conversion.
     *
     * @param converted  Converted value
     * @return           A successful ValidationResult
     */
    protected ValidationResult<From, To> success(To converted) {
        return new ConversionResult(converted);
    }

    /**
     * Emits an invalid conversion
     *
     * @param format  Error format string
     * @param args    Arguments to interpolate in the format string
     * @return        A failed ValidationResult
     */
    protected ValidationResult<From, To> conversionError(String format,
                                                         Object... args) {
        return new ConversionResult(asList(new ValidationError(format, args)));
    }

    /**
     * Emits an invalid conversion with several errors.
     *
     * @param errors  Collection of errors
     * @return        A failed ValidationResult
     */
    protected ValidationResult<From, To> conversionErrors(
            Collection<ValidationError> errors) {
        return new ConversionResult(errors);
    }

    /**
     * Result representation.
     *
     * Uses its link with the outer class to save typing to the implementers
     * of derived classes of Conversion.
     */
    private class ConversionResult implements ValidationResult<From, To> {
        private final To convertedValue;
        private final From originalValue;
        private final Collection<ValidationError> errors;

        public ConversionResult(To convertedValue) {
            this.originalValue = Conversion.this.originalValue;
            this.convertedValue = convertedValue;
            this.errors = Collections.emptyList();
        }

        public ConversionResult(Collection<ValidationError> errors) {
            this.originalValue = null;
            this.convertedValue = null;
            this.errors = errors;
        }
            @Override
        public boolean isValid() {
            return this.convertedValue != null;
        }

        @Override
        public From getOriginalValue() {
            return this.originalValue;
        }

        @Override
        public To getCleanValue() {
            return this.convertedValue;
        }

        @Override
        public Collection<ValidationError> getValidationErrors() {
            return this.errors;
        }
    }
}
