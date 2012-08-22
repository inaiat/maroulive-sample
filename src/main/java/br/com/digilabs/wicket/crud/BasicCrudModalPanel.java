package br.com.digilabs.wicket.crud;

import br.com.digilabs.domain.BasicEntity;
import br.com.digilabs.wicket.bootstrap.BootstrapModalPanel;

public class BasicCrudModalPanel<T extends BasicEntity> extends BootstrapModalPanel {

	private static final long serialVersionUID = 3853964590576367771L;
	
	private final Class<T> entityType;

	private final CrudDao dao;

	public BasicCrudModalPanel(String id, Class<T> entityType, CrudDao dao) {
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
