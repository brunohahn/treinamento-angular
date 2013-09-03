package dextra.angular.api.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import dextra.angular.api.domain.vo.Function;

@Path("/functions")
public class FunctionResource {

	@GET
	@Produces(value = { APPLICATION_JSON, APPLICATION_XML })
	public Function[] list() {
		return Function.values();
	}

}
