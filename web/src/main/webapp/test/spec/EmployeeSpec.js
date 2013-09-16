describe('Employee Crud Test', function() {

	var service = new EmployeeService();
	var employee = {
			name : 'test',
			birthday : 123456,
			salary : 1576,
			function : 'DEVELOPER'
		};

	it('list employees', function() {
		service.list().then(function(results){
			expect(results.length).toBe(3);
		}, function(){
			fail('No employees returned');
		});
	});
	
	it('get a employee', function() {
		service.list().then(function(results){
			expect(results.length).toBe(3);
		}, function(){
			fail('No employees returned');
		});
	});

	it('create a employee', function() {
		service.create(employee).then(function(result){
			expect(result).toBe(3);
		}, function(){
			fail('No employees created');
		});
	});
	
	it('update a employee', function() {
		employee.id = 1;
		service.update(employee).then(function(result){
			expect(result).toBe(3);
		}, function(){
			fail('No employees updated');
		});
	});
	
	it('remove a employee', function() {
		employee.id = 1;
		service.remove(employee).then(function(result){
			expect(result).toBe(3);
		}, function(){
			fail('No employees removed');
		});
	});
	
});