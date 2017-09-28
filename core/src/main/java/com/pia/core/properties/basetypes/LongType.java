package com.pia.core.properties.basetypes;

import com.pia.core.properties.BaseType;

import java.lang.reflect.Field;

public class LongType extends BaseType<Long> {
    private Long value;

    public LongType (Field ownField) {
        super(ownField);
    }

    @Override
    protected void setDefaultValue () {
        value = 0L;
    }

    public LongType (Class ownClass) {
        super(ownClass);
    }

    @Override
    public String getContentType () {
        return "Long";
    }

    @Override
    public String toString () {
        return value.toString();
    }

    @Override
    public Long getValue () {
        return value;
    }

    @Override
    public void setValue (Long value) {
        this.value = value;
    }

    @Override
    public void parseValue (String value) {
        this.value = Long.parseLong(value);
    }
}
