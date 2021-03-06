package com.pia.core.plugin;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * A simple plugin finder that works on a provided list of plugins.
 */
public class ListBasedPluginFinder extends PluginFinder {

    private List<Class<? extends Plugin>> plugins;

    /**
     * Creates a new instance with a provided list of plugins.
     * The passed plugins are checked against {@link com.pia.core.internal.PluginHelper}
     *
     * @param pluginList a list of plugins, must not be null
     */
    public ListBasedPluginFinder(@NotNull List<Class<? extends Plugin>> pluginList) {
        this.plugins = this.stripIncompletePlugins(pluginList);
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    protected List<Class<? extends Plugin>> getPluginsSynchronized() {
        return this.plugins;
    }
}
