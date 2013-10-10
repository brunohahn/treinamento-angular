describe('Employee Service Test', function() {

	var page = new IndexPage();
	var employee = {
			name : 'test',
			birthday : '22/07/84',
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

	it('create a employee', function() {
		employee = {
			name : 'a',
			birthday : '22/07/84',
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
			birthday : '22/07/84',
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

});