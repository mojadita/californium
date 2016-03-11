/*
 * (C) 2016 BaseN Oy, FI
 *
 * All rights reserved.
 * 
 */
package org.eclipse.californium.core.coap;

/**
 * 
 */
public class OptionSetChangeEvent {
    
    private OptionSet m_optionSet;
    private Option m_option;
    private OptionSetChangeEventType m_eventType;
    
    public OptionSetChangeEvent(
            OptionSetChangeEventType eventType,
            OptionSet optionSet,
            Option option) {
        m_eventType = eventType;
        m_optionSet = optionSet;
        m_option = option;
    }
    
    /**
     * @return the optionSet
     */
    public OptionSet getOptionSet() {
        return m_optionSet;
    }
    /**
     * @return the option
     */
    public Option getOption() {
        return m_option;
    }
    /**
     * @return the eventType
     */
    public OptionSetChangeEventType getEventType() {
        return m_eventType;
    }
}
