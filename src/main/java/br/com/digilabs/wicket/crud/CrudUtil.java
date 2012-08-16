package br.com.digilabs.wicket.crud;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.digilabs.exception.IntegrationException;

public class CrudUtil {
	
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

	public static List<PropertyDescriptor> getPropertiesFromBean(Class<?> entity)  {
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
}
