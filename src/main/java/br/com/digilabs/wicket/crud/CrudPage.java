package br.com.digilabs.wicket.crud;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.digilabs.domain.BasicEntity;
import br.com.digilabs.web.base.BasePage;
import br.com.digilabs.wicket.bootstrap.BootstrapModalLink;

public abstract class CrudPage<T extends BasicEntity> extends BasePage {

	private static final long serialVersionUID = -884216005257406567L;	
	
	@SpringBean
	private CrudDao crudDao;
	
	private final CrudActionListenerCollection crudActionListener = new CrudActionListenerCollection();

	private final ListPanel<T> crudListPanel;

	public CrudPage() {
		this(null);
	}

	public CrudPage(PageParameters pageParameters) {
		add(crudListPanel = new ListPanel<T>("crudList", getDomainClass(), getDao()) {

			private static final long serialVersionUID = 2396656607254609652L;

			@Override
			public CrudActionListenerCollection getCrudActionListener() {
				return crudActionListener;
			}
			
		} );
		
		EditPanel<T> editPanel = new EditPanel<T>("editPanel", getDomainClass() ,crudDao);
		BootstrapModalLink modalLink = new BootstrapModalLink("create",editPanel);
		
		add(editPanel);
		add(modalLink);
		
//		PageParameters createParameters = new PageParameters();
//		createParameters.add("action", "create");
//		add(new BookmarkablePageLink<Void>("create",this.getClass(),createParameters));
	}
	
	public abstract Class<T> getDomainClass();
	
	public abstract CrudDao getDao();

	public CrudActionListenerCollection getCrudActionListener() {
		return crudActionListener;
	}

}
