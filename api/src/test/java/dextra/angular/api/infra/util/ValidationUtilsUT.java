package dextra.angular.api.infra.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ValidationUtilsUT {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsBlank1() {

		String string = "";

		boolean result = ValidationUtils.isBlank(string);

		assertTrue(result);

	}

	@Test
	public void testIsBlank2() {

		String string = " ";

		boolean result = ValidationUtils.isBlank(string);

		assertTrue(result);

	}

	@Test
	public void testIsBlank3() {

		String string = "   ";

		boolean result = ValidationUtils.isBlank(string);

		assertTrue(result);

	}

	@Test
	public void testIsBlankFalse() {

		String string = " 1 ";

		boolean result = ValidationUtils.isBlank(string);

		assertFalse(result);

	}

	@Test
	public void testIsNotBlank1() {

		String string = "";

		boolean result = ValidationUtils.isNotBlank(string);

		assertFalse(result);

	}

	@Test
	public void testIsNotBlank2() {

		String string = " ";

		boolean result = ValidationUtils.isNotBlank(string);

		assertFalse(result);

	}

	@Test
	public void testIsNotBlank3() {

		String string = "   ";

		boolean result = ValidationUtils.isNotBlank(string);

		assertFalse(result);

	}

	@Test
	public void testIsNotBlankFalse() {

		String string = " 1 ";

		boolean result = ValidationUtils.isNotBlank(string);

		assertTrue(result);

	}

}
