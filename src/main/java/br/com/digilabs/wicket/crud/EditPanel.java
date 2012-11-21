package br.com.digilabs.wicket.crud;

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
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

	private final T entityModel;

	private boolean createOperation = false;

	private final CrudActionListenerCollection crudActionListener;

	public EditPanel(String id, final Class<T> entityType, final CrudActionListenerCollection crudActionListener) {
		this(id, entityType, null, crudActionListener);
	}

	public EditPanel(String id, final Class<T> entityType, T entityObject, final CrudActionListenerCollection crudActionListener) {
		super(id, entityType);

		this.crudActionListener = crudActionListener;

		if (entityObject == null) {
			createOperation = true;
			try {
				this.entityModel = CrudUtil.createInstanceFromCass(entityType);
			} catch (Exception e) {
				throw new IntegrationException(e);
			}
		} else {
			this.entityModel = entityObject;
		}

		List<String> fields = CrudUtil.getPropertiesNameFromBean(entityType);

		Form<T> form = new Form<T>("form", new Model<T>(entityModel));
		form.setOutputMarkupPlaceholderTag(true);
		form.add(generateItems("items", fields));

		add(new AjaxButton("ajax-button", form) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				@SuppressWarnings("unchecked")
				T object = (T) form.getModelObject();
				getDao().save(object);
			}
		});

		add(form);
	}

	private ListView<String> generateItems(String id, List<String> fields) {
		ListView<String> headView = new ListView<String>(id, fields) {

			private static final long serialVersionUID = -5561784201214880067L;

			@SuppressWarnings("unchecked")
			@Override
			protected void populateItem(ListItem<String> listItem) {
				PropertyDescriptor propertyDescriptor = CrudUtil.getPropertyFromBean(getEntityType(), listItem.getModelObject());

				WebMarkupContainer formComponent = null;
				@SuppressWarnings("rawtypes")
				PropertyModel propertyModel = new PropertyModel(entityModel, propertyDescriptor.getName());
				Class<?> propertyType = propertyDescriptor.getPropertyType();
				if (createOperation && BasicEntity.class.isAssignableFrom(propertyType)) {
					List<?> list = getDao().getList(propertyType);
					formComponent = new CrudComboBoxField("formComponent", propertyModel, list, propertyType);
				} else if (propertyDescriptor.getPropertyType().isAssignableFrom(Date.class)) {
					formComponent = new CrudDateTextField("formComponent", propertyModel);
				} else {
					formComponent = new CrudTextField("formComponent", propertyModel.getObjectClass(), propertyModel);
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
			TextField textField = new TextField("field", type);
			textField.setModel(model);
			add(textField);
		}
	}

	private class CrudDateTextField extends Panel {
		private static final long serialVersionUID = 1L;

		public CrudDateTextField(String id, IModel<Date> model) {
			super(id);
			DateTextField dateTextField = new DateTextField("field",model);
			DatePicker datePicker = new DatePicker();
			datePicker.setShowOnFieldClick(true);
			dateTextField.add(datePicker);
			add(dateTextField);
		}
	}

	private class CrudComboBoxField extends Panel {
		private static final long serialVersionUID = 1L;

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public CrudComboBoxField(String id, IModel<?> model, List<?> choices, Class<?> classType) {
			super(id);
			DropDownChoice dropDownChoice = new DropDownChoice("field", model, choices);
			final CrudActionEvent<?> actionEvent = CrudUtil.findListener(crudActionListener, classType);
			if (actionEvent != null) {
				dropDownChoice.setChoiceRenderer(new IChoiceRenderer() {
					private static final long serialVersionUID = 1L;

					public Object getDisplayValue(Object object) {
						return CrudUtil.invokeReadMethod(object.getClass(), object, actionEvent.getTargetAttribute());
					}

					public String getIdValue(Object object, int index) {
						BasicEntity basicEntity = (BasicEntity) object;
						return String.valueOf(basicEntity.getId());
					}
				});
			}
			add(dropDownChoice);
		}
	}

}
