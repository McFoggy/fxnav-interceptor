package fr.brouillard.fx.browser;

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


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import fr.brouillard.fx.browser.download.DownloadView;
import fr.brouillard.fx.browser.tabs.ByLocationView;
import fr.brouillard.fx.browser.tabs.ByMutationObserverView;

public class BrowserPresenter implements Initializable {
	@FXML
	private Tab byMutationMonitoringTab;
	
	@FXML
	private Tab byLocationTab;

	@FXML
	private BorderPane downloads;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ByLocationView blv = new ByLocationView();
		byLocationTab.setContent(blv.getView());
		
		ByMutationObserverView bmov = new ByMutationObserverView();
		byMutationMonitoringTab.setContent(bmov.getView());
		
		DownloadView dv = new DownloadView();
		downloads.setCenter(dv.getViewWithoutRootContainer());
	}
}
