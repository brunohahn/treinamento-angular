function EmployeeService($http, $q, appConfig) {

	this.list = function(id) {
		var deferred = $q.defer();
		$http.get(appConfig.baseApiUrl + '/employees').success(function(data) {
			deferred.resolve(data);
		}).error(function(data, status, func, request) {
			deferred.reject(data);
		});
		return deferred.promise; 
	};
	
	this.get = function(id) {
		var deferred = $q.defer();
		$http.get(appConfig.baseApiUrl + '/employees/' + id + '.json').success(function(data) {
			deferred.resolve(data);
		}).error(function(data, status, func, request) {
			deferred.reject(data);
		});
		return deferred.promise;
	};

	this.create = function(employee) {
		var deferred = $q.defer();
		var self = this;
		$http.post(appConfig.baseApiUrl + '/employees.json', employee).success(function(data, status, func, request) {
			deferred.resolve(data);
		}).error(function(data, status, func, request) {
			deferred.reject();
		});
		return deferred.promise;
	};

	this.update = function(employee) {
		var deferred = $q.defer();
		$http.put(appConfig.baseApiUrl + '/employees/' + employee.id + '.json', employee).success(function(data) {
			deferred.resolve(data);
		}).error(function(data, status, func, request) {
			deferred.reject(data);
		});
		return deferred.promise;
	};
	
	this.remove = function(employee) {
		var deferred = $q.defer();
		$http.delete(appConfig.baseApiUrl + '/employees/' + employee.id + '.json', employee).success(function(data) {
			deferred.resolve(data);
		}).error(function(data, status, func, request) {
			deferred.reject(data);
		});
		return deferred.promise;
	};

};
