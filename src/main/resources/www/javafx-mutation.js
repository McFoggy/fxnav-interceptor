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
window.JavaFXMutation = (function() {
	var context = {};

	context.logger = (function() {
		return {
			log: function(msg) {
				var logContainer = document.getElementById('log-container');
				if (logContainer) {
					var span = document.createElement('span');
					span.appendChild(document.createTextNode(msg));
					logContainer.appendChild(span);
					logContainer.appendChild(document.createElement('br'));
				} else {
					console.log(msg)
				}
			} 
		}
	}());
	
	context.domListener = (function(){
		return {
			nodeAdded: function(node) {
				context.logger.log('node added: ' + node);
			},
			nodeRemoved: function(node) {
				context.logger.log('node removed: ' + node);
			}
		}
	}());
	
	context.mutationCallback = function(records, observer) {
		context.logger.log('mutation(s) detected: ' + records.length);
		records.forEach(function(mutation) {
			context.logger.log('added nodes: ' + mutation.addedNodes.length);
			for (var i = 0; i < mutation.addedNodes.length; ++i) {
				context.domListener.nodeAdded(mutation.addedNodes.item(i));
			}
			context.logger.log('removed nodes: ' + mutation.removedNodes.length);
			for (var i = 0; i < mutation.removedNodes.length; ++i) {
				context.domListener.nodeRemoved(mutation.removedNodes.item(i));
			}
		});
		observer.takeRecords();
	};
	
	context.monitorNodeForMutation = function(node) {
		if (typeof node == 'undefined') {
			context.logger.log('given node is null, cannot start monitoring');
			return;
		}
		
		var mo = new MutationObserver(context.mutationCallback);
		var config = {
			childList : true,
			subtree:true			
		};

		if (typeof context.domListener != 'undefined') {
			context.logger.log('domListener detected, start observing node ' + toString(node));
			mo.observe(node, config);
			// we also signal that the whole DOM in its actual state can be parsed
			context.domListener.nodeAdded(node);
		} else {
			logger.log('no domListener detected, no monitoring started');
		}
	}
	
	return context;
}());
 
