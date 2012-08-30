package br.com.digilabs.wicket.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.wicket.validation.INullAcceptingValidator;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanPropertyValidator<T> implements INullAcceptingValidator<T> {

	 private transient final Logger log = LoggerFactory
	            .getLogger(BeanPropertyValidator.class);

	    private Class<?> beanClass;

	    private String propertyName;

	    private Class<?>[] groups;

	    /**
	     * Serialisation ID
	     */
	    private static final long serialVersionUID = 1L;

	    /**
	     * Constructs a BeanPropertyValidator.
	     * 
	     * @param beanClass
	     *            the class of the object we want to validate
	     * @param propertyName
	     *            the name of the object's property we want to validate
	     * @param groups
	     *            group or list of groups targeted for validation
	     */
	    public BeanPropertyValidator(final Class<?> beanClass, String propertyName,
	            final Class<?>... groups) {
	        this.beanClass = beanClass;
	        this.propertyName = propertyName;
	        this.groups = groups;
	    }

	    /**
	     * {@inheritDoc}
	     * <p>
	     * Validates an annotated bean field, would its value be
	     * <code>validatable.getValue()</code>.
	     * 
	     * @see javax.validation.Validator#validateValue(Class, String, Object,
	     *      Class...)
	     * 
	     */
	    public void validate(IValidatable<T> validatable) {

	        T value = validatable.getValue();

	        final Validator validator = Validation.buildDefaultValidatorFactory()
	                .getValidator();

	        Set<?> violations = validator.validateValue(beanClass, propertyName,
	                value, groups);

	        // For each violations we set the component state to ERROR
	        for (Object v : violations) {
	            ConstraintViolation<?> violation = (ConstraintViolation<?>) v;
	            validatable.error(new ValidationError().setMessage(propertyName
	                    + " " + violation.getMessage()));
	            log.error("Violation = " + propertyName + " "
	                    + violation.getMessage() + " - value was " + value);
	        }

	    }
}
