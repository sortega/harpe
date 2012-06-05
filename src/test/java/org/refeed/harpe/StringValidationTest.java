package org.refeed.harpe;

import org.junit.Test;
import static org.refeed.harpe.StringValidation.*;
import static org.refeed.harpe.FloatingValidation.about;
import static org.refeed.harpe.IntegerValidation.atLeast;
import static org.refeed.harpe.IntegerValidation.atMost;

public class StringValidationTest extends BaseValidatorTest<String> {
    @Test
    public void testNonEmpty() {
        instance = nonEmpty();
        assertNoErrors(instance.validate("hello"));
        assertHasError(instance.validate("  "), "cannot be empty");
    }

    @Test
    public void testMatches() {
        instance = matches("(ab)+", "must be ab-able");
        assertNoErrors(instance.validate("abab"));
        assertHasError(instance.validate("abba"), "must be ab-able");
    }

    @Test
    public void testLength() {
        instance = length(atMost(15));
        assertNoErrors(instance.validate("not so long"));
        assertHasError(instance.validate("very, very, long"),
                       "length must be at most 15");
    }

    @Test
    public void testParseableInteger() {
        instance = integer(atLeast(0));
        assertNoErrors(instance.validate(" 121"));
        assertHasError(instance.validate("one hundred"),
                       "'one hundred' is not a valid integer");
        assertHasError(instance.validate("-3"), "must be at least 0");
    }

    @Test
    public void testParseableDouble() {
        instance = floating(about(1, 0.001));
        assertNoErrors(instance.validate(" 0.9999"));
        assertHasError(instance.validate("one"),
                       "'one' is not a valid double");
        assertHasError(instance.validate("3.001e10"),
                       "must be about 1,000000 (margin of 0,001000)");
    }
}
