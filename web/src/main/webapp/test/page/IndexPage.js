function IndexPage() {

	this.clickCancel = function() {
		element('#cancel').click();
	}

	this.clickSave = function() {
		element('#save').click();
	}

	this.clickDelete = function() {
		element('#remove').click();
	}

	this.clickCreate = function() {
		element('#create').click();
	}
	
	this.clickEmployee = function(index) {
		element("#employees tbody tr:eq(" + index + ") td:first").click();
	}
	
	this.countEmployees = function() {
		return element('#employees tbody tr').count();
	}

	this.fillForm = function(employee) {
		input('employee.name').enter(employee.name);
		input('employee.birthday').enter(employee.birthday);
		input('employee.salary').enter(employee.salary);
		select('employee.function').option(employee.function);
	}

}