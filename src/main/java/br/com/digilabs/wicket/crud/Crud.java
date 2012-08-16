package br.com.digilabs.wicket.crud;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class Crud extends Panel {

    private static final long serialVersionUID = 2449207633535228121L;

    public Crud(String id) {
	super(id,null);
    }
    
    public Crud(String id, IModel<?> model) {
	super(id, model);
    }

    
    

}
