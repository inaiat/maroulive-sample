package br.com.digilabs.wicket.crud;

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import br.com.digilabs.domain.BasicEntity;
import br.com.digilabs.exception.IntegrationException;

public class EditPanel<T extends BasicEntity> extends BasicCrudModalPanel<T> {

	private static final long serialVersionUID = -8549165424203389781L;
	
	private final IModel<T> entityModel;
	
	private boolean createOperation = false;
	
	public EditPanel(String id, final Class<T> entityType, CrudDao dao) {
		this(id,entityType,null,dao);
	}

	public EditPanel(String id, final Class<T> entityType, IModel<T> model, CrudDao dao) {
		super(id, entityType, dao);
		
		if (model==null) {
			createOperation = true;
			try {
				this.entityModel = new Model<T>(entityType.newInstance());
			} catch (Exception e) {
				throw new IntegrationException(e);
			}
		} else {
			this.entityModel = model;
		}

		List<String> fields= CrudUtil.getPropertiesNameFromBean(entityType);
		
		Form<T> form = new Form<T>("form", entityModel);
		form.add(generateItems("items", fields));
		form.add(new AjaxButton("ajax-button",form) {		
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				@SuppressWarnings("unchecked")
				T object = (T) form.getModelObject();
				getDao().saveOrUpdate(object);
			}
		});
		
		add(form);
	}

	private ListView<String> generateItems(String id, List<String> fields) {
		ListView<String> headView = new ListView<String>(id, fields) {

			private static final long serialVersionUID = -5561784201214880067L;

			@Override
			protected void populateItem(ListItem<String> listItem) {
				PropertyDescriptor propertyDescriptor = CrudUtil.getPropertyFromBean(getEntityType(), listItem.getModelObject());

				WebMarkupContainer formComponent = null;
				@SuppressWarnings("rawtypes")
				PropertyModel propertyModel = new PropertyModel(entityModel.getObject(), propertyDescriptor.getName());
				if (propertyDescriptor.getPropertyType().isAssignableFrom(Date.class)) {
					formComponent = new CrudDateTextField("formComponent",propertyModel);
				} else {
					formComponent = new CrudTextField("formComponent", String.class, propertyModel);
				}

				listItem.add(new Label("label", propertyDescriptor.getName()));
				listItem.add(formComponent);
			}
		};
		return headView;
	}
	

	private class CrudTextField extends Panel {
		private static final long serialVersionUID = 1L;

		@SuppressWarnings("unchecked")
		public CrudTextField(String id, Class<?> type, IModel<?> model) {
			super(id);
			@SuppressWarnings({ "rawtypes" })
			TextField textField = new TextField("textField",type);
			textField.setModel(model);
			add(textField);
		}
	}
	
	private class CrudDateTextField extends Panel {
		private static final long serialVersionUID = 1L;

		public CrudDateTextField(String id, IModel<?> model) {
			super(id);
			DateTextField dateTextField = new DateTextField("textField");
			DatePicker datePicker = new DatePicker();
			datePicker.setShowOnFieldClick(true);
			dateTextField.add(datePicker);
			add(dateTextField);
		}
	}

}
