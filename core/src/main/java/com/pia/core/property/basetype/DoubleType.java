package com.pia.core.property.basetype;

import com.pia.core.property.BaseType;

import java.lang.reflect.Field;

public class DoubleType extends BaseType<Double> {
    private Double value;

    public DoubleType (Field ownField) {
        super(ownField);
    }

    @Override
    protected void setDefaultValue () {
        value = 0.;
    }

    public DoubleType (Class ownClass) {
        super(ownClass);
    }

    @Override
    public String getContentType () {
        return "Double";
    }

    @Override
    public String toString () {
        return value == null ? "null" : value.toString();
    }

    @Override
    public Double getValue () {
        return value;
    }

    @Override
    public void setValue (Double value) {
        this.value = value;
    }

    @Override
    public void parseValue (String value) {
        try {
            this.value = Double.parseDouble(value);
        }
        catch (NumberFormatException ex) {
            value = value.replace(",", ".");
            this.value = Double.parseDouble(value);
        }
    }
}
