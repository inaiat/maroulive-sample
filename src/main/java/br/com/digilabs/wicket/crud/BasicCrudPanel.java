package br.com.digilabs.wicket.crud;

import org.apache.wicket.markup.html.panel.Panel;

import br.com.digilabs.domain.BasicEntity;

public class BasicCrudPanel<T extends BasicEntity> extends Panel {

	private static final long serialVersionUID = 3853964590576367771L;
	
	private final Class<T> entityType;

	private final CrudDao dao;

	public BasicCrudPanel(String id, Class<T> entityType, CrudDao dao) {
		super(id);
		this.entityType = entityType;
		this.dao = dao;
	}

	public CrudDao getDao() {
		return dao;
	}

	public Class<T> getEntityType() {
		return entityType;
	}

}
