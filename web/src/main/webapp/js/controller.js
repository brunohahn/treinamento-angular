function Controller($scope, $rootScope, $window, $filter, $http, $log) {

	$scope.openned = false;
	
	$scope.init = function() {
		
		$http.get('/dextra-angular/api/employees').success(function(data) {
			$scope.employees = data;
		}).error($scope.error);
		
		$http.get('/dextra-angular/api/functions').success(function(data) {
			$scope.functions = data;
		}).error($scope.error);
		
	}
	
	$scope.edit = function(employee) {
		$scope.openForm(angular.copy(employee));
	}

	$scope.create = function() {
		$scope.openForm({});
	}

	$scope.cancel = function() {
		$scope.closeForm();
	}

	$scope.save = function() {

		$scope.employee.birthday = Utils.parseDate($scope.formatedBirthday);

		if ($scope.employee.id) {
			$http.put('/dextra-angular/api/employees/' + $scope.employee.id, $scope.employee).success(function() {
				Utils.replaceItemById($scope.employee, $scope.employees);
				$scope.closeForm();
			}).error($scope.error);
		} else {
			$http.post('/dextra-angular/api/employees', $scope.employee).success(function(data) {
				Utils.addItem(data, $scope.employees);
				$scope.closeForm();
			}).error($scope.error);
		}

	}

	$scope.remove = function() {
		if ($scope.employee && $scope.employee.id) {
			$http.delete('/dextra-angular/api/employees/' + $scope.employee.id).success(function() {
				Utils.removeItemById($scope.employee, $scope.employees);
				$scope.closeForm();
			}).error($scope.error);
		}else{
			$scope.closeForm();
		}
	}

	$scope.openForm = function(employee) {
		$scope.employee = employee;
		$scope.formatedBirthday = Utils.formatDate($scope.employee.birthday);
		$scope.openned = true;
	}

	$scope.closeForm = function() {
		$scope.employee = {};
		$scope.openned = false;
	}

	$scope.error = function(data, status, func, request) {
		$log.error('Http Error ' + status + " : " + data);
	}

}