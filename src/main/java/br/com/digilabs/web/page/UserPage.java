package br.com.digilabs.web.page;

import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.digilabs.domain.Address;
import br.com.digilabs.domain.User;
import br.com.digilabs.wicket.crud.CrudActionEvent;
import br.com.digilabs.wicket.crud.CrudDao;
import br.com.digilabs.wicket.crud.CrudPage;

public class UserPage extends CrudPage<User> {

	private static final long serialVersionUID = 7196379432800820361L;
	
	@SpringBean
	private CrudDao simpleDao;
	
	public UserPage() {
		
		getCrudActionListener().add(new CrudActionEvent<Address>(Address.class, AddressPage.class,"city","address"));
		
//		Address address = new Address();
//		address.setAddress("Rio Branco");
//		address.setCity("Rio de Janeiro");
//		
//		simpleDao.saveOrUpdate(address);
//		
//		User user = new User();
//		user.setName("Juliana");
//		user.setDateOfBirth(new Date());
//		user.setAdress(address);
//		
//		simpleDao.saveOrUpdate(user);		
		

	}

	@Override
	public Class<User> getDomainClass() {		
		return User.class;
	}

}
