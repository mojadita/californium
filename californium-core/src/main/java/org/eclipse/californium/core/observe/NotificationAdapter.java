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

import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.coap.Response;

public class NotificationAdapter implements NotificationListener {

	@Override
	public void onRetransmission(Request request) {
	}

	@Override
	public void onResponse(Request request, Response response) {
	}

	@Override
	public void onAcknowledgement(Request request) {
	}

	@Override
	public void onReject(Request request) {
	}

	@Override
	public void onTimeout(Request request) {
	}

	@Override
	public void onCancel(Request request) {
	}

}
