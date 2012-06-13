package org.refeed.harpe;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidationErrorTest {
    private ValidationError instance;

    @Before
    public void setUp() {
        this.instance = new ValidationError("too much %s%s", "foo", "bar");
    }

    @Test
    public void shouldGetFormattedMessage() {
        assertEquals("too much foobar", this.instance.getMessage());
    }

    @Test
    public void testToString() {
        assertEquals(this.instance.getMessage(), this.instance.toString());
    }

    @Test
    public void testEquality() {
        ValidationError a = new ValidationError("a"),
                b = new ValidationError("b"),
                a2 = new ValidationError("a");
        assertTrue(a.equals(a2));
        assertEquals(a.hashCode(), a2.hashCode());
        assertFalse(a.equals(b));
        assertFalse(a.hashCode() == b.hashCode());
        assertFalse(a.equals(null));
        assertFalse(a.equals("other type"));
    }
}
