package org.refeed.harpe.jcommander;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import org.refeed.harpe.Validation;
import org.refeed.harpe.ValidationError;
import org.refeed.harpe.ValidationResult;

/**
 * Helper class for using Harpe validations as JCommander parameter validators.
 *
 * Just define #getValidation method with the appropriate expression and use
 * it as the validateWith argument of @Parameter.
 *
 * Example: limit the range of verbosity levels from 1 to 4.
 *
 * <pre>
 *     public static class VerbosityValidator extends HarpeValidator {
 *         @Override
 *         protected Validation<String, Integer> getValidation() {
 *             return integer(between(1, 4));
 *         }
 *     }
 *
 *     ...
 *
 *     @Parameter(names = { "-log", "-verbose" },
 *                validateWith = VerbosityValidator.class,
 *                description = "Level of verbosity from 1 to 4")
 *     public Integer verbose = 1;
 * </pre>
 */
public abstract class HarpeValidator implements IParameterValidator {

    /**
     * Validates a parameter.
     *
     * @param name Name of the parameter
     * @param value Actual value
     * @throws ParameterException Reporting the first validation error if any
     */
    @Override
    public void validate(String name, String value) throws ParameterException {
        Validation<String, ?> validation = getValidation();
        ValidationResult<String, ?> result = validation.run(value);
        for (ValidationError error : result.getValidationErrors()) {
            throw new ParameterException(name + " " + error.getMessage());
        }
    }

    /**
     * @return Validation to perform
     */
    protected abstract Validation<String, ?> getValidation();
}