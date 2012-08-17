package br.com.digilabs.wicket.crud;

import java.io.Serializable;

import org.apache.wicket.Page;

public class CrudActionEvent<T> implements Serializable {

	private static final long serialVersionUID = -6480895021658997926L;
	
	private final Page page;
	
	private final Class<T> entityType;
	
	public CrudActionEvent(Class<T> entityType, Page page) {
		this.entityType = entityType;
		this.page = page;
	}
	
	public Class<T> getEntityType() {
		return entityType;
	}
	
	public Page getPage() {
		return page;
	}	

}
