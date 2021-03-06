package com.pia.testing;


import com.pia.core.annotation.PluginMetadata;
import com.pia.core.annotation.Property;
import com.pia.core.plugin.Plugin;

import java.util.*;

@PluginMetadata
public class CollectionDataStorageTestPlugin extends Plugin {
	@Property(name = "StringLinkedList Object")
    public LinkedList<String> stringLinkedList;

	@Property(name = "StringArrayList Object")
    public ArrayList<String> stringArrayList;

	@Property(name = "StringList Object")
    private List<String> stringList;
    
	@Property(name = "IntegerHashSet Object")
    public HashSet<int[]> integerHashSet;

	@Property(name = "StringLinkedListSet Object")
    public Set<LinkedList<String>> stringLinkedListSet;

	@Property(name = "StringListSet Object")
    public Set<List<String>> stringListSet;

    @Override
    public void start () {

    }
}
