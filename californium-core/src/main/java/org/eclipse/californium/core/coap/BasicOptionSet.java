/*
 * (C) 2016 BaseN Oy, FI
 *
 * All rights reserved.
 * 
 */
package org.eclipse.californium.core.coap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * There are only two kinds of Options in an OptionSet, 
 * {@code {@link List}<Option>} and {@link Option}s.
 */
public class BasicOptionSet implements Serializable{
    
    private static final long serialVersionUID = 3165608672502762030L;
    
    Map<OptionNumberRegistry, Serializable> m_map;
    
    public BasicOptionSet () {
        m_map = new EnumMap<OptionNumberRegistry, Serializable>(OptionNumberRegistry.class);
    }
    
    public BasicOptionSet clear() {
        m_map.clear();
        return this;
    }
    
    public BasicOptionSet unsetOption(OptionNumberRegistry name) {
        m_map.remove(name);
        return this;
    }
    
    public<T extends Serializable> BasicOptionSet setOption(OptionNumberRegistry name, T value) {
        if (name == null) throw new NullPointerException("name cannot be null");
        if (value == null) return unsetOption(name);
        if (name.isRepeatable()) 
            throw new IllegalAccessError(
                    "Option " + name + " is a list, use "
                    + "<T>addToOption(OptionNumberRegistry name, T value)");
        if (!name.getTargetClass().isAssignableFrom(value.getClass()))
            throw new ClassCastException(
                    "Cannot assign " + value.getClass().getName() 
                    + " to " + name.getTargetClass().getName() 
                    + " in Option " + name);
        m_map.put(name, value);
        return this;
    }
    
    @SuppressWarnings("unchecked")
    public<T extends Serializable> BasicOptionSet addToOption(OptionNumberRegistry name, T value) {
        if (name == null || value == null) 
            throw new NullPointerException("name and value cannot be null");
        if (name.isSingleValue())
            throw new IllegalAccessError(
                    "Option " + name + " is single value, use "
                    + "<T>setOption(OptionNumberRegistry name, T value)");
        if (!name.getTargetClass().isAssignableFrom(value.getClass()))
            throw new ClassCastException(
                    "Cannot assign " + value.getClass().getName() 
                    + " to " + name.getTargetClass().getName() 
                    + " in Option " + name);
        Serializable c = m_map.get(name);
        if (c == null) {
            m_map.put(name, c = new ArrayList<>());
        }
        List<T> l = (List<T>) c;
        l.add(value);
        return this;
    }
    
    @SuppressWarnings("unchecked")
    public<T extends Serializable> T getOption(OptionNumberRegistry name, Class<T> cl) {
        if (name == null || cl == null)
            throw new NullPointerException("name and cl must be not null");
        if (name.isRepeatable())
            throw new IllegalAccessError(
                    "Option " + name + " is list, use "
                    + "<T>getListOption(OptionNumberRegistry name, Class<T> cl)");
        if (!cl.isAssignableFrom(name.getTargetClass()))
            throw new ClassCastException(
                    "Cannot return Option " + name 
                    + " of type " + name.getTargetClass() 
                    + " as a " + cl.getName());
        return (T) m_map.get(name);
    }
    
    @SuppressWarnings("unchecked")
    public<T extends Serializable> List<T> getListOption(OptionNumberRegistry name, Class<T> cl) {
        if (name == null || cl == null)
            throw new NullPointerException("name and cl must be not null");
        if (name.isSingleValue())
            throw new IllegalAccessError(
                    "Option " + name + " is not a list, use " 
                    + "<T>getOption(OptionNumberRegistry name, Class<T> cl)");
        if (!cl.isAssignableFrom(name.getTargetClass()))
            throw new ClassCastException(
                    "Cannot return Option " + name 
                    + " of type " + name.getTargetClass() 
                    + " as a List<" + cl.getName() + ">");
        return (List<T>) m_map.get(name);
    }

}
