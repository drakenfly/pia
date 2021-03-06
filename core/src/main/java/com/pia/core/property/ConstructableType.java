package com.pia.core.property;

import java.util.List;

public interface ConstructableType {
    List<PiaConstructor> getConstructors();
    void setChosenConstructor (PiaConstructor constructor) throws IllegalAccessException;
    PiaConstructor getChosenConstructor() throws IllegalAccessException;
    List<DataType> getChosenArgumens();
}
