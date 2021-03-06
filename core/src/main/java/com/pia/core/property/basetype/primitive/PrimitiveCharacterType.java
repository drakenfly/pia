package com.pia.core.property.basetype.primitive;

import com.pia.core.property.basetype.CharacterType;

import java.lang.reflect.Field;

public class PrimitiveCharacterType extends CharacterType {

    public PrimitiveCharacterType (Field ownField) {
        super(ownField);
    }

    public PrimitiveCharacterType (Class ownClass) {
        super(ownClass);
    }

    @Override
    protected void writeFieldToObject (Object object) throws IllegalAccessException {
        ownField.setChar(object, getValue());
    }

    @Override
    public void setValue (Character value) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot set null as value in a primitive datatype");
        }
        super.setValue(value);
    }
}
