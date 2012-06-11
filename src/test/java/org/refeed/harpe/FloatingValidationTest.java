package org.refeed.harpe;

import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static org.refeed.harpe.FloatingValidation.*;

public class FloatingValidationTest extends BaseValidatorTest {
    private final static double TOLERANCE = 0.001;

    @Before
    public void setUp() {
        Locale.setDefault(Locale.ENGLISH);
    }

    @Test
    public void testParseDouble() {
        Validation<String, Double> instance = floating(about(1, 0.001));
        assertValid(instance.run(" 0.9999"));
        assertHasError(instance.run("one"), "is not a valid double");
        assertHasError(instance.run("3.001e10"),
                "must be about 1.000000 (margin of 0.001000)");
    }

    @Test
    public void testBetween() {
        RuleCheck<Double> instance = between(0, 10, TOLERANCE);
        assertValid(instance.run(0d));
        assertValid(instance.run(5d));
        assertValid(instance.run(10d));
        assertHasError(instance.run(-1d),
                "must be at least 0.000000 (margin of 0.001000)");
        assertHasError(instance.run(11d),
                "must be at most 10.000000 (margin of 0.001000)");
    }

    @Test
    public void testAbout() {
        RuleCheck<Double> instance = about(5, TOLERANCE);
        assertValid(instance.run(5d));
        assertValid(instance.run(5.0001d));
        assertValid(instance.run(4.9999d));
        assertHasError(instance.run(4d),
                "must be about 5.000000 (margin of 0.001000)");
        assertHasError(instance.run(6d),
                "must be about 5.000000 (margin of 0.001000)");
    }
}