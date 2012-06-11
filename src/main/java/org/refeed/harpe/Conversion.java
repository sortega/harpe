package org.refeed.harpe;

import java.util.Collection;
import java.util.Collections;

import static java.util.Arrays.asList;

/**
 * Base class for conversion validators.
 *
 * @param <From>
 * @param <To>
 */
public abstract class Conversion<From, To> implements Validation<From, To> {
    private From originalValue;

    @Override
    public final ValidationResult<From, To> run(From value) {
        this.originalValue = value;
        return convert(value);
    }

    protected abstract ValidationResult<From, To> convert(From value);

    protected ValidationResult<From, To> success(To converted) {
        return new ConversionResult(converted);
    }

    protected ValidationResult<From, To> conversionError(String format,
                                                         Object... args) {
        return new ConversionResult(asList(new ValidationError(format, args)));
    }

    protected ValidationResult<From, To> conversionErrors(
            Collection<ValidationError> errors) {
        return new ConversionResult(errors);
    }

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
