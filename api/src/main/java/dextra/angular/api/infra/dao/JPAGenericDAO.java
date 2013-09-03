package dextra.angular.api.infra.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class JPAGenericDAO implements GenericDAO {

	private final EntityManager entityManager;

	public JPAGenericDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public long count(Class<?> clazz) {

		CriteriaBuilder builder = createQueryBuilder();

		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

		criteria = criteria.select(builder.count(criteria.from(clazz)));

		return getByQuery(criteria, Long.class, null);

	}

	@Override
	public <T> CriteriaQuery<T> createQuery(Class<T> clazz) {
		return createQueryBuilder().createQuery(clazz);
	}

	@Override
	public CriteriaBuilder createQueryBuilder() {
		return entityManager.getCriteriaBuilder();
	}

	@Override
	public void delete(Object entity) {
		entityManager.remove(entity);
	}

	@Override
	public void deleteById(Class<?> clazz, Object id) {
		delete(getById(clazz, id));
	}

	@Override
	public long executeNamedQuery(String query, Map<String, ?> parameters) {
		return configQuery(entityManager.createNamedQuery(query), null, null, parameters).executeUpdate();
	}

	@Override
	public long executeNativeQuery(String query, Map<String, ?> parameters) {
		return configQuery(entityManager.createNativeQuery(query), null, null, parameters).executeUpdate();
	}

	@Override
	public long executeQuery(String query, Map<String, ?> parameters) {
		return configQuery(entityManager.createQuery(query), null, null, parameters).executeUpdate();
	}

	@Override
	public <T> T getById(Class<T> clazz, Object id) {
		return entityManager.find(clazz, id);
	}

	@Override
	public <T> T getByNamedQuery(String name, Class<T> clazz, Map<String, ?> parameters) {
		return resultSingle(namedQuery(name, clazz, null, null, parameters));
	}

	@Override
	public <T> T getByNativeQuery(Object query, Class<T> clazz, Map<String, ?> parameters) {
		return resultSingle(nativeQuery(query, clazz, null, null, parameters));
	}

	@Override
	public <T> T getByQuery(Object query, Class<T> clazz, Map<String, ?> parameters) {
		return resultSingle(query(query, clazz, null, null, parameters));
	}

	@Override
	public void insert(Object entity) {
		entityManager.persist(entity);
	}

	@Override
	public <T> List<T> list(Class<T> clazz, Integer firstResult, Integer maxResults) {

		CriteriaQuery<T> criteria = createQueryBuilder().createQuery(clazz);

		criteria = criteria.select(criteria.from(clazz));

		return resultList(query(criteria, clazz, firstResult, maxResults, null));

	}

	@Override
	public <T> List<T> listAll(Class<T> clazz) {

		CriteriaQuery<T> criteria = createQueryBuilder().createQuery(clazz);

		criteria = criteria.select(criteria.from(clazz));

		return resultList(entityManager.createQuery(criteria));

	}

	@Override
	public <T> List<T> listByNamedQuery(String name, Class<T> clazz, Integer firstResult, Integer maxResults,
			Map<String, ?> parameters) {
		return resultList(namedQuery(name, clazz, firstResult, maxResults, parameters));
	}

	@Override
	public <T> List<T> listByNativeQuery(Object query, Class<T> clazz, Integer firstResult, Integer maxResults,
			Map<String, ?> parameters) {
		return resultList(nativeQuery(query, clazz, firstResult, maxResults, parameters));
	}

	@Override
	public <T> List<T> listByQuery(Object query, Class<T> clazz, Integer firstResult, Integer maxResults,
			Map<String, ?> parameters) {
		return resultList(query(query, clazz, firstResult, maxResults, parameters));
	}

	@Override
	public void refresh(Object entity) {
		entityManager.refresh(entity);
	}

	@Override
	public <T> T update(T entity) {
		return entityManager.merge(entity);
	}

	private Query configQuery(Query q, Integer firstResult, Integer maxResults, Map<String, ?> parameters) {

		if (parameters != null) {
			for (Entry<String, ?> entry : parameters.entrySet()) {
				q.setParameter(entry.getKey(), entry.getValue());
			}
		}

		if (firstResult == null) {
			firstResult = DEFAULT_FIRST_RESULT;
		}

		if (maxResults == null) {
			maxResults = DEFAULT_MAX_RESULT;
		}

		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);

		return q;

	}

	private Query namedQuery(String name, Class<?> clazz, Integer firstResult, Integer maxResults,
			Map<String, ?> parameters) {
		Query q = entityManager.createNamedQuery(name, clazz);
		return configQuery(q, firstResult, maxResults, parameters);
	}

	private Query nativeQuery(Object query, Class<?> clazz, Integer firstResult, Integer maxResults,
			Map<String, ?> parameters) {

		Query q = null;

		if (query instanceof String) {
			q = entityManager.createNativeQuery(query.toString(), clazz);
		} else {
			throw new IllegalArgumentException(
					"Query is not a valid type! Use String types for natives queries.");
		}

		return configQuery(q, firstResult, maxResults, parameters);

	}

	private <T> Query query(Object query, Class<?> clazz, Integer firstResult, Integer maxResults,
			Map<String, ?> parameters) {

		TypedQuery<?> q = null;

		if (query instanceof String) {
			q = entityManager.createQuery(query.toString(), clazz);
		} else if (query instanceof CriteriaQuery) {
			CriteriaQuery<?> criteria = (CriteriaQuery<?>) query;
			q = entityManager.createQuery(criteria);
		} else {
			throw new IllegalArgumentException(
					"Query is not a valid type! Use CriteriaQuery or String types for queries.");
		}

		return configQuery(q, firstResult, maxResults, parameters);

	}

	@SuppressWarnings("unchecked")
	private <T> List<T> resultList(Query query) {
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	private <T> T resultSingle(Query query) {
		try {
			return (T) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}