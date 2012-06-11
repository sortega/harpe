package org.refeed.harpe;

import org.junit.Test;

import static org.refeed.harpe.IntegerValidation.*;

public class IntegerValidationTest extends BaseValidatorTest {

    @Test
    public void testParseInteger() {
        Validation<String, Integer> instance = integer(atLeast(0));
        assertValid(instance.run("121"));
        assertHasError(instance.run("one hundred"), "is not a valid integer");
        assertHasError(instance.run("-3"), "must be at least 0");
    }

    @Test
    public void testBetween() {
        Check<Integer> instance = between(0, 10);
        assertValid(instance.run(0));
        assertValid(instance.run(5));
        assertValid(instance.run(10));
        assertHasError(instance.run(-1), "must be at least 0");
        assertHasError(instance.run(11), "must be at most 10");
    }

    @Test
    public void testAtLeast() {
        Check<Integer> instance = atLeast(0);
        assertValid(instance.run(0));
        assertHasError(instance.run(-1), "must be at least 0");
    }

    @Test
    public void testAtMost() {
        Check<Integer> instance = atMost(10);
        assertHasError(instance.run(11), "must be at most 10");
    }
}
