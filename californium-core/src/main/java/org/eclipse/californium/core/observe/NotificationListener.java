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

public interface NotificationListener {

		public void onRetransmission(Request request);
		
		/**
		 * Invoked when a response arrives.
		 * 
		 * @param response the response that arrives
		 */
		public void onResponse(Request request, Response response);

		/**
		 * Invoked when the message has been acknowledged by the remote endpoint.
		 */
		public void onAcknowledgement(Request request);

		/**
		 * Invoked when the message has been rejected by the remote endpoint.
		 */
		public void onReject(Request request);

		/**
		 * Invoked when the client stops retransmitting the message and still has
		 * not received anything from the remote endpoint. By default this is the
		 * case after 5 unsuccessful transmission attempts.
		 */
		public void onTimeout(Request request);

		/**
		 * Invoked when the message has been canceled. For instance, a user might
		 * cancel a request or a CoAP resource that is being observer might cancel a
		 * response to send another one instead.
		 */
		public void onCancel(Request request);

}
