package dextra.angular.api.infra.rest;

import java.io.IOException;
import java.util.Collection;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@Provider
public class NormalizeResponseFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {

		if (response.getStatus() == 200) {

			Object entity = response.getEntity();

			if (entity != null && entity instanceof Collection) {
				Collection<?> collection = (Collection<?>) entity;
				if (collection.isEmpty()) {
					response.setStatus(Status.NO_CONTENT.getStatusCode());
				}
			}

			if ("post".equalsIgnoreCase(request.getMethod()) && entity != null) {
				response.setStatus(Status.CREATED.getStatusCode());
				response.setEntity(entity);
			}

		}

	}

}