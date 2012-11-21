package br.com.digilabs.wicket.crud;

import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.digilabs.domain.BasicEntity;
import br.com.digilabs.wicket.bootstrap.BootstrapModalPanel;

public class BasicCrudModalPanel<T extends BasicEntity> extends BootstrapModalPanel implements DaoService {

	private static final long serialVersionUID = 3853964590576367771L;
	
	private final Class<T> entityType;
	
	@SpringBean
	private CrudDao crudDao;

	public BasicCrudModalPanel(String id, Class<T> entityType) {
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
