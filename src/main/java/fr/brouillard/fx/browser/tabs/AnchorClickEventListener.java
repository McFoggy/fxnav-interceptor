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


import javax.inject.Inject;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.html.HTMLAnchorElement;

import fr.brouillard.fx.browser.download.Downloader;

public class AnchorClickEventListener implements EventListener {
	@Inject
	private Downloader downloader;
	
	@Override
	public void handleEvent(Event evt) {
		EventTarget target = evt.getCurrentTarget();
		HTMLAnchorElement anchorElement = (HTMLAnchorElement) target;
		String url = anchorElement.getHref();

		// If the clicked link is downloadable
		// then we prevent the browser to follow the link
		// and instead we perform the download ourselves
		if (downloader.isDownloadable(url)) {
			downloader.download(url);
			evt.preventDefault();
		}
	}
}
