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
package br.com.digilabs.web.base;

import br.com.digilabs.wicket.bootstrap.Bootstrap;
import br.com.digilabs.wicket.bootstrap.BootstrapFeedbackPanel;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;

public class BasePage extends WebPage {

    private static final long serialVersionUID = 3550193318265360660L;
    private final BootstrapFeedbackPanel feedbackPanel;

    public BasePage() {
        feedbackPanel = new BootstrapFeedbackPanel("feedbackPanel");
        feedbackPanel.setOutputMarkupPlaceholderTag(true);
        add(feedbackPanel);
    }

    public BootstrapFeedbackPanel getFeedbackPanel() {
        return feedbackPanel;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);        
        Bootstrap.renderHead(response);
    }
    
    
}
