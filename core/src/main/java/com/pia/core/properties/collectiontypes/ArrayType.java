package com.pia.core.properties.collectiontypes;

import com.pia.core.properties.CollectionType;
import com.pia.core.properties.DataType;
import com.pia.core.properties_old.primitives.*;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

public class ArrayType<T extends DataType> extends CollectionType<T> {

    public ArrayType (Field ownField) throws IllegalAccessException {
        super(ownField);
    }

    public ArrayType (Class ownType) throws IllegalAccessException {
        super(ownType);
    }

    @Override
    public String getContentType () {
        return childDataType.getContentType() + "[]";
    }

    @Override
    public Object getValue () {
        return getArray();
    }

    private Object getArray () {
        Object array;
        if (size() <= 0) {
            return null;
        }
        assert ownClass.isArray();
        // differentiate between objects and primitives
        if ((new Object[0]).getClass().isAssignableFrom(ownClass)) {
            //Array is not primitive
            array = Array.newInstance(ownClass.getComponentType(), size());
            for (int i = 0; i < size(); i++) {
                ((Object[])array)[i] = children.get(i).getValue();
            }
        }
        else {
            //Array is primitive
            switch (ownClass.getName()) {
                case "boolean":
                    array = new boolean[size()];
                    for (int i = 0; i < size(); i++) {
                        ((boolean[]) array)[i] = (boolean) (children.get(i).getValue());
                    }
                    break;
                case "byte":
                    array = new byte[size()];
                    for (int i = 0; i < size(); i++) {
                        ((byte[]) array)[i] = (byte) (children.get(i).getValue());
                    }
                    break;
                case "char":
                    array = new char[size()];
                    for (int i = 0; i < size(); i++) {
                        ((char[]) array)[i] = (char) (children.get(i).getValue());
                    }
                    break;
                case "double":
                    array = new double[size()];
                    for (int i = 0; i < size(); i++) {
                        ((double[]) array)[i] = (double) (children.get(i).getValue());
                    }
                    break;
                case "float":
                    array = new float[size()];
                    for (int i = 0; i < size(); i++) {
                        ((float[]) array)[i] = (float) (children.get(i).getValue());
                    }
                    break;
                case "int":
                    array = new int[size()];
                    for (int i = 0; i < size(); i++) {
                        ((int[]) array)[i] = (int) (children.get(i).getValue());
                    }
                    break;
                case "long":
                    array = new long[size()];
                    for (int i = 0; i < size(); i++) {
                        ((long[]) array)[i] = (long) (children.get(i).getValue());
                    }
                    break;
                case "short":
                    array = new short[size()];
                    for (int i = 0; i < size(); i++) {
                        ((short[]) array)[i] = (short) (children.get(i).getValue());
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Field of type " + ownClass + "is not primitive");
            }
        }

        return array;
    }
}
