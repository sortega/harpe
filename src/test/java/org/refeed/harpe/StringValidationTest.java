package org.refeed.harpe;

import org.junit.Test;

import static org.refeed.harpe.IntegerValidation.atMost;
import static org.refeed.harpe.StringValidation.*;

public class StringValidationTest extends BaseValidatorTest {
    @Test
    public void testNonEmpty() {
        Validation<String, String> instance = trimmed(nonEmpty());
        assertValid(instance.run("hello "));
        assertHasError(instance.run("  "), "cannot be empty");
    }

    @Test
    public void testMatches() {
        Validation<String, String> instance = matches("(ab)+", "must be ab-able");
        assertValid(instance.run("abab"));
        assertHasError(instance.run("abba"), "must be ab-able");
    }

    @Test
    public void testLength() {
        RuleCheck<String> instance = length(atMost(15));
        assertValid(instance.run("not so long"));
        assertHasError(instance.run("very, very, long"),
                       "length must be at most 15");
    }
}
