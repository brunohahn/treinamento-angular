package andreluiznsilva.angular.api.infra.util;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import andreluiznsilva.angular.api.infra.exception.ConstraintValidationException;

public class ValidationUtils {

	public static void checkArgument(boolean expression, String errorMessageTemplate, Object... errorMessageArgs) {
		if (!expression) {
			throw new IllegalArgumentException(String.format(errorMessageTemplate, errorMessageArgs));
		}
	}

	public static void checkArgumentNotBlank(String value, String errorMessageTemplate, Object... errorMessageArgs) {
		ValidationUtils.checkArgument(ValidationUtils.isNotBlank(value), errorMessageTemplate, errorMessageArgs);
	}

	public static void checkArgumentNotNull(Object value, String errorMessageTemplate, Object... errorMessageArgs) {
		ValidationUtils.checkArgument(value != null, errorMessageTemplate, errorMessageArgs);
	}

	public static void checkArgumentNotNullOrZero(Number value, String errorMessageTemplate, Object... errorMessageArgs) {
		ValidationUtils.checkArgument(ValidationUtils.isNotNullOrZero(value), errorMessageTemplate, errorMessageArgs);
	}

	public static void checkState(boolean expression, String errorMessageTemplate, Object... errorMessageArgs) {
		if (!expression) {
			throw new IllegalStateException(String.format(errorMessageTemplate, errorMessageArgs));
		}
	}

	public static void checkStateNotBlank(String value, String errorMessageTemplate, Object... errorMessageArgs) {
		ValidationUtils.checkState(ValidationUtils.isNotBlank(value), errorMessageTemplate, errorMessageArgs);
	}

	public static void checkStateNotNull(Object value, String errorMessageTemplate, Object... errorMessageArgs) {
		ValidationUtils.checkState(value != null, errorMessageTemplate, errorMessageArgs);
	}

	public static void checkStateNotNullOrZero(Number value, String errorMessageTemplate, Object... errorMessageArgs) {
		ValidationUtils.checkState(ValidationUtils.isNotNullOrZero(value), errorMessageTemplate, errorMessageArgs);
	}

	public static void checkValid(Object bean, Validator validator, Class<?>... groups) {
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(bean, groups);
		if (!constraintViolations.isEmpty()) {
			throw new ConstraintValidationException(constraintViolations);
		}
	}

	public static String defaultIfBlank(String value, String defaultValue) {
		return ValidationUtils.isBlank(value) ? defaultValue : value;
	}

	public static <T> T defaultIfNull(T value, T defaultValue) {
		return value == null ? defaultValue : value;
	}

	public static <T extends Number> T defaultIfNullOrZero(T value, T defaultValue) {
		return ValidationUtils.isNullOrZero(value) ? defaultValue : value;
	}

	public static boolean isBlank(String value) {
		return value == null || value.isEmpty() || value.trim().isEmpty();
	}

	public static boolean isNotBlank(String value) {
		return value != null && !ValidationUtils.isBlank(value);
	}

	public static boolean isNotNullAndInRange(Date begin, Date end) {
		return begin != null && end != null && begin.before(end);
	}

	public static boolean isNotNullOrEmpty(Collection<?> collection) {
		return !ValidationUtils.isNullOrEmpty(collection);
	}

	public static boolean isNotNullOrEmpty(Map<?, ?> map) {
		return !ValidationUtils.isNullOrEmpty(map);
	}

	public static <T> boolean isNotNullOrEmpty(T[] array) {
		return !ValidationUtils.isNullOrEmpty(array);
	}

	public static boolean isNotNullOrZero(Number value) {
		return !ValidationUtils.isNullOrZero(value);
	}

	public static boolean isNullOrEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	public static boolean isNullOrEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	public static <T> boolean isNullOrEmpty(T[] array) {
		return array == null || array.length == 0;
	}

	public static boolean isNullOrNotInRange(Date begin, Date end) {
		return begin == null || end == null || begin.after(end);
	}

	public static boolean isNullOrZero(Integer value) {
		return value == null || value == 0;
	}

	public static boolean isNullOrZero(Number value) {
		return value == null || value.longValue() == 0;
	}

	private ValidationUtils() {
		// contrutor privado para evitar instanciacao
	}

}
