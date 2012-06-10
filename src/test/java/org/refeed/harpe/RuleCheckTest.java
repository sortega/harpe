package org.refeed.harpe;

import org.junit.Test;

import static java.util.Arrays.asList;

public class RuleCheckTest extends BaseValidatorTest {
    public RuleCheck instance;

    @Test
    public void testCheckErrorCall() throws Exception {
        this.instance = new RuleCheck() {
            @Override
            protected void checkRule(Object value) {
                error("invalid " + value);
                error(new ValidationError("erroneous %s", value));
            }
        };
        assertHasErrors(this.instance.run("foo"), "invalid foo", "erroneous foo");
    }

    @Test
    public void testCheckErrorCalls() throws Exception {
        this.instance = new RuleCheck() {
            @Override
            protected void checkRule(Object value) {
                errors(asList(new ValidationError("wrong %s", value),
                        new ValidationError("bad %s", value)));

            }
        };
        assertHasErrors(this.instance.run("bar"), "wrong bar", "bad bar");
    }

    @Test
    public void testCheckAllComposition() throws Exception {
        Validation<String, String> composedCheck = RuleCheck.checkAll(
                new RuleCheck<String>() {
                    @Override
                    public void checkRule(String value) { error("A" + value); }
                },
                new RuleCheck<String>() {
                    @Override
                    public void checkRule(String value) { error("B" + value); }
                }
        );
        assertHasErrors(composedCheck.run("-value"), "A-value", "B-value");
    }

    @Test
    public void testCheckAllOfOneComposition() throws Exception {
        Validation<String, String> composedCheck = RuleCheck.checkAll(
                new RuleCheck<String>() {
                    @Override
                    public void checkRule(String value) { error("A" + value); }
                }
        );
        assertHasError(composedCheck.run("-value"), "A-value");
    }

    @Test
    public void testCheckAllOfNoneComposition() throws Exception {
        Validation<String, String> composedCheck = RuleCheck.checkAll();
        assertValid(composedCheck.run("anything"));
    }

    @Test
    public void testCheckAny() throws Exception {
        this.instance = RuleCheck.any();
        assertValid(this.instance.run("hello"));
        assertValid(this.instance.run(10));
        assertValid(this.instance.run(null));
    }
}
