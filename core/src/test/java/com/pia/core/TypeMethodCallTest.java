package com.pia.core;

import com.pia.core.property.*;
import com.pia.testing.*;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * DataType methods usually operate on
 * dangerously loose define objects.
 * This test just calls all methods,
 * revealing if any of them throw an
 * exception when they shouldn't.
 */
public class TypeMethodCallTest {

    private Collection<Field> allFields;

    @Before
    public void setup() {
        BaseTypeTestPlugin baseTypePlugin = new BaseTypeTestPlugin();
        ArrayTypeTestPlugin arrayTypePlugin = new ArrayTypeTestPlugin();
        ListTypeTestPlugin listTypePlugin = new ListTypeTestPlugin();
        SimpleDataStorageTestPlugin simplePlugin = new SimpleDataStorageTestPlugin();
        ArrayDataStorageTestPlugin arrayPlugin = new ArrayDataStorageTestPlugin();
        ComplexTypeTestPlugin complexPlugin = new ComplexTypeTestPlugin();

        allFields = new ArrayList<>();
        allFields.addAll(baseTypePlugin.getAnnotatedFields());
        allFields.addAll(arrayTypePlugin.getAnnotatedFields());
        allFields.addAll(listTypePlugin.getAnnotatedFields());
        allFields.addAll(simplePlugin.getAnnotatedFields());
        allFields.addAll(arrayPlugin.getAnnotatedFields());
        allFields.addAll(complexPlugin.getAnnotatedFields());
    }

    @Test
    public void getContentType() throws IllegalAccessException {
        for (DataType d : allDataTypes()) {
            System.out.println(d.getContentType());
        }
    }

    @Test
    public void toStringImpl() throws IllegalAccessException {
        allDataTypes().forEach(type -> System.out.println(type.toString()));
    }

    @Test
    public void getValue() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        for (DataType type : allDataTypes()) {
            try {
                System.out.println(type.getValue());
            }
            catch (IllegalStateException ex) {
                // it's ok if it happens in a test
                // it just means that no constructor could be chosen
                // automatically in a collection of no generic nature.
            }
        }
    }

    @Test
    public void getCollectionChildDataType() throws IllegalAccessException {
        for (DataType dataType : allDataTypes()) {
            if (dataType instanceof CollectionType) {
                ((CollectionType)dataType).getChildDataType();
            }
        }
    }

    @Test
    public void getConstructors() throws IllegalAccessException {
        ConstructableType[] constructableTypes = new ConstructableType[allDataTypes().stream()
                .filter(type -> type instanceof ConstructableType).toArray().length];
        constructableTypes = Arrays.asList(allDataTypes().stream()
                .filter(type -> type instanceof ConstructableType).toArray()).toArray(constructableTypes);
        for (ConstructableType c : constructableTypes) {
            System.out.println(((DataType) c).getContentType() + " - Constructors: ");
            PiaConstructor chosen = c.getChosenConstructor();
            for (PiaConstructor pc : c.getConstructors()) {
                if (chosen != null && chosen.equals(pc)) {
                    System.out.println(" (*)" + pc);
                }
                else {
                    System.out.println("    " + pc);
                }
            }
            System.out.println();
        }
    }

    private Collection<DataType> allDataTypes() throws IllegalAccessException {
        List<DataType> dataTypes = new ArrayList<>();
        for (Field field : allFields) {
            dataTypes.add(DataType.getDataType(field));
        }
        return dataTypes;
    }
}
