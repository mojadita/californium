/*******************************************************************************
 * Copyright (c) 2016 Sierra Wireless and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 * 
 * The Eclipse Public License is available at
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *    http://www.eclipse.org/org/documents/edl-v10.html.
 * 
 ******************************************************************************/
package org.eclipse.californium.core.observe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.core.network.Exchange.KeyToken;
import org.eclipse.californium.core.network.serialization.DataParser;
import org.eclipse.californium.core.network.serialization.Serializer;
import org.eclipse.californium.elements.RawData;

public class InMemoryObserveRequestStore implements ObserveRequestStore {

	private Map<KeyToken, Request> map = new ConcurrentHashMap<>();

	@Override
	public void add(Request request) {
		if (request != null) {
			map.put(new KeyToken(request.getToken()), request);
		}
	}

	@Override
	public Request get(Response response) {
		Request request = map.get(new KeyToken(response.getToken()));
		if (request != null) {
			RawData serialize = Serializer.serialize(request, null);
			DataParser parser = new DataParser(serialize.getBytes());
			Request newRequest = parser.parseRequest();
			return newRequest;
		}
		return null;
	}

	@Override
	public void remove(Request request) {
		map.remove(new KeyToken(request.getToken()));
	}
	
	public boolean isEmpty(){
		return map.isEmpty();
	}

	public void clear() {
		map.clear();
	}
}
