package br.com.digilabs.wicket.crud;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.digilabs.domain.Address;
import br.com.digilabs.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ="/./applicationContext.xml")
public class HibernateTest {

	@Autowired
	private CrudDao simpleDao;

	@Test
	public void addUser() {
		
		Address address = new Address();
		address.setAddress("Rio Branco");
		address.setCity("Rio de Janeiro");
		
		simpleDao.saveOrUpdate(address);
		
		User user = new User();
		user.setName("Juliana");
		user.setDateOfBirth(new Date());
		user.setAdress(address);
		
		simpleDao.saveOrUpdate(user);		

	}

}
