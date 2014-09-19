package fr.brouillard.fx.browser.tabs;

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

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;

import javax.inject.Inject;

import fr.brouillard.fx.browser.HomeLocationProvider;
import fr.brouillard.fx.browser.download.Downloader;

public class ByLocationPresenter implements Initializable {
	@FXML
	private WebView webView;

	@FXML
	private TextField address;

	@FXML
	private Label status;

	@FXML
	private Label location;

	private SimpleStringProperty webEngineStatus = new SimpleStringProperty();

	@Inject
	private HomeLocationProvider homeLocation;

	@Inject
	private Downloader downloader;

	@Override
	public void initialize(URL u, ResourceBundle resources) {
		bindUI();

		webView.getEngine().locationProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (downloader.isDownloadable(newValue)) {
					downloader.download(newValue);
				}
			}
		});

		goToHomePage();
	}

	private void bindUI() {
		// Monitor status property of web-engine, make it observable
		webView.getEngine().setOnStatusChanged((we) -> webEngineStatus.set(we.getData()));

		// bind status to UI
		status.textProperty().bind(webEngineStatus);

		// bind location to UI
		location.textProperty().bind(webView.getEngine().locationProperty());

		webView.getEngine().documentProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				address.setText(newValue.getDocumentURI());
			}
		});
	}

	public void homeClick(ActionEvent click) {
		goToHomePage();
	}

	public void reloadClick(ActionEvent click) {
		webView.getEngine().reload();
	}

	public void addresssKeyReleased(KeyEvent releasedKey) {
		if (KeyCode.ENTER == releasedKey.getCode()) {
			if (address.getText() == null) {
				goToHomePage();
			} else {
				webView.getEngine().load(address.getText());
			}
		}
	}

	private void goToHomePage() {
		webView.getEngine().load(homeLocation.getDefaultLocation());
	}
}
