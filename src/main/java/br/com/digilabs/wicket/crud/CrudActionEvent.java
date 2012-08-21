package br.com.digilabs.wicket.crud;

import java.io.Serializable;

public class CrudActionEvent implements Serializable {

	private static final long serialVersionUID = -6480895021658997926L;
	
	private final Class<? extends CrudPage<?>> targetPage;
	
	private final Class<?> targetEntityType;
	
	private final String targetAttribute;
	
	private final String defaultColumnValue;	

	
	public CrudActionEvent(Class<?> targetEntityType, Class<? extends CrudPage<?>> targetPage) {
		this(targetEntityType, targetPage, null, null);
	}
	
	public CrudActionEvent(Class<?> targetEntityType, Class<? extends CrudPage<?>> targetPage, String targetAttribute, String defaultColumnValue) {
		this.targetEntityType = targetEntityType;
		this.targetPage = targetPage;
		this.defaultColumnValue = defaultColumnValue;
		this.targetAttribute = targetAttribute;
	}


	public Class<? extends CrudPage<?>> getTargetPage() {
		return targetPage;
	}


	public Class<?> getTargetEntityType() {
		return targetEntityType;
	}


	public String getTargetAttribute() {
		return targetAttribute;
	}


	public String getDefaultColumnValue() {
		return defaultColumnValue;
	}
	
	
}
