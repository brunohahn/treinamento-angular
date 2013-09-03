package dextra.angular.lib.dao;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
		@NamedQuery(name = "query1", query = JPAGenericDAOIT.QUERY_GET_DUMMY2),
		@NamedQuery(name = "query2", query = JPAGenericDAOIT.QUERY_GET_DUMMY_BY_NAME),
		@NamedQuery(name = "query3", query = JPAGenericDAOIT.QUERY_LIST_DUMMY1_DUMMY3),
		@NamedQuery(name = "query4", query = JPAGenericDAOIT.QUERY_LIST_DUMMYS_BY_NAME),
		@NamedQuery(name = "query5", query = JPAGenericDAOIT.EXECUTE_QUERY)
})
public class DummyEntity implements Serializable {

	@Id
	@GeneratedValue
	private Long	id;
	private String	name;

	public DummyEntity() {
	}

	public DummyEntity(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
