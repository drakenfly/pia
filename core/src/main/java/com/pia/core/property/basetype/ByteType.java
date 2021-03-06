package com.pia.core.property.basetype;

import com.pia.core.property.BaseType;

import java.lang.reflect.Field;

public class ByteType extends BaseType<Byte> {
    private Byte value;

    public ByteType (Field ownField) {
        super(ownField);
    }

    @Override
    protected void setDefaultValue () {
        value = 0;
    }

    public ByteType (Class ownClass) {
        super(ownClass);
    }

    @Override
    public String getContentType () {
        return "Byte";
    }

    @Override
    public String toString () {
        return value == null ? "null" : value.toString();
    }

    @Override
    public Byte getValue () {
        return value;
    }

    @Override
    public void setValue (Byte value) {
        this.value = value;
    }

    @Override
    public void parseValue (String value) {
        this.value = Byte.parseByte(value);
    }
}
