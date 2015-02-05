package andreluiznsilva.angular.api.domain.repository;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Validation;
import javax.validation.Validator;

import andreluiznsilva.angular.api.domain.entity.Employee;
import andreluiznsilva.angular.api.infra.dao.GenericDAO;
import andreluiznsilva.angular.api.infra.util.ValidationUtils;

public class EmployeeRepository {

	@Inject
	private GenericDAO genericDAO;

	private Class<Employee> entityClass = Employee.class;

	public Employee getById(Long entityId) {
		return genericDAO.getById(entityClass, entityId);
	}

	public List<Employee> list(Integer firstResult, Integer maxResults) {
		return genericDAO.list(entityClass, firstResult, maxResults);
	}

	public List<Employee> listAll() {
		return genericDAO.listAll(entityClass);
	}

	public Employee put(Employee entity) {

		validate(entity);

		if (entity.getId() == null) {
			genericDAO.insert(entity);
		} else {
			entity = genericDAO.update(entity);
		}

		return entity;

	}

	public void remove(Employee entity) {

		validate(entity);

		genericDAO.delete(entity);

	}

	public void removeById(Long entityId) {

		Employee entity = getById(entityId);

		if (entity != null) {
			remove(entity);
		}

	}

	protected void validate(Object entity) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		ValidationUtils.checkValid(entity, validator);
	}

}
