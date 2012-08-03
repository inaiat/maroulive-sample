/*
 * Copyright 2011 inaiat.
 *
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
 */
package br.com.digilabs.wicket.bootstrap;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * 
 * @author inaiat
 */
public class BootstrapModalLink extends WebMarkupContainer {

	private static final long serialVersionUID = -6663542446743415397L;
	
	private final String targetId;

	public BootstrapModalLink(String id, Panel bootstrapModalPanel) {
		this(id,bootstrapModalPanel.getMarkupId(true));
	}
	
	public BootstrapModalLink(String id, String targetId) {
		super(id);
		this.targetId = targetId;
	}	
	
	@Override
	protected void onComponentTag(ComponentTag tag) {
		checkComponentTag(tag, "a");
		super.onComponentTag(tag);		
		AttributeModifier href = new AttributeModifier("href", "#" + targetId);
		AttributeModifier dataToggle = new AttributeModifier("data-toggle", "modal");
		tag.addBehavior(href);
		tag.addBehavior(dataToggle);
	}    

}
