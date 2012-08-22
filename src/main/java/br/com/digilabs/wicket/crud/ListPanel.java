package br.com.digilabs.wicket.crud;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import br.com.digilabs.domain.BasicEntity;

public class ListPanel<T extends BasicEntity> extends BasicCrudPanel<T> {

	private static final long serialVersionUID = -2915941722094627572L;

	private final CrudActionListenerCollection crudActionListener = new CrudActionListenerCollection();

	public ListPanel(String id, final Class<T> entityType, CrudDao dao) {
		super(id,entityType,dao);
		Map<String, CrudUtil.PropertyAndField> propertiesAndFields = CrudUtil.getPropertiesAndFieldsFromBean(entityType);
		add(generateTableHeader("headView", propertiesAndFields));
		add(generateTableContent("tableContent", propertiesAndFields));
	}

	private ListView<CrudUtil.PropertyAndField> generateTableHeader(String id, Map<String, CrudUtil.PropertyAndField> fields) {
		ListView<CrudUtil.PropertyAndField> headView = new ListView<CrudUtil.PropertyAndField>(id,
				new ArrayList<CrudUtil.PropertyAndField>(fields.values())) {

			private static final long serialVersionUID = -5561784201214880067L;

			@Override
			protected void populateItem(ListItem<CrudUtil.PropertyAndField> listItem) {
				listItem.add(new Label("name", listItem.getModelObject().getField().getName()));
			}
		};
		return headView;
	}

	private ListView<CrudUtil.PropertyAndField> generateTableCols(String id, final T entity,
			Map<String, CrudUtil.PropertyAndField> propertiesAndFields) {

		ListView<CrudUtil.PropertyAndField> headView = new ListView<CrudUtil.PropertyAndField>(id,
				new ArrayList<CrudUtil.PropertyAndField>(propertiesAndFields.values())) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<CrudUtil.PropertyAndField> item) {
				CrudUtil.PropertyAndField propertyAndField = item.getModelObject();
				Object columnValue = CrudUtil.invokeReadMethod(propertyAndField.getPropertyDescriptor(), entity);
				Object linkValue = null;

				WebMarkupContainer link = null;
				for (Iterator<CrudActionEvent> iterator = getCrudActionListener().iterator(); iterator.hasNext();) {
					CrudActionEvent crudActionEvent = iterator.next();
					if (crudActionEvent.getTargetEntityType().isAssignableFrom(columnValue.getClass())) {
						PageParameters pageParameters = new PageParameters();
						pageParameters.add("id", entity.getId());
						String targetAttribute = crudActionEvent.getTargetAttribute();
						link = new BookmarkablePageLink<Void>("link", crudActionEvent.getTargetPage(), pageParameters);
						if (targetAttribute == null) {
							linkValue = columnValue.getClass().getSimpleName();
						} else {
							linkValue = CrudUtil.invokeReadMethod(columnValue.getClass(), columnValue, targetAttribute);
						}
					}
				}

				Label label = new Label("value", String.valueOf(columnValue));

				if (link == null) {
					link = new WebMarkupContainer("link");
					link.setVisibilityAllowed(false);
					link.add(new Label("linkValue", ""));
				} else {
					label.setVisibilityAllowed(false);
					link.add(new Label("linkValue", String.valueOf(linkValue)));
				}

				item.add(label);
				item.add(link);

			}
		};
		return headView;
	}

	private ListView<T> generateTableContent(String id, final Map<String, CrudUtil.PropertyAndField> propertiesAndFields) {

		LoadableDetachableModel<List<T>> listModel = new LoadableDetachableModel<List<T>>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<T> load() {
				return getDao().getList(getEntityType());
			}
		};

		ListView<T> line = new ListView<T>(id, listModel) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<T> item) {
				item.add(generateTableCols("cols", item.getModelObject(), propertiesAndFields));

			}
		};

		return line;

	}

	public CrudActionListenerCollection getCrudActionListener() {
		return crudActionListener;
	}

}
