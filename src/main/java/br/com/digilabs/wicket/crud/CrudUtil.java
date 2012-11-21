package br.com.digilabs.wicket.crud;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.com.digilabs.exception.IntegrationException;

public class CrudUtil {
	
	public static <T> T createInstanceFromCass(Class<T> clazz) throws InstantiationException, IllegalAccessException {
		T t = (T) clazz.newInstance();
		return t;
	}

	public static List<String> getPropertiesNameFromBean(Class<?> entity) {
		List<PropertyDescriptor> descriptors = getPropertiesFromBean(entity);
		Map<String, Field> fields = getDeclaredFieldsHierarchy(entity);
		List<String> list = new ArrayList<String>();
		for (PropertyDescriptor propertyDescriptor : descriptors) {
			String name = propertyDescriptor.getName();
			if (fields.containsKey(name)) {
				list.add(name);
			}
		}
		return Collections.unmodifiableList(list);
	}

	public static Map<String, PropertyAndField> getPropertiesAndFieldsFromBean(Class<?> entity) {
		List<PropertyDescriptor> descriptors = getPropertiesFromBean(entity);
		Map<String, PropertyAndField> fieldsFromProperties = new HashMap<String, PropertyAndField>(descriptors.size());
		Map<String, Field> fields = getDeclaredFieldsHierarchy(entity);
		for (PropertyDescriptor propertyDescriptor : descriptors) {
			if (fields.containsKey(propertyDescriptor.getName())) {
				String key = propertyDescriptor.getName();
				PropertyAndField propertyDescriptorAndField = new PropertyAndField(fields.get(key), propertyDescriptor);
				fieldsFromProperties.put(key, propertyDescriptorAndField);
			}
		}
		return Collections.unmodifiableMap(fieldsFromProperties);
	}

	public static Map<String, Field> getFieldsFromPropertiesBean(Class<?> entity) {
		List<PropertyDescriptor> descriptors = getPropertiesFromBean(entity);
		Map<String, Field> fieldsFromProperties = new HashMap<String, Field>(descriptors.size());
		Map<String, Field> fields = getDeclaredFieldsHierarchy(entity);
		for (PropertyDescriptor propertyDescriptor : descriptors) {
			if (fields.containsKey(propertyDescriptor.getName())) {
				fieldsFromProperties.put(propertyDescriptor.getName(), fields.get(propertyDescriptor.getName()));
			}
		}
		return Collections.unmodifiableMap(fieldsFromProperties);
	}

	public static List<PropertyDescriptor> getPropertiesFromBean(Class<?> entity) {
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(entity);
		} catch (IntrospectionException e) {
			throw new IntegrationException(e);
		}
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		return Arrays.asList(propertyDescriptors);
	}

	public static Map<String, Field> getDeclaredFieldsHierarchy(Class<?> clazz) {
		Map<String, Field> fieldsMap = new HashMap<String, Field>();
		for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
			for (Field field : c.getDeclaredFields()) {
				fieldsMap.put(field.getName(), field);
			}
		}
		return Collections.unmodifiableMap(fieldsMap);
	}

	public static Map<String, PropertyDescriptor> getPropertiesMapFromBean(Class<?> entity) {
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(entity);
		} catch (IntrospectionException e) {
			throw new IntegrationException(e);
		}
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		Map<String, PropertyDescriptor> propertyDescriptorMap = new HashMap<String, PropertyDescriptor>();
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			propertyDescriptorMap.put(propertyDescriptor.getName(), propertyDescriptor);
		}

		return Collections.unmodifiableMap(propertyDescriptorMap);
	}

	/**
	 * Return {@link PropertyDescriptor} of entity specified on property param
	 * or return {@code null} if this property doesn't exist in the entity.
	 * 
	 * @param entity
	 * @param property
	 * @return
	 */
	public static PropertyDescriptor getPropertyFromBean(Class<?> entity, String property) {
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(entity);
		} catch (IntrospectionException e) {
			throw new IntegrationException(e);
		}
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		PropertyDescriptor propertyDescriptorToReturn = null;
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			if (property.equals(propertyDescriptor.getName())) {
				propertyDescriptorToReturn = propertyDescriptor;
				break;
			}
		}
		return propertyDescriptorToReturn;
	}

	public static Object invokeReadMethod(Class<?> entity, Object object, String property) {
		try {
			PropertyDescriptor propertyDescriptor = getPropertyFromBean(entity, property);
			if (propertyDescriptor == null) {
				throw new IntegrationException("Property " + property + " not found in " + entity.getSimpleName());
			}
			return propertyDescriptor.getReadMethod().invoke(object);
		} catch (Exception e) {
			throw new IntegrationException(e);
		}
	}

	public static Object invokeReadMethod(PropertyDescriptor propertyDescriptor, Object object) {
		try {
			return propertyDescriptor.getReadMethod().invoke(object);
		} catch (Exception e) {
			throw new IntegrationException(e);
		}
	}

	/**
	 * Return a {@link CrudActionEvent} specified in classType or or return
	 * {@code null} if this classType doesn't exist in the
	 * {@link CrudActionListenerCollection}
	 * 
	 * @param crudActionListener
	 * @param classType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static CrudActionEvent<?> findListener(CrudActionListenerCollection crudActionListener, Class<?> classType) {
		CrudActionEvent<?> crudActionEventToReturn = null;
		for (Iterator<CrudActionEvent<?>> iterator = crudActionListener.iterator(); iterator.hasNext();) {
			@SuppressWarnings("rawtypes")
			CrudActionEvent crudActionEvent = iterator.next();
			if (crudActionEvent.getTargetEntityType().isAssignableFrom(classType)) {
				crudActionEventToReturn = crudActionEvent;
			}
		}
		return crudActionEventToReturn;
	}

	public static class PropertyAndField implements Serializable {

		private static final long serialVersionUID = -5833660069297440308L;

		private Field field;
		private PropertyDescriptor propertyDescriptor;

		public PropertyAndField() {
		}

		public PropertyAndField(Field field, PropertyDescriptor propertyDescriptor) {
			this.field = field;
			this.propertyDescriptor = propertyDescriptor;
		}

		public Field getField() {
			return field;
		}

		public void setField(Field field) {
			this.field = field;
		}

		public PropertyDescriptor getPropertyDescriptor() {
			return propertyDescriptor;
		}

		public void setPropertyDescriptor(PropertyDescriptor propertyDescriptor) {
			this.propertyDescriptor = propertyDescriptor;
		}

	}
}
