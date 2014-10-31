package andreluiznsilva.angular.api.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import andreluiznsilva.angular.api.infra.util.ValidationUtils;

public class ResetServlet extends HttpServlet {

	@Inject
	private EntityManager entityManager;

	public void cleanDB() throws IOException {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			entityManager.createNativeQuery("TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK ")
					.executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}

	public void populateDB() throws IOException {

		InputStream sql = Thread.currentThread().getContextClassLoader().getResourceAsStream("import.sql");

		List<String> lines = IOUtils.readLines(sql);

		for (String line : lines) {
			if (ValidationUtils.isNotBlank(line)) {
				EntityTransaction transaction = entityManager.getTransaction();
				try {
					transaction.begin();
					entityManager.createNativeQuery(line).executeUpdate();
					transaction.commit();
				} catch (Exception e) {
					if (transaction.isActive()) {
						transaction.rollback();
					}
					throw new IOException(e);
				}
			}

		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		cleanDB();
		populateDB();
		res.sendRedirect("");
	}

}