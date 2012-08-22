package br.com.digilabs.wicket.crud;

import java.util.ArrayList;
import java.util.Map;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import br.com.digilabs.domain.BasicEntity;

public class EditPanel<T extends BasicEntity> extends BasicCrudPanel<T> {
	
	private static final long serialVersionUID = -8549165424203389781L;

	public EditPanel(String id, final Class<T> entityType, CrudDao dao) {
		super(id,entityType,dao);
		
		Map<String, CrudUtil.PropertyAndField> propertiesAndFields = CrudUtil.getPropertiesAndFieldsFromBean(entityType);
		add(generateItems("items", propertiesAndFields));
	}
	
	private ListView<CrudUtil.PropertyAndField> generateItems(String id, Map<String, CrudUtil.PropertyAndField> fields) {
		ListView<CrudUtil.PropertyAndField> headView = new ListView<CrudUtil.PropertyAndField>(id,
				new ArrayList<CrudUtil.PropertyAndField>(fields.values())) {

			private static final long serialVersionUID = -5561784201214880067L;

			@Override
			protected void populateItem(ListItem<CrudUtil.PropertyAndField> listItem) {
				listItem.add(new Label("label", listItem.getModelObject().getField().getName()));
			}
		};
		return headView;
	}

}
