package dextra.angular.api.rest;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.resteasy.plugins.providers.jackson.ResteasyJacksonProvider;
import org.jboss.resteasy.plugins.providers.jaxb.JAXBXmlRootElementProvider;

import dextra.angular.api.infra.rest.DefaultExceptionMapper;
import dextra.angular.api.infra.rest.NormalizeResponseFilter;
import dextra.angular.api.infra.rest.TransactionFilter;

public class Application extends javax.ws.rs.core.Application {

	@Inject
	private EntityManager entityManager;

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(EmployeeResource.class);
		classes.add(FunctionResource.class);
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		Set<Object> objects = new HashSet<Object>();
		loadProviders(objects);
		return objects;
	}

	private Object createJacksonJaxbJsonProvider() {
		ResteasyJacksonProvider result = new ResteasyJacksonProvider();
		result.configure(org.codehaus.jackson.map.SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
		result.configure(org.codehaus.jackson.map.SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
		result.configure(org.codehaus.jackson.map.SerializationConfig.Feature.INDENT_OUTPUT, true);
		result.configure(org.codehaus.jackson.map.SerializationConfig.Feature.AUTO_DETECT_FIELDS, false);
		result.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		result.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.AUTO_DETECT_FIELDS, false);
		return result;
	}

	private void loadProviders(Set<Object> objects) {
		objects.add(new DefaultExceptionMapper());
		objects.add(new TransactionFilter(entityManager));
		objects.add(new NormalizeResponseFilter());
		objects.add(new JAXBXmlRootElementProvider());
		objects.add(createJacksonJaxbJsonProvider());
	}

}
