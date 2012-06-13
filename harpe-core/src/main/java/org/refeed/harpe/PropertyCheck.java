package org.refeed.harpe;

/**
 * Property checks are intended for validating a feature or sub element of a
 * given value.
 *
 * @param <Value>    Type of the value to validate
 * @param <Property> Type of the property of interest
 */
public abstract class PropertyCheck<Value, Property> extends Check<Value> {
    private final String name;
    private final Check<Property> innerValidation;

    /**
     * Constructor.
     *
     * @param name             Name of the property
     * @param innerValidation  Check the property should pass
     */
    public PropertyCheck(String name, Check<Property> innerValidation) {
        this.name = name;
        this.innerValidation = innerValidation;
    }

    @Override
    protected final void checkRule(Value value) {
        for (ValidationError error: this.innerValidation.run(
                this.getProperty(value)).getValidationErrors()) {
            error("%s %s", this.name, error);
        }
    }

    /**
     * Extracts the property from the value.
     *
     * @param value  Complete value
     * @return       Property of interest
     */
    protected abstract Property getProperty(Value value);
}
