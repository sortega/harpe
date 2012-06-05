package org.refeed.harpe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ValidatorSupport<T> implements Validator<T> {
    private LinkedList<ValidationError> errors;

    @Override
    public final List<ValidationError> validate(T value) {
        errors = new LinkedList<ValidationError>();

        for (Method m: this.getClass().getDeclaredMethods()) {
            if (m.getName().startsWith("check")) {
                if (m.getParameterTypes().length != 1) {
                    throwCannotInvoke(m, null);
                }
                try {
                    m.setAccessible(true);
                    Object result = m.invoke(this, new Object[] {value});
                    if (result == null)
                        continue;
                    if (result instanceof ValidationError) {
                        errors.add((ValidationError) result);
                    } else if (result instanceof Collection) {
                        errors.addAll((Collection) result);
                    }
                } catch (IllegalAccessException ex) {
                    throwCannotInvoke(m, ex);
                } catch (IllegalArgumentException ex) {
                    throwCannotInvoke(m, ex);
                } catch (InvocationTargetException ex) {
                    throwCannotInvoke(m, ex);
                }
            }
        }

        return errors;
    }

    private void throwCannotInvoke(Method m, Throwable ex) throws RuntimeException {
        throw new RuntimeException("Don't know how to invoke " + m.getName(), ex);
    }

    protected void error(String formatString, Object... args) {
        this.errors.add(new ValidationError(formatString, args));
    }

    protected void errors(Collection<ValidationError> errors) {
        this.errors.addAll(errors);
    }
}
