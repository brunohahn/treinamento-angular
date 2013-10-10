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
	});

	it('create a employee', function() {
	});

	it('update a employee', function() {
	});

	it('delete a employee', function() {
		
	});
	
	it('cancel employee creation', function() {
	});

	it('cancel employee update', function() {
	});

});