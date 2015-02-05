package andreluiznsilva.angular.api.infra.exception;

import javax.validation.ValidationException;

public class BusinessValidationException extends ValidationException {

	private final Object[] parameters;

	public BusinessValidationException(String message, Object... parameters) {
		super(message);
		this.parameters = parameters;
	}

	public String getKey() {
		return getMessage();
	}

	public Object[] getParameters() {
		return parameters;
	}

}