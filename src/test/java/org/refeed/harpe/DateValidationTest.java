package org.refeed.harpe;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.refeed.harpe.DateValidation.date;

public class DateValidationTest extends BaseValidatorTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void shouldParseDate() throws Exception {
        Validation<String, Date> instance = date();
        ValidationResult<String, Date> validResult =
                instance.run("11/06/12 18:25");
        assertValid(validResult);
        assertEquals(new Date(1339431900000l), validResult.getCleanValue());
    }

    @Test
    public void shouldNotParseOtherText() throws Exception {
        Validation<String, Date> instance = date();
        assertHasError(instance.run("2012 is a great year"),
                       "is not a valid date");
    }

    @Test
    public void shouldParseCustomFormatDate() throws Exception {
        Validation<String, Date> instance = date(
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
        ValidationResult<String, Date> validResult =
                instance.run("2001-07-04T12:08:56.235-0700");
        assertValid(validResult);
        assertEquals(new Date(994273736235l), validResult.getCleanValue());
    }

    @Test
    public void shouldChainOtherValidations() throws Exception {
        Validation<String, Date> instance = date(new Conversion<Date, Date>() {
            @Override
            protected ValidationResult<Date, Date> convert(Date value) {
                return success(new Date(value.getTime() + 24 * 3600 * 1000));
            }
        });

        Date result = instance.run("11/06/12 18:25").getCleanValue();
        assertEquals("12/06/12 18:25", new SimpleDateFormat().format(result));
    }
}
