package org.refeed.harpe;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.refeed.harpe.ValidationUtil.compose;

public abstract class DateValidation {
    private static final SimpleDateFormat DEFAULT_FORMAT = new SimpleDateFormat();

    private static class DateConversion extends Conversion<String, Date> {
        private final DateFormat format;

        public DateConversion(DateFormat format) {
            this.format = format;
        }

        @Override
        public ValidationResult<String, Date> convert(String value) {
            try {
                return success(this.format.parse(value));
            } catch (ParseException e) {
                return conversionError("is not a valid date");
            }
        }
    }

    public static Conversion<String, Date> date(DateFormat format) {
        return new DateConversion(format);
    }

    public static Conversion<String, Date> date() {
        return new DateConversion(DEFAULT_FORMAT);
    }

    public static<T> Validation<String, T> date(
            DateFormat format,
            Validation<Date, T> nestedValidation) {
        return compose(nestedValidation, date(format));
    }

    public static<T> Validation<String, T> date(
            Validation<Date, T> nestedValidation) {
        return date(DEFAULT_FORMAT, nestedValidation);
    }
}
