package org.refeed.harpe;

public abstract class ValidationUtil {

    public static<A,B,C> Validation<A,C> compose(
            final Validation<B,C> outerValidation,
            final Validation<A,B> innerValidation) {
        return new Validation<A, C>() {
            @Override
            public ValidationResult<A, C> run(A original) {
                ValidationResult<A, B> innerResult =
                        innerValidation.run(original);
                if (!innerResult.isValid()) {
                    return new ValidationFailure<A, C>(original,
                            innerResult.getValidationErrors());
                }
                ValidationResult<B, C> outerResult =
                        outerValidation.run(innerResult.getCleanValue());
                return outerResult.isValid()
                        ? new ValidationSuccess<A, C>(original, outerResult.getCleanValue())
                        : new ValidationFailure<A, C>(original, outerResult.getValidationErrors());
            }
        };
    }
}
