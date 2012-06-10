package org.refeed.harpe;

import org.junit.Ignore;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.*;

@Ignore
public class BaseValidatorTest {
    protected void assertValid(ValidationResult result) {
        assertTrue("should be valid", result.isValid());
        assertTrue("should be empty", result.getValidationErrors().isEmpty());
    }

    protected void assertHasError(ValidationResult result, String message) {
        assertFalse("should be invalid", result.isValid());
        Iterable<ValidationError> errors = result.getValidationErrors();
        assertThat(errors, hasItem(new ValidationError(message)));
    }
}
