package andreluiznsilva.angular.api.infra.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;

public class ConstraintValidationException extends ValidationException {

	private final Set<ConstraintViolation<Object>> constraintViolations;

	public ConstraintValidationException(Set<ConstraintViolation<Object>> constraintViolations) {
		this.constraintViolations = constraintViolations;
	}

	public Set<ConstraintViolation<Object>> getConstraintViolations() {
		return constraintViolations;
	}

	@Override
	public String getMessage() {
		StringBuilder messages = new StringBuilder();
		for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
			messages.append("\r\n").
					append(constraintViolation.getPropertyPath().toString()).
					append(" ").
					append(constraintViolation.getMessage());
		}
		return messages.toString();
	}

}