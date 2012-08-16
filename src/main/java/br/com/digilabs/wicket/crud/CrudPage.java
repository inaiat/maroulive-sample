package br.com.digilabs.wicket.crud;

import br.com.digilabs.web.base.BasePage;

public abstract class CrudPage<T> extends BasePage {

	private static final long serialVersionUID = -884216005257406567L;
	
	public CrudPage(Class<T> entity) {
		add(new CrudList<T>("crudList", entity));
	}

}
