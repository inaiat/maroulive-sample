/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
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

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * A panel that displays {@link org.apache.wicket.feedback.FeedbackMessage}s in
 * a list view. The maximum number of messages to show can be set with
 * setMaxMessages().
 *
 * Bootstrap's FeedbackPanel 
 *
 * @see org.apache.wicket.feedback.FeedbackMessage
 * @see org.apache.wicket.feedback.FeedbackMessages
 * @see FeedbackPanel
 *
 * @author Inaiat
 */
public class BootstrapFeedbackPanel extends FeedbackPanel {

    private static final long serialVersionUID = 3855254884742723577L;

    public BootstrapFeedbackPanel(String id) {
        super(id);
    }

    public BootstrapFeedbackPanel(String id, IFeedbackMessageFilter filter) {
        super(id, filter);
    }

    /**
     * Gets the css class for the given message.
     *
     * @param message the message
     * @return the css class; by default, this returns feedbackPanel + the
     * message level, eg 'feedbackPanelERROR', but you can override this method
     * to provide your own
     */
    @Override
    protected String getCSSClass(final FeedbackMessage message) {
        return "alert alert-" + message.getLevelAsString().toLowerCase();
    }
}
