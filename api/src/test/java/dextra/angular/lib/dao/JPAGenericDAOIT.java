package dextra.angular.lib.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dextra.angular.api.infra.dao.JPAGenericDAO;

public class JPAGenericDAOIT {

	public static final String QUERY_GET_DUMMY2 = "FROM DummyEntity WHERE name='test2'";
	public static final String QUERY_GET_DUMMY_BY_NAME = "FROM DummyEntity WHERE name=:name";
	public static final String QUERY_LIST_DUMMY1_DUMMY3 = "FROM DummyEntity WHERE name!='test2'";
	public static final String QUERY_LIST_DUMMYS_BY_NAME = "FROM DummyEntity WHERE name!=:name";

	public static final String NATIVE_GET_DUMMY2 = "SELECT * FROM DummyEntity WHERE name='test2'";
	public static final String NATIVE_GET_DUMMY_BY_NAME = "SELECT * FROM DummyEntity WHERE name=:name";
	public static final String NATIVE_LIST_DUMMY1_DUMMY3 = "SELECT * FROM DummyEntity WHERE name!='test2'";
	public static final String NATIVE_LIST_DUMMYS_NOT_BY_NAME = "SELECT * FROM DummyEntity WHERE name!=:name";

	public static final String EXECUTE_QUERY = "UPDATE DummyEntity SET name=:name";
	public static final String EXECUTE_NATIVE_QUERY = "UPDATE DummyEntity SET name=:name";

	private static final Map<String, ?> PARAMETER_DUMMY2 = Collections.singletonMap("name", "test2");
	private static final Map<String, ?> PARAMETER_DUMMY_TEST = Collections.singletonMap("name", "test");

	private static final Class<DummyEntity> clazz = DummyEntity.class;

	private static EntityManager em;

	@AfterClass
	public static void end() throws Exception {
		em.close();
	}

	@BeforeClass
	public static void start() throws Exception {
		em = Persistence.createEntityManagerFactory("default").createEntityManager();
	}

	private JPAGenericDAO dao;

	@Before
	public void setUp() throws Exception {

		beginTransaction();
		em.persist(new DummyEntity("test1"));
		em.persist(new DummyEntity("test2"));
		em.persist(new DummyEntity("test3"));
		commitTransaction();

		dao = new JPAGenericDAO(em);

	}

	@After
	public void tearDown() throws Exception {

		beginTransaction();
		List<DummyEntity> list = em.createQuery("from DummyEntity", DummyEntity.class).getResultList();
		for (Object object : list) {
			em.remove(object);
		}
		commitTransaction();

	}

	@Test
	public void testCount() {

		Long result = dao.count(clazz);

		assertEquals(3L, result.longValue());

	}

	@Test
	public void testCreateQuery() {

		CriteriaQuery<DummyEntity> query = dao.createQuery(DummyEntity.class);

		assertNotNull(query);

	}

	@Test
	public void testCreateQueryBuilder() {

		CriteriaBuilder builder = dao.createQueryBuilder();

		assertNotNull(builder);

	}

	@Test
	public void testDelete() {

		beginTransaction();

		dao.delete(getDummy2());

		commitTransaction();

		assertEquals(2L, dao.count(clazz));

	}

	@Test
	public void testDeleteById() {

		beginTransaction();

		dao.deleteById(clazz, getDummy2().getId());

		commitTransaction();

		assertEquals(2L, dao.count(clazz));

	}

	@Test
	public void testExecuteNamedQuery() {

		beginTransaction();
		long count = dao.executeNamedQuery("query5", PARAMETER_DUMMY_TEST);
		commitTransaction();

		assertEquals(3, count);

		for (DummyEntity dummy : dao.listAll(DummyEntity.class)) {
			assertEquals("test", dummy.getName());
		}

	}

	@Test
	public void testExecuteNativeQuery() {

		beginTransaction();
		long count = dao.executeNativeQuery(EXECUTE_NATIVE_QUERY, PARAMETER_DUMMY_TEST);
		commitTransaction();

		assertEquals(3, count);

		for (DummyEntity dummy : dao.listAll(DummyEntity.class)) {
			assertEquals("test", dummy.getName());
		}

	}

	@Test
	public void testExecuteQuery() {

		beginTransaction();
		long count = dao.executeQuery(EXECUTE_QUERY, PARAMETER_DUMMY_TEST);
		commitTransaction();

		assertEquals(3, count);

		for (DummyEntity dummy : dao.listAll(DummyEntity.class)) {
			assertEquals("test", dummy.getName());
		}

	}

	@Test
	public void testGetById() {

		DummyEntity dummy2 = getDummy2();

		DummyEntity result = dao.getById(clazz, dummy2.getId());

		assertEquals(dummy2, result);

	}

	@Test
	public void testGetByNamedQuery_WithoutParameters() {

		DummyEntity result = dao.getByNamedQuery("query1", clazz, null);

		assertEquals(getDummy2(), result);

	}

	@Test
	public void testGetByNamedQuery_WithParameters() {

		DummyEntity result = dao.getByNamedQuery("query2", clazz, PARAMETER_DUMMY2);

		assertEquals(getDummy2(), result);

	}

	@Test
	public void testGetByNativeQuery_WithoutParameters() {

		DummyEntity result = dao.getByNativeQuery(NATIVE_GET_DUMMY2, clazz, null);

		assertEquals(getDummy2(), result);

	}

	@Test
	public void testGetByNativeQuery_WithParameters() {

		DummyEntity result = dao.getByNativeQuery(NATIVE_GET_DUMMY_BY_NAME, clazz, PARAMETER_DUMMY2);

		assertEquals(getDummy2(), result);

	}

	@Test
	public void testGetByQuery_WithoutParameters() {

		DummyEntity result = dao.getByQuery(QUERY_GET_DUMMY2, clazz, null);

		assertEquals(getDummy2(), result);

	}

	@Test
	public void testGetByQuery_WithParameters() {

		DummyEntity result = dao.getByQuery(QUERY_GET_DUMMY_BY_NAME, clazz, PARAMETER_DUMMY2);

		assertEquals(getDummy2(), result);

	}

	@Test
	public void testInsert() {

		beginTransaction();

		dao.insert(new DummyEntity("test4"));

		commitTransaction();

		assertEquals(4L, dao.count(clazz));

	}

	@Test
	public void testList_WithLimit() {

		List<DummyEntity> list = dao.list(clazz, 1, 1);

		assertEquals(getDummy2(), list.get(0));

	}

	@Test
	public void testList_WithoutLimit() {

		List<?> list = dao.list(clazz, 0, 100);

		assertEquals(3L, list.size());

	}

	@Test
	public void testListAll() {

		List<?> list = dao.listAll(clazz);

		assertEquals(3L, list.size());

	}

	@Test
	public void testListByNamedQuery_WithLimitAndParameters() {

		List<DummyEntity> results = dao.listByNamedQuery("query4", clazz, 1, 2, PARAMETER_DUMMY2);

		assertEquals(1, results.size());
		assertEquals(getDummy3(), results.get(0));

	}

	@Test
	public void testListByNamedQuery_WithLimitWithoutParameters() {

		List<DummyEntity> results = dao.listByNamedQuery("query3", clazz, 1, 2, null);

		assertEquals(1, results.size());
		assertEquals(getDummy3(), results.get(0));

	}

	@Test
	public void testListByNamedQuery_WithoutParameters() {

		List<DummyEntity> results = dao.listByNamedQuery("query3", clazz, 0, 10, null);

		assertEquals(2, results.size());
		assertEquals(getDummy1(), results.get(0));
		assertEquals(getDummy3(), results.get(1));

	}

	@Test
	public void testListByNamedQuery_WithParameters() {

		List<DummyEntity> results = dao.listByNamedQuery("query4", clazz, 0, 10, PARAMETER_DUMMY2);

		assertEquals(2, results.size());
		assertEquals(getDummy1(), results.get(0));
		assertEquals(getDummy3(), results.get(1));

	}

	@Test
	public void testListByNativeQuery_WithLimitAndParameters() {

		List<DummyEntity> results = dao
				.listByNativeQuery(NATIVE_LIST_DUMMYS_NOT_BY_NAME, clazz, 1, 2, PARAMETER_DUMMY2);

		assertEquals(1, results.size());
		assertEquals(getDummy3(), results.get(0));

	}

	@Test
	public void testListByNativeQuery_WithLimitWithoutParameters() {

		List<DummyEntity> results = dao.listByNativeQuery(NATIVE_LIST_DUMMY1_DUMMY3, clazz, 1, 2, null);

		assertEquals(1, results.size());
		assertEquals(getDummy3(), results.get(0));

	}

	@Test
	public void testListByNativeQuery_WithoutParameters() {

		List<DummyEntity> results = dao.listByNativeQuery(NATIVE_LIST_DUMMY1_DUMMY3, clazz, 0, 10, null);

		assertEquals(2, results.size());
		assertEquals(getDummy1(), results.get(0));
		assertEquals(getDummy3(), results.get(1));

	}

	@Test
	public void testListByNativeQuery_WithParameters() {

		List<DummyEntity> results = dao.listByNativeQuery(NATIVE_LIST_DUMMYS_NOT_BY_NAME, clazz, 0, 10,
				PARAMETER_DUMMY2);

		assertEquals(2, results.size());
		assertEquals(getDummy1(), results.get(0));
		assertEquals(getDummy3(), results.get(1));

	}

	@Test
	public void testListByQuery_WithLimitAndParameters() {

		List<DummyEntity> results = dao.listByQuery(QUERY_LIST_DUMMYS_BY_NAME, clazz, 1, 2, PARAMETER_DUMMY2);

		assertEquals(1, results.size());
		assertEquals(getDummy3(), results.get(0));

	}

	@Test
	public void testListByQuery_WithLimitWithoutParameters() {

		List<DummyEntity> results = dao.listByQuery(QUERY_LIST_DUMMY1_DUMMY3, clazz, 1, 2, null);

		assertEquals(1, results.size());
		assertEquals(getDummy3(), results.get(0));

	}

	@Test
	public void testListByQuery_WithoutParameters() {

		List<DummyEntity> results = dao.listByQuery(QUERY_LIST_DUMMY1_DUMMY3, clazz, 0, 10, null);

		assertEquals(2, results.size());
		assertEquals(getDummy1(), results.get(0));
		assertEquals(getDummy3(), results.get(1));

	}

	@Test
	public void testListByQuery_WithParameters() {

		List<DummyEntity> results = dao.listByQuery(QUERY_LIST_DUMMYS_BY_NAME, clazz, 0, 10, PARAMETER_DUMMY2);

		assertEquals(2, results.size());
		assertEquals(getDummy1(), results.get(0));
		assertEquals(getDummy3(), results.get(1));

	}

	@Test
	public void testRefresh() {

		DummyEntity dummy = getDummy2();

		String oldName = dummy.getName();

		dummy.setName("NovoNome");

		dao.refresh(dummy);

		assertEquals(oldName, dummy.getName());

	}

	@Test
	public void testUpdate() {

		beginTransaction();

		DummyEntity oldDummy = getDummy2();

		oldDummy.setName("NovoNome");

		dao.update(oldDummy);

		commitTransaction();

		DummyEntity newDummy = dao.getById(clazz, oldDummy.getId());

		assertEquals(oldDummy.getName(), newDummy.getName());

	}

	private void beginTransaction() {
		em.getTransaction().begin();
	}

	private void commitTransaction() {
		em.getTransaction().commit();
		em.clear();
	}

	private DummyEntity getDummy1() {
		return dao.listAll(clazz).get(0);
	}

	private DummyEntity getDummy2() {
		return dao.listAll(clazz).get(1);
	}

	private DummyEntity getDummy3() {
		return dao.listAll(clazz).get(2);
	}

}
