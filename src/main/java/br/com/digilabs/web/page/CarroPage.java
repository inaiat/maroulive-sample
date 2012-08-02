package br.com.digilabs.web.page;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.digilabs.dao.CarroDao;
import br.com.digilabs.domain.Carro;
import br.com.digilabs.web.base.BasePage;

public class CarroPage extends BasePage {

	private static final long serialVersionUID = 7196379432800820361L;
	
	@SpringBean
	private CarroDao carroDao;
	
	public CarroPage() {
		
		IModel<List<Carro>> carros = new LoadableDetachableModel<List<Carro>>() {

			private static final long serialVersionUID = -2324160337600683764L;

			@Override
			protected List<Carro> load() {
				return carroDao.getList();
			}
			
		};
		
		ListView<Carro> listView = new ListView<Carro>("list",carros) {

			private static final long serialVersionUID = -6114820327799292378L;

			@Override
			protected void populateItem(ListItem<Carro> listItem) {
				listItem.add(new Label("actions",""));	
				
				Carro carro = listItem.getModelObject();
				
				listItem.add(new Label("cor",carro.getCor().toString()));				
				listItem.add(new Label("modelo",carro.getModelo()));		

			}
		};
		
		add(listView);
		
	}

}
