package br.com.digilabs.wicket.crud;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.digilabs.domain.BasicEntity;

public class BasicCrudPanel<T extends BasicEntity> extends Panel implements DaoService {

	private static final long serialVersionUID = 3853964590576367771L;
	
	private final Class<T> entityType;
	
	@SpringBean
	private CrudDao crudDao;

	public BasicCrudPanel(String id, Class<T> entityType) {
		super(id);
		this.entityType = entityType;
	}

	public Class<T> getEntityType() {
		return entityType;
	}
	
	public CrudDao getDao() {
		return crudDao;
	}

}
