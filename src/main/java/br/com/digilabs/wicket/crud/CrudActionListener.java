package br.com.digilabs.wicket.crud;

import org.apache.wicket.Page;

public interface CrudActionListener<T> {
	
	public void actionPerformed(T entity, Page page);	

}
