package andreluiznsilva.angular.api.infra.rest;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DefaultExceptionMapper implements ExceptionMapper<ValidationException> {

	private static final int UNPROCESSABLE_ENTITY = 422;

	/**
	 * http://httpstatus.es/
	 */
	@Override
	public Response toResponse(ValidationException exception) {

		Response result = null;

		if (exception instanceof ValidationException) {
			result = buildResponse(UNPROCESSABLE_ENTITY, exception);
		} else {
			return buildResponse(INTERNAL_SERVER_ERROR, exception);
		}

		return result;

	}

	private Response buildResponse(int status, Exception exception) {
		String message = exception.getMessage();
		return buildResponse(status, message);
	}

	private Response buildResponse(int status, String... messages) {
		return Response.status(status).entity(messages).build();
	}

	private Response buildResponse(Status status, Exception exception) {
		return buildResponse(status.getStatusCode(), exception);
	}

}