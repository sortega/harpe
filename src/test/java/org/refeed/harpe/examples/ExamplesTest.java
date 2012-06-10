package org.refeed.harpe.examples;

import org.junit.Test;
import org.refeed.harpe.BaseValidatorTest;
import org.refeed.harpe.Validation;

import static org.refeed.harpe.IntegerValidation.between;
import static org.refeed.harpe.StringValidation.*;

public class ExamplesTest extends BaseValidatorTest {

    @Test
    public void simpleValidation() throws Exception {
        Validation<String, String> validation = trimmed(length(between(5, 15)));
        assertHasError(validation.run(" hi!  "), "length must be at least 5");
        assertHasError(validation.run("very, very  long string"),
                                      "length must be at most 15");
        assertHasCleanValue(validation.run("valid value  "), "valid value");
    }
}
