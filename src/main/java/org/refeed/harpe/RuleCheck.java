package org.refeed.harpe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Rule checks are validations that do not perform any transformation on the
 * input value.
 *
 * This class is intended to be inherited for simplified rule specification.
 *
 * @param <T>
 */
public abstract class RuleCheck<T> implements Validation<T, T> {
    private List<ValidationError> errors;

    /**
     * Looks for methods whose name starts with the prefix "check" and collects
     * all the validation errors they report or reports success otherwise.
     *
     * Validation errors are reported either:
     * <ul>
     *     <li>Returning a ValidationError or a collection of them</li>
     *     <li>Invoking protected methods #error or #errors</li>
     * </ul>
     *
     * @param value Value to check
     * @return      Validation result
     * @see #error(String, Object...)
     * @see #errors(java.util.Collection)
     */
    @Override
    public final ValidationResult<T, T> run(T value) {
        this.errors = new LinkedList<ValidationError>();

        for (Method m: this.getClass().getDeclaredMethods()) {
            if (m.getName().startsWith("check")) {
                invokeCheck(value, m);
            }
        }

        return this.errors.isEmpty()
                ? new ValidationSuccess<T, T>(value, value)
                : new ValidationFailure<T, T>(value, errors);
    }

    private void invokeCheck(T value, Method m) {
        if (m.getParameterTypes().length != 1) {
            throwCannotInvoke(m, null);
        }
        try {
            m.setAccessible(true);
            Object result = m.invoke(this, value);
            if (result == null) {
                return;
            }
            if (result instanceof ValidationError) {
                this.errors.add((ValidationError) result);
            } else if (result instanceof Collection) {
                this.errors.addAll((Collection<ValidationError>) result);
            }
        } catch (IllegalAccessException ex) {
            throwCannotInvoke(m, ex);
        } catch (IllegalArgumentException ex) {
            throwCannotInvoke(m, ex);
        } catch (InvocationTargetException ex) {
            throwCannotInvoke(m, ex);
        }
    }

    private void throwCannotInvoke(Method m, Throwable ex) {
        throw new RuntimeException("Don't know how to invoke " + m.getName(),
                                   ex);
    }

    protected void error(String formatString, Object... args) {
        this.errors.add(new ValidationError(formatString, args));
    }

    protected void errors(Collection<ValidationError> errors) {
        this.errors.addAll(errors);
    }

    /**
     * Aggregate a number of rule checks into a complex rule check.
     *
     * @param rules Rules to check.
     * @param <T>   Type of values to check
     * @return      The composed rule check
     */
    public static <T> RuleCheck<T> checkAll(
            final RuleCheck<T>... rules) {
        switch(rules.length) {
            case 0:
                return new RuleCheck<T>() {};
            case 1:
                return rules[0];
            default:
                return new RuleCheck<T>() {
                    public void checkSubRules(T value) {
                        for (Validation<T, T> validation : rules) {
                            errors(validation.run(value)
                                    .getValidationErrors());
                        }
                    }
                };
        }
    }
}
