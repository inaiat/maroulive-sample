/*
 *  Copyright 2012 inaiat.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package br.com.digilabs.web.page;

import br.com.digilabs.wicket.bootstrap.BootstrapModalLink;
import br.com.digilabs.web.base.BasePage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.PropertyModel;

public class HomePage extends BasePage {

    private static final long serialVersionUID = -3516141952665953127L;
    private String name;

    public HomePage() {
        MyModal modalPanel = new MyModal("modal");
        UserForm form = new UserForm("form");
        form.add(new RequiredTextField<String>("name", new PropertyModel<String>(this, "name")));
        form.add(new BootstrapModalLink("modalLink", modalPanel));
        add(form);
        add(modalPanel);

    }

    private class UserForm extends Form<Void> {

        private static final long serialVersionUID = -3455251252456718601L;

		public UserForm(String id) {
            super(id);
        }

        @Override
        protected void onSubmit() {
            super.onSubmit();
            info("Form submitted");
            success("Hello " + name);
        }
    }
}
