package br.com.digilabs.dao;

import br.com.digilabs.domain.Carro;
import br.com.digilabs.persistence.AbstractHibernateDaoImpl;

public class CarroDaoImpl extends AbstractHibernateDaoImpl<Carro, Long> implements CarroDao {

	@Override
	protected Class<Carro> getDomainClass() {
		return Carro.class;
	}

}
