package fr.brouillard.fx.browser.download;

/*
 * #%L
 * fxnav-interceptor
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

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import javax.inject.Inject;

public class DownloadPresenter implements DownloadListener, Initializable {
	@Inject
	Downloader downloader;
	
	@FXML
	private VBox downloads;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		downloader.addDownloadListener(this);
	}

	@Override
	public void downloadOccured(String url) {
		Label download = new Label(url);
		download.getStyleClass().add("download");
		downloads.getChildren().add(download);
		
		Timeline tlStayShown = new Timeline();
	    KeyValue kvStayOpaque = new KeyValue(download.opacityProperty(), 1);
	    KeyFrame kfStayOpaque = new KeyFrame(Duration.millis(2000), kvStayOpaque);
	    tlStayShown.getKeyFrames().add(kfStayOpaque);
	    
		Timeline tlDisappear = new Timeline();
	    KeyValue kvDisappear = new KeyValue(download.opacityProperty(), 0);
	    KeyFrame kfDisappear = new KeyFrame(Duration.millis(500), kvDisappear);
	    tlDisappear.getKeyFrames().add(kfDisappear);

	    SequentialTransition sequential = new SequentialTransition(tlStayShown, tlDisappear);
	    sequential.setOnFinished((ae) -> downloads.getChildren().remove(download));
	    sequential.play();
	}
}
