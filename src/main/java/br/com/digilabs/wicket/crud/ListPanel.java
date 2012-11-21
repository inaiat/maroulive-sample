package br.com.digilabs.wicket.crud;

import java.beans.PropertyDescriptor;
import java.util.List;

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

	private final CrudActionListenerCollection crudActionListener;	

	public ListPanel(String id, final Class<T> entityType, final CrudActionListenerCollection crudActionListener) {
		super(id, entityType);
		this.crudActionListener = crudActionListener;
		List<String> fields = CrudUtil.getPropertiesNameFromBean(entityType);
		add(generateTableHeader("headView", fields));
		add(generateTableContent("tableContent", fields));
	}

	private ListView<String> generateTableHeader(String id, List<String> fields) {
		ListView<String> headView = new ListView<String>(id, fields) {

			private static final long serialVersionUID = -5561784201214880067L;

			@Override
			protected void populateItem(ListItem<String> listItem) {
				listItem.add(new Label("name", listItem.getModelObject()));
			}
		};
		return headView;
	}

	private ListView<String> generateTableCols(String id, final T entity, List<String> fields) {

		ListView<String> headView = new ListView<String>(id, fields) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<String> item) {
				PropertyDescriptor propertyDescriptor = CrudUtil.getPropertyFromBean(getEntityType(), item.getModelObject());
				Object columnValue = CrudUtil.invokeReadMethod(propertyDescriptor, entity);
				Object linkValue = null;

				WebMarkupContainer link = null;

				CrudActionEvent<?> crudActionEvent = CrudUtil.findListener(crudActionListener, columnValue.getClass());
				if (crudActionEvent != null) {
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

	private ListView<T> generateTableContent(String id, final List<String> fields) {

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
				item.add(generateTableCols("cols", item.getModelObject(), fields));

			}
		};

		return line;

	}

}
