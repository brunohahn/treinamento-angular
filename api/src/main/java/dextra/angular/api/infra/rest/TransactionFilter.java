package dextra.angular.api.infra.rest;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class TransactionFilter implements ContainerRequestFilter, ContainerResponseFilter {

	private static final String TRANSACTION = "transaction";

	private final EntityManager entityManager;

	public TransactionFilter(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void filter(ContainerRequestContext request) throws IOException {
		if (!"get".equalsIgnoreCase(request.getMethod())) {
			EntityTransaction transaction = entityManager.getTransaction();
			transaction.begin();
			request.setProperty(TRANSACTION, transaction);
		}

	}

	@Override
	public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
		EntityTransaction transaction = (EntityTransaction) request.getProperty(TRANSACTION);
		if (transaction != null) {
			try {
				if (transaction.isActive()) {
					if (!transaction.getRollbackOnly()) {
						transaction.commit();
					} else if (transaction.getRollbackOnly()) {
						transaction.rollback();
					}
				}
			} catch (RollbackException e) {
				transaction.rollback();
				throw e;
			}
		}
	}

}