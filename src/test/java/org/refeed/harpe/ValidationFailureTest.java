package org.refeed.harpe;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.*;

public class ValidationFailureTest {
    private ValidationFailure<String, Integer> instance;

    @Before
    public void setUp() throws Exception {
        this.instance = new ValidationFailure<String, Integer>("ñ12",
                new ValidationError("Invalid integer"));
    }

    @Test
    public void shouldBeInvalid() throws Exception {
        assertFalse(this.instance.isValid());
    }

    @Test
    public void shouldReturnOriginalValue() throws Exception {
        assertEquals("ñ12", this.instance.getOriginalValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotGetCleanValue() throws Exception {
        this.instance.getCleanValue();
    }

    @Test
    public void shouldReturnErrors() throws Exception {
        assertThat(this.instance.getValidationErrors(),
                   hasItems(new ValidationError("Invalid integer")));
    }
}
