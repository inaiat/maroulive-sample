package br.com.digilabs.wicket.crud;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.digilabs.dao.SimpleDao;

public class CrudList<T> extends Panel {

	@SpringBean
	private SimpleDao simpleDao;

	private static final long serialVersionUID = -2915941722094627572L;
	
	private Class<T> entity;

	public CrudList(String id, final Class<T> entity) {
		super(id);
		
		this.entity = entity;
		
		Map<String, Field> fields = CrudUtil.getFieldsFromPropertiesBean(entity);		

		add(generateTableHeader(fields));
		
		List<IModel<?>> list = new ArrayList<IModel<?>>();
		for (Field field : fields.values()) {
			Model<String> model = new Model<String>(field.getName());
			list.add(model);
		}
		


		LoadableDetachableModel<List<T>> model = new LoadableDetachableModel<List<T>>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<T> load() {
				return simpleDao.getList(entity);
			}
		};

		ListView<T> listView = new ListView<T>("listView", model) {

			private static final long serialVersionUID = -4624095310847431346L;

			@Override
			protected void populateItem(ListItem<T> arg0) {

			}
		};

		add(listView);

	}

	private ListView<Field> generateTableHeader(Map<String, Field> fields) {
		ListView<Field> headView = new ListView<Field>("headView", new ArrayList<Field>(fields.values()) ) {

			private static final long serialVersionUID = -5561784201214880067L;

			@Override
			protected void populateItem(ListItem<Field> listItem) {
				listItem.add(new Label("name", listItem.getModelObject().getName()));
			}
		};
		return headView;
	}	

	
	private ListView<T> generateTableContent(List<T> modelList) {
		ListView<T> line = new ListView<T>("tableContent", simpleDao.getList(entity)) {

			private static final long serialVersionUID = -5561784201214880067L;


			@Override
			protected void populateItem(ListItem<T> item) {
				listItem.add(new Label("name", listItem.getModelObject()));
				
			}
		};
		
		return line;

	}
	
	


}
