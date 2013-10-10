function EmployeeService($http, $log) {
	
	var error = function(data, status, func, request) {
		$log.error('Http Error ' + status + " : " + data);
	};

	this.list = function(callback) {
		$http.get('/dextra-angular/api/employees').success(callback).error(error);
	};
	
	this.listFunctions = function(callback) {
		$http.get('/dextra-angular/api/functions').success(callback).error(error);
	};

	this.get = function(id, callback) {
		$http.get('/dextra-angular/api/employees/' + id).success(callback).error(error);
	};

	this.update = function(employee, callback) {
		$http.put('/dextra-angular/api/employees/' + employee.id, employee).success(callback).error($scope.error);
	};

	this.create = function(employee, callback) {
		$http.post('/dextra-angular/api/employees', employee).success(callback).error($scope.error);
	};

	this.remove = function(employee, callback) {
		$http.delete('/dextra-angular/api/employees/' + employee.id).success(callback).error($scope.error);
	};

};