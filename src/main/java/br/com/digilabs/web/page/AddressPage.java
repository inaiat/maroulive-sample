package br.com.digilabs.web.page;

import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.digilabs.domain.Address;
import br.com.digilabs.wicket.crud.CrudDao;
import br.com.digilabs.wicket.crud.CrudPage;

public class AddressPage extends CrudPage<Address> {

	private static final long serialVersionUID = -5328574101836181933L;
	
	@SpringBean
	private CrudDao crudDao;

	@Override
	public Class<Address> getDomainClass() {
		return Address.class;
	}	

}
