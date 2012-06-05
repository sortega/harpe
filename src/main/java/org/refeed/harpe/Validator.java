package org.refeed.harpe;

import java.util.List;

public interface Validator<T> {
    List<ValidationError> validate(T value);
}
