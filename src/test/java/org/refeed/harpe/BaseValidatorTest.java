package org.refeed.harpe;

import org.junit.Ignore;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@Ignore
public class BaseValidatorTest {
    protected static void assertValid(ValidationResult result) {
        assertTrue("should be valid", result.isValid());
        assertTrue("should be empty", result.getValidationErrors().isEmpty());
    }

    protected static<From, To> void assertHasCleanValue(
            ValidationResult<From, To> result, To value) {
        assertValid(result);
        assertThat(result.getCleanValue(), is(value));
    }

    protected static void assertHasError(ValidationResult result, String message) {
        assertFalse("should be invalid", result.isValid());
        Iterable<ValidationError> errors = result.getValidationErrors();
        assertThat(errors, hasItem(new ValidationError(message)));
    }

    protected static void assertHasErrors(ValidationResult result,
                                   String... expectedMessages) {
        assertFalse("should be invalid", result.isValid());
        List<ValidationError> expectedErrors =
                new LinkedList<ValidationError>();
        for (String message : expectedMessages) {
            expectedErrors.add(new ValidationError(message));
        }
        Iterable<ValidationError> errors = result.getValidationErrors();
        assertThat(errors,
                   hasItems(expectedErrors.toArray(new ValidationError[0])));
    }
}
