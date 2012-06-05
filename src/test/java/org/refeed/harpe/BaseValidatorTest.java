package org.refeed.harpe;

import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;
import org.refeed.harpe.ValidationError;
import org.refeed.harpe.Validator;

@Ignore
public class BaseValidatorTest<T> {
    protected Validator<T> instance;

    protected void assertNoErrors(List<ValidationError> errors) {
        assertThat(errors, hasSize(0));
    }

    protected void assertHasError(List<ValidationError> errors, String message) {
        assertThat(errors, hasItem(new ValidationError(message)));
    }
}
