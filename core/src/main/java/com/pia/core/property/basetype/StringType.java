package com.pia.core.property.basetype;

import com.pia.core.annotation.Property;
import com.pia.core.internal.FieldHelper;
import com.pia.core.property.BaseType;
import com.pia.core.property.NullableType;

import java.lang.reflect.Field;

public class StringType extends BaseType<String> implements NullableType {
    private boolean valueIsNull;
    private final boolean required;
    private String value;

    public StringType (Field ownField) {
        super(ownField);
        Property property = FieldHelper.getProperty(ownField);
        if (property != null && property.required()) {
            required = property.required();
        }
        else {
            required = false;
        }
    }

    @Override
    protected void setDefaultValue () {
        value = null;
    }

    public StringType (Class ownClass) {
        super(ownClass);
        required = false;
    }

    @Override
    public String getContentType () {
        return "String";
    }

    @Override
    public String toString () {
        return value;
    }

    @Override
    public String getValue () {
        return value;
    }

    @Override
    public void setValue (String value) {
        this.value = value;
    }

    @Override
    public void parseValue (String value) {
        setValue(value);
    }

    @Override
    public boolean getValueIsNull () {
        return valueIsNull;
    }

    @Override
    public void setValueIsNull (boolean valueIsNull) {
        if (isRequired() && valueIsNull) {
            throw new IllegalArgumentException("Cannot set a field to null, which is required.");
        }
        this.valueIsNull = valueIsNull;
    }

    @Override
    public boolean isRequired () {
        return required;
    }
}
