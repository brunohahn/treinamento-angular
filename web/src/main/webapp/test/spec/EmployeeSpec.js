describe('Employee Test', function() {

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
	});
	
	it('cancel employee update', function() {
	});

	it('create a employee', function() {
	});
	
	it('update a employee', function() {
	});
	
	it('delete a employee', function() {
	});
	
});