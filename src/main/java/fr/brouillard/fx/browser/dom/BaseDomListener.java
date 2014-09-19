package fr.brouillard.fx.browser.dom;

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


import java.util.function.Consumer;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class BaseDomListener<T extends Node> implements DOMListener {
	private Class<T> nodeClass;

	public BaseDomListener(Class<T> nodeClass) {
		this.nodeClass = nodeClass;
	}

	@Override
	public void nodeAdded(Object addedNode) {
		parse(addedNode, this::added);
	}
	
	@SuppressWarnings("unchecked")
	public void parse(Object o, Consumer<T> notifier) {
		Node node = (Node) o;
		if (node != null) {
			if (nodeClass.isAssignableFrom(node.getClass())) {
				// let's check that the node is of type we are looking for
				notifier.accept((T) node);
			}
			
			// then look at children
			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				parse(children.item(i), notifier);
			}
		}
	}

	@Override
	public void nodeRemoved(Object removedNode) {
		parse(removedNode, this::removed);
	}
	
	protected void added(T node) {};
	protected void removed(T node) {};
}
