package br.com.digilabs.wicket.crud;

import java.beans.IntrospectionException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Id;

import org.junit.Test;

import br.com.digilabs.domain.User;

public class CrudTest {

	@Test
	public void test() throws IntrospectionException {

		Map<String, Field> fields = CrudUtil.getFieldsFromPropertiesBean(User.class);
		for (Entry<String, Field> field : fields.entrySet()) {
			System.out.print(field.getKey());
			System.out.println(" is key " + field.getValue().isAnnotationPresent(Id.class));
		}

		// fail("Not yet implemented");
	}

	@Test
	public void test2() throws InterruptedException {

		String s = new String("test");
		WeakReference r = new WeakReference(s);
		WeakReference sr = new WeakReference("aaa");
		System.out.println("before gc: r=" + r.get() + ", static=" + sr.get());
		System.gc();
		Thread.sleep(100);

		// only r.get() becomes null
		System.out.println("after gc: r=" + r.get() + ", static=" + sr.get());

	}

}
