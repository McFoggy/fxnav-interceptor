package fr.brouillard.fx.browser.download;

import java.util.ArrayList;
import java.util.List;

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
public class Downloader  {
	private List<DownloadListener> listeners = new ArrayList<>();
	
	/* (non-Javadoc)
	 * @see fr.brouillard.fx.browser.download.Downloader#isDownloadable(java.lang.String)
	 */
	public boolean isDownloadable(String url) {
		// stupid check
		return (url == null)?false:url.toLowerCase().endsWith(".pdf");
	}
	
	/* (non-Javadoc)
	 * @see fr.brouillard.fx.browser.download.Downloader#download(java.lang.String)
	 */
	public void download(String url) {
		System.out.println("DOWNLOADING: " + url);
		
		listeners.forEach(dl -> dl.downloadOccured(url));
	}
	
	public void addDownloadListener(DownloadListener dl) {
		listeners.add(dl);
	}
}
