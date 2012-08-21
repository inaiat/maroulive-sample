package br.com.digilabs.wicket.crud;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import br.com.digilabs.domain.BasicEntity;
import br.com.digilabs.web.base.BasePage;

public abstract class CrudPage<T extends BasicEntity> extends BasePage {

	private static final long serialVersionUID = -884216005257406567L;
	
	private final CrudListPanel<T> crudListPanel;	

	public CrudPage() {
		this(null);
	}
	
	public CrudPage(PageParameters pageParameters) {
		add(crudListPanel = new CrudListPanel<T>("crudList", getDomainClass()));
	}

	public abstract Class<T> getDomainClass();
	
	
	public CrudActionListenerCollection getCrudActionListener() {
		return crudListPanel.getCrudActionListener();
	}

}
