package br.com.digilabs.wicket.crud;

import br.com.digilabs.web.base.BasePage;

public abstract class CrudPage<T> extends BasePage {

	private static final long serialVersionUID = -884216005257406567L;
	
	//private Map<String,Page> actionsListenersMap = new HashMap<String, Page>();

	//private List<CrudActionListener<Serializable>> actionListeners = new ArrayList<CrudActionListener<Serializable>>();

	public CrudPage() {
		add(new CrudListPanel<T>("crudList", getDomainClass()));
	}

	public abstract Class<T> getDomainClass();

//	public void addActionListeners(CrudActionListener<Serializable>... item) {
//		actionListeners.addAll(Arrays.asList(item));
//	}
//	
//	public void removeActionListener(CrudActionListener<Serializable> ...item) {
//		actionListeners.removeAll(Arrays.asList(item));
//	}
//	
//	protected List<CrudActionListener<Serializable>> getActionListeners() {
//		return actionListeners;
//	}
	

}
