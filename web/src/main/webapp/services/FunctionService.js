function FunctionService($http, $q, appConfig) {

	this.list = function(id) {
		var deferred = $q.defer();
		$http.get(appConfig.baseApiUrl + '/functions').success(function(data) {
			deferred.resolve(data);
		}).error(function(data, status, func, request) {
			deferred.reject(data);
		});
		return deferred.promise;
	};

};
