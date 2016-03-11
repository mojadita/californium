/*
 * (C) 2016 BaseN Oy, FI
 *
 * All rights reserved.
 * 
 */
package org.eclipse.californium.core.coap;

import java.util.EventListener;

/**
 * 
 */
public interface OptionSetChangeListener extends EventListener {
    void onChange(OptionSetChangeEvent event);
}
