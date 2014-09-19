package fr.brouillard.fx;

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


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.airhacks.afterburner.injection.Injector;

import fr.brouillard.fx.browser.BrowserView;
import fr.brouillard.fx.browser.misc.Logger;
import fr.brouillard.fx.browser.misc.SysoutLogger;
import fr.brouillard.fx.browser.webserver.EmbeddedWebServer;

public class App extends Application {
	@SuppressWarnings("serial")
	Map<String, String> defaultsParameters = new HashMap<String, String>() {
		{
			put("host", "127.0.0.1");
			put("port", "8080");
		}
	};

	@Override
	public void start(Stage stage) throws Exception {
		Injector.setConfigurationSource(t -> Optional.ofNullable(getParameters().getNamed().get(t)).orElse(defaultsParameters.get(t)));
		
		Logger sysoutLogger = new SysoutLogger();
		Injector.setModelOrService(Logger.class, sysoutLogger);
		
		final EmbeddedWebServer server = (EmbeddedWebServer) Injector.instantiateModelOrService(EmbeddedWebServer.class);

		server.start();

		stage.setOnCloseRequest((e) -> {
			try {
				server.stop();
			} catch (Exception ex) {
				// ignored
			}
		});

		BrowserView main = new BrowserView();
		Scene scene = new Scene(main.getView(), 400, 300);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
