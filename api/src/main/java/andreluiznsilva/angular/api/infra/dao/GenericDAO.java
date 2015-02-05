package andreluiznsilva.angular.api.infra.dao;

import java.util.List;
import java.util.Map;

public interface GenericDAO {

	Integer DEFAULT_FIRST_RESULT = 0;
	Integer DEFAULT_MAX_RESULT = 50;

	long count(Class<?> clazz);

	<T> Object createQuery(Class<T> clazz);

	Object createQueryBuilder();

	void delete(Object entity);

	void deleteById(Class<?> clazz, Object id);

	long executeNamedQuery(String query, Map<String, ?> parameters);

	long executeNativeQuery(String query, Map<String, ?> parameters);

	long executeQuery(String query, Map<String, ?> parameters);

	<T> T getById(Class<T> clazz, Object id);

	<T> T getByNamedQuery(String name, Class<T> clazz, Map<String, ?> parameters);

	<T> T getByNativeQuery(Object query, Class<T> clazz, Map<String, ?> parameters);

	<T> T getByQuery(Object query, Class<T> clazz, Map<String, ?> parameters);

	void insert(Object entity);

	<T> List<T> list(Class<T> clazz, Integer firstResult, Integer maxResults);

	<T> List<T> listAll(Class<T> clazz);

	<T> List<T> listByNamedQuery(String name, Class<T> clazz, Integer firstResult, Integer maxResults,
			Map<String, ?> parameters);

	<T> List<T> listByNativeQuery(Object query, Class<T> clazz, Integer firstResult, Integer maxResults,
			Map<String, ?> parameters);

	<T> List<T> listByQuery(Object query, Class<T> clazz, Integer firstResult, Integer maxResults,
			Map<String, ?> parameters);

	void refresh(Object entity);

	<T> T update(T entity);

}
