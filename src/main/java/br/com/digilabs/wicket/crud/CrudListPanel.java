package br.com.digilabs.wicket.crud;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.digilabs.dao.SimpleDao;
import br.com.digilabs.domain.BasicEntity;
import br.com.digilabs.exception.IntegrationException;

public class CrudListPanel<T extends BasicEntity> extends Panel {

	@SpringBean
	private SimpleDao simpleDao;

	private static final long serialVersionUID = -2915941722094627572L;

	private Class<T> entityType;

	private final CrudActionListenerCollection crudActionListener = new CrudActionListenerCollection();

	public CrudListPanel(String id, final Class<T> entityType) {
		super(id);
		this.entityType = entityType;
		Map<String, CrudUtil.PropertyAndField> propertiesAndFields = CrudUtil.getPropertiesAndFieldsFromBean(entityType);
		add(generateTableHeader("headView", propertiesAndFields));
		add(generateTableContent("tableContent", propertiesAndFields));
	}

	private ListView<CrudUtil.PropertyAndField> generateTableHeader(String id, Map<String, CrudUtil.PropertyAndField> fields) {
		ListView<CrudUtil.PropertyAndField> headView = new ListView<CrudUtil.PropertyAndField>(id, new ArrayList<CrudUtil.PropertyAndField>(fields.values())) {

			private static final long serialVersionUID = -5561784201214880067L;

			@Override
			protected void populateItem(ListItem<CrudUtil.PropertyAndField> listItem) {
				listItem.add(new Label("name", listItem.getModelObject().getField().getName()));
			}
		};
		return headView;
	}

	private ListView<CrudUtil.PropertyAndField> generateTableCols(String id, final T entity, Map<String, CrudUtil.PropertyAndField> propertiesAndFields) {

		ListView<CrudUtil.PropertyAndField> headView = new ListView<CrudUtil.PropertyAndField>(id, new ArrayList<CrudUtil.PropertyAndField>(
				propertiesAndFields.values())) {

			private static final long serialVersionUID = -5561784201214880067L;

			@Override
			protected void populateItem(ListItem<CrudUtil.PropertyAndField> item) {
				CrudUtil.PropertyAndField propertyAndField = item.getModelObject();
				Object value;
				try {
					value = propertyAndField.getPropertyDescriptor().getReadMethod().invoke(entity);
				} catch (Exception e) {
					throw new IntegrationException(e);
				}

				WebMarkupContainer link = null;
				String targetAttribute = null;
				CrudActionEvent crudActionEvent;
				for (Iterator<CrudActionEvent> iterator = getCrudActionListener().iterator(); iterator.hasNext();) {
					crudActionEvent = iterator.next();
					if (crudActionEvent.getTargetEntityType().isAssignableFrom(value.getClass())) {
						PageParameters pageParameters = new PageParameters();
						pageParameters.add("id", entity.getId());
						targetAttribute = crudActionEvent.getTargetAttribute();
						link = new BookmarkablePageLink<Void>("link", crudActionEvent.getTargetPage(), pageParameters);
						if (targetAttribute == null) {
							value = new String("TESTE");
						} else {
							Map<String, PropertyDescriptor> map = CrudUtil.getPropertiesMapFromBean(crudActionEvent.getTargetEntityType());
							try {
								Object attrEntity = propertyAndField.getPropertyDescriptor().getReadMethod().invoke(entity);
								Map<String,PropertyDescriptor> attrEntityMap = CrudUtil.getPropertiesMapFromBean(attrEntity.getClass());
							 	value = attrEntityMap.get(targetAttribute).getReadMethod().invoke(attrEntity);								
							} catch (Exception e) {
								throw new IntegrationException(e);
							} 

						}						
					}
				}

				Label label = new Label("value", String.valueOf(value));

				if (link == null) {
					link = new WebMarkupContainer("link");
					link.setVisibilityAllowed(false);
					link.add(new Label("linkValue", ""));
				} else {
					label.setVisibilityAllowed(false);
					Map<String, PropertyDescriptor> map = CrudUtil.getPropertiesMapFromBean(entity.getClass());
					link.add(new Label("linkValue", String.valueOf(value)));
				}

				item.add(label);
				item.add(link);

			}
		};
		return headView;
	}

	private ListView<T> generateTableContent(String id, final Map<String, CrudUtil.PropertyAndField> propertiesAndFields) {

		LoadableDetachableModel<List<T>> model = new LoadableDetachableModel<List<T>>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<T> load() {
				return simpleDao.getList(entityType);
			}
		};

		ListView<T> line = new ListView<T>(id, model) {

			private static final long serialVersionUID = -5561784201214880067L;

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
