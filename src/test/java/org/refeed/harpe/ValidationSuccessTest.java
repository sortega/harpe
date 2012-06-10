package org.refeed.harpe;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ValidationSuccessTest {
    private ValidationSuccess<String, Integer> instance;

    @Before
    public void setUp() throws Exception {
        this.instance = new ValidationSuccess<String, Integer>(" 12", 12);
    }

    @Test
    public void shouldBeValid() throws Exception {
        assertTrue(this.instance.isValid());
    }

    @Test
    public void shouldGetOriginalAndCleanValues() throws Exception {
        assertEquals(" 12", this.instance.getOriginalValue());
        assertEquals(12, this.instance.getCleanValue(), 0);
    }

    @Test
    public void shouldHaveNoErrors() throws Exception {
        assertTrue(this.instance.getValidationErrors().isEmpty());
    }
}
