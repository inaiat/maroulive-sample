package br.com.digilabs.web.page;

import br.com.digilabs.domain.User;
import br.com.digilabs.wicket.crud.CrudPage;

public class UserPage extends CrudPage<User> {

	private static final long serialVersionUID = 7196379432800820361L;

	@Override
	public Class<User> getDomainClass() {
		return User.class;
	}

}
