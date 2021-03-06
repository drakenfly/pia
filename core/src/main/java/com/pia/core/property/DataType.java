package com.pia.core.property;

import com.pia.core.annotation.Property;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

/**
 * A DataType object is used to handle dataflow
 * from and to a class' field.
 * It is important to use appropriate implementations
 * for any kind of field, since variable access to
 * unknown objects at runtime must be safe and should
 * guarantee some consistency. Also using this DataType
 * structure helps decorating different fields with
 * display techniques, depending on the chosen frontend.
 */
public abstract class DataType {
    /**
     * This field stores a reference to the corresponding
     * field that the DataType originated from.
     * This variable also always stores additional
     * information, for example about the generic type
     * of a field or it's annotations.
     */
    protected final Field ownField;

    /**
     * Mainly used when ownField is null.
     * Is needed when the data does not sit on a field,
     * but in a collection.
     */
    protected final Class<?> ownClass;

    /**
     * Standard constructor storing the own field as a reference.
     *
     * @param ownField Stores a reference to the corresponding
     *                 field that the DataType originated from.
     */
    public DataType (Field ownField) {
        this.ownField = ownField;
        this.ownClass = ownField.getType();
    }

    public DataType (Class<?> ownClass) {
        this.ownField = null;
        this.ownClass = ownClass;
    }

    public Field getField () {
        return ownField;
    }

    public abstract String getContentType () throws IllegalAccessException;

    public final void readValueFromObject (Object object) {
        boolean originalAccessibility = ownField.isAccessible();
        ownField.setAccessible(true);
        readValueFromObject(object);
        ownField.setAccessible(originalAccessibility);
    }

    protected abstract void readFieldFromObject (Object object) throws IllegalAccessException;

    public final void writeValueBackToObject (Object object) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        boolean originalAccessibility = ownField.isAccessible();
        ownField.setAccessible(true);
        writeFieldToObject(object);
        ownField.setAccessible(originalAccessibility);
    }

    protected void writeFieldToObject (Object object) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        ownField.set(object, getValue());
    }

    public abstract Object getValue () throws IllegalAccessException, InstantiationException, InvocationTargetException;

    /**
     * By parsing the field's class and type information the
     * correct DataType implementation is chosen and instantiated.
     * This method should be called almost every time when
     * a DataType object is needed externally.
     */
    public static @NotNull
    DataType getDataType (Field field) throws IllegalAccessException {
        /*
         * Basetypes are primitives, their primitive object wrappers and Strings
         */
        if (BaseType.isBaseType(field.getType())) {
            return BaseType.getBaseType(field);
        }
        /*
         * Collections are arrays, primitive arrays and generic collections
         */
        else if (CollectionType.isCollection(field.getType())) {
            return CollectionType.getCollectionType(field);
        }
        else if (false) {
            //TODO for Map<K,V>
            //throw new NotImplementedException();
            throw new IllegalArgumentException("NotImplementedException");
        }
        else {
            return new ComplexType(field);
        }
    }

    /**
     * This method returns the same DataType implementation as
     * getCollectionType(Field) for the field's class.
     * It should almost never be called from outside, but rather
     * is used for complex datatypes that have nested types
     * within, but no corresponding class' field.
     *
     * @param fieldType
     * @return
     * @throws IllegalArgumentException
     */
    public static @NotNull
    DataType getDataType (Type fieldType, Class<?> fieldClass) throws IllegalAccessException {
        /*
         * Basetypes are primitives, their primitive object wrappers and Strings
         */
        if (BaseType.isBaseType(fieldClass)) {
            return BaseType.getBaseType(fieldClass);
        }
        /*
         * Collections are arrays, primitive arrays and generic collections
         */
        else if (CollectionType.isCollection(fieldClass)) {
            return CollectionType.getCollectionType(fieldClass, fieldType);
        }
        else if (false) {
            //TODO for Map<K,V>
            //throw new NotImplementedException();
            throw new IllegalArgumentException("NotImplementedException");
        }
        else {
            return new ComplexType(fieldType, fieldClass);
        }
    }

    public abstract String toString ();

    protected boolean isInterfaceOrAbstract() {
        boolean ret = ownClass.isInterface();
        ret = ret || Modifier.isAbstract(ownClass.getModifiers());
        return ret;
    }

    public String presentableFieldName() throws IllegalAccessException {
        String propertyName = getField() == null ? null : getField().getAnnotation(Property.class).name();
        if (propertyName == null || propertyName.equals("")) {
            StringBuilder modifiedFieldName = new StringBuilder();
            String fieldName = getField() == null ? getContentType(): getField().getName();

            for (int i = 0; i < fieldName.length(); i++) {
                if (i > 0 && Character.isUpperCase(fieldName.charAt(i)) && !Character.isUpperCase(fieldName.charAt(i - 1))) {
                    modifiedFieldName.append(" ");
                }
                if (i == 0) {
                    modifiedFieldName.append(Character.toUpperCase(fieldName.charAt(i)));
                } else {
                    modifiedFieldName.append(fieldName.charAt(i));
                }
            }

            return modifiedFieldName.toString();
        }
        return propertyName;
    }

    public String presentableDescription() throws IllegalAccessException {
        StringBuilder modifiedDescription = new StringBuilder();

        String propertyDescription = getField() == null ? null : getField().getAnnotation(Property.class).description();
        if (propertyDescription != null && !propertyDescription.equals("")) {
            modifiedDescription.append(propertyDescription);
            modifiedDescription.append(" (Type: ");
            modifiedDescription.append(getContentType());
            modifiedDescription.append(")");
            return modifiedDescription.toString();
        }
        else {
            modifiedDescription.append(getContentType());
            return modifiedDescription.toString();
        }
    }
}
