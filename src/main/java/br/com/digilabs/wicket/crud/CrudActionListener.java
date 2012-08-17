package br.com.digilabs.wicket.crud;

import java.io.Serializable;

import org.apache.wicket.Page;

public interface CrudActionListener<T extends Serializable> {
	
	public void actionPerformed(String property, Page page);

}
