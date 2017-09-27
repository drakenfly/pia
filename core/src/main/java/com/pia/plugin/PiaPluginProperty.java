package com.pia.plugin;

public interface PiaPluginProperty<T> {
    String getName ();

    String getDescription ();

    T getValue ();

    void setValue (T value);
}
