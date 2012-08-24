package br.com.digilabs.wicket.crud;

import java.io.Serializable;

import br.com.digilabs.domain.BasicEntity;

public class CrudActionEvent<T extends BasicEntity> implements Serializable {

	private static final long serialVersionUID = -6480895021658997926L;
	
	private final Class<? extends CrudPage<?>> targetPage;
	
	private final Class<T> targetEntityType;
	
	private final String targetAttribute;
	
	private final String defaultColumnValue;	

	
	public CrudActionEvent(Class<T> targetEntityType, Class<? extends CrudPage<T>> targetPage) {
		this(targetEntityType, targetPage, null, null);
	}
	
	public CrudActionEvent(Class<T> targetEntityType, Class<? extends CrudPage<T>> targetPage, String targetAttribute, String defaultColumnValue) {
		this.targetEntityType = targetEntityType;
		this.targetPage = targetPage;
		this.defaultColumnValue = defaultColumnValue;
		this.targetAttribute = targetAttribute;
	}


	public Class<T> getTargetEntityType() {
		return targetEntityType;
	}


	public String getTargetAttribute() {
		return targetAttribute;
	}


	public String getDefaultColumnValue() {
		return defaultColumnValue;
	}

	public Class<? extends CrudPage<?>> getTargetPage() {
		return targetPage;
	}
	
	
}
