describe('Employee Crud Test', function() {

	var page = new IndexPage();
	var employee = {
			name : 'test',
			birthday : 123456,
			salary : 1576,
			function : 'DEVELOPER'
		};

	beforeEach(function() {
		browser().navigateTo('../reset');
		browser().navigateTo('../');
	});
	
	it('list employees', function() {
		expect(page.countEmployees()).toBe(3);
	});
	
	it('cancel employee creation', function() {
		page.clickEmployee(1);
		page.fillForm(employee);
		page.clickCancel();
	});
	
	it('cancel employee update', function() {
		page.clickEmployee(0);
		page.fillForm(employee);
		page.clickCancel();
	});

	it('create a employee', function() {
		employee = {
			name : 'a',
			birthday : new Date(),
			salary : 3333,
			function : 'MANAGER'
		};
		page.clickCreate();
		page.fillForm(employee)
		page.clickSave();
		expect(page.countEmployees()).toBe(4);
	});
	
	it('update a employee', function() {
		employee = {
			name : 'b',
			birthday : 3453555,
			salary : 45637,
			function : 'PO'
		};
		page.clickEmployee(1);
		page.fillForm(employee)
		page.clickSave();
		expect(page.countEmployees()).toBe(3);
	});
	
	it('delete a employee', function() {
		page.clickEmployee(2);
		page.clickDelete();
		expect(page.countEmployees()).toBe(2);
	});
	
});