package org.refeed.harpe;

import org.junit.Test;

import static org.refeed.harpe.FloatingValidation.about;
import static org.refeed.harpe.IntegerValidation.atLeast;
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

    @Test
    public void testParseInteger() {
        Validation<String, Integer> instance = integer(atLeast(0));
        assertValid(instance.run("121"));
        assertHasError(instance.run("one hundred"),
                       "'one hundred' is not a valid integer");
        assertHasError(instance.run("-3"), "must be at least 0");
    }

    @Test
    public void testParseDouble() {
        Validation<String, Double> instance = floating(about(1, 0.001));
        assertValid(instance.run(" 0.9999"));
        assertHasError(instance.run("one"),
                       "'one' is not a valid double");
        assertHasError(instance.run("3.001e10"),
                       "must be about 1,000000 (margin of 0,001000)");
    }
}
