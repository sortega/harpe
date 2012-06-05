package org.refeed.harpe;

import static java.util.Arrays.asList;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.refeed.harpe.ValidationError;
import org.refeed.harpe.ValidatorSupport;

public class ValidatorSupportTest {

    public ValidatorSupport instance;
    private List<ValidationError> errors;

    @Before
    public void setUp() {
        this.instance = new ValidatorSupport() {
            public ValidationError checkReturnFoo(Object o) {
                return new ValidationError("invalid return");
            }

            public void checkCallError(Object o) {
                error("invalid foo");
                error("invalid bar");
            }

            public void checkCallErrors(Object o) {
                errors(asList(new ValidationError("invalid foos"),
                              new ValidationError("invalid bars")));
            }
        };
    }

    @Test
    public void testCheckFunctionReturningError() {
        errors = instance.validate(new Object());
        assertThat(errors, hasItem(new ValidationError("invalid return")));
    }

    @Test
    public void testCheckErrorCall() {
        errors = instance.validate(new Object());
        assertThat(errors, hasItems(new ValidationError("invalid foo"),
                                    new ValidationError("invalid bar")));
    }

    @Test
    public void testCheckErrorCalls() {
        errors = instance.validate(new Object());
        assertThat(errors, hasItems(new ValidationError("invalid foos"),
                                    new ValidationError("invalid bars")));
    }
}
