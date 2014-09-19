package fr.brouillard.fx.browser.webserver;

/*
 * #%L
 * navigation-interceptor
 * %%
 * Copyright (C) 2014 Matthieu Brouillard
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import java.net.InetSocketAddress;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;

public class EmbeddedWebServer {
	private Server jettyServer;
	
	@Inject
	private String port;
	
	@Inject
	private String host;
	
	@PostConstruct
	private void init() {
		jettyServer = new Server(new InetSocketAddress(host, Integer.parseInt(port)));
		ResourceHandler rh = new ResourceHandler();
		rh.setBaseResource(Resource.newClassPathResource("/www"));
		jettyServer.setHandler(rh);
	}
	
	public void start() {
		try {
			jettyServer.start();
			System.out.println("web server started");
		} catch (Exception e) {
			throw new RuntimeException("failed to start embedded server", e);
		}
	}
	
	public void stop() {
		try {
			jettyServer.stop();
			System.out.println("web server stopped");
		} catch (Exception e) {
			throw new RuntimeException("failed to stop embedded server", e);
		}
	}
}
