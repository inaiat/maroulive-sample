/*
 * Copyright 2012 inaiat.
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
package br.com.digilabs.web.page;

import br.com.digilabs.wicket.bootstrap.BootstrapModalCloseButton;
import br.com.digilabs.wicket.bootstrap.BootstrapModalPanel;

/**
 *
 * @author inaiat
 */
public class MyModal extends BootstrapModalPanel {

    public MyModal(String id) {
        super(id);
        
        add(new BootstrapModalCloseButton("close"));
    }
    
}
