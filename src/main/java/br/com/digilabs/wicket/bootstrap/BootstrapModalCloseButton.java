/*
 * Copyright 2012 Digilabs Solutions.
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
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.IModel;

/**
 *
 * @author inaiat
 */
public class BootstrapModalCloseButton extends AbstractLink {

    private static final long serialVersionUID = 8669064225493586171L;

    public BootstrapModalCloseButton(String id) {
        super(id);
    }

    public BootstrapModalCloseButton(String id, IModel<Void> model) {
        super(id, model);
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        AttributeModifier dataToggle = new AttributeModifier("data-dismiss", "modal");
        tag.addBehavior(dataToggle);
    }
}
