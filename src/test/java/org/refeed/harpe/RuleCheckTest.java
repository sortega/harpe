package org.refeed.harpe;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class RuleCheckTest {

    public RuleCheck instance;
    private Collection<ValidationError> errors;

    @Before
    public void setUp() throws Exception {
        this.instance = new RuleCheck() {
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
        this.errors = instance.run(new Object()).getValidationErrors();
    }

    @Test
    public void testCheckFunctionReturningError() throws Exception {
        assertThat(errors, hasItem(new ValidationError("invalid return")));
    }

    @Test
    public void testCheckErrorCall() throws Exception {
        assertThat(errors, hasItems(new ValidationError("invalid foo"),
                                    new ValidationError("invalid bar")));
    }

    @Test
    public void testCheckErrorCalls() throws Exception {
        assertThat(errors, hasItems(new ValidationError("invalid foos"),
                                    new ValidationError("invalid bars")));
    }

    @Test
    public void testCheckAllComposition() throws Exception {
        Validation<String, String> composedCheck = RuleCheck.checkAll(
                new RuleCheck<String>() {
                    public void checkA(String value) { error("A" + value); }
                },
                new RuleCheck<String>() {
                    public void checkB(String value) { error("B" + value); }
                }
        );

        ValidationResult<String, String> result =
                composedCheck.run("-value");
        assertFalse(result.isValid());
        assertThat(result.getValidationErrors(), hasItems(
                new ValidationError("A-value"),
                new ValidationError("B-value")
        ));
    }
}
