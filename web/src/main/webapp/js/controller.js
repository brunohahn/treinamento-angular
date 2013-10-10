function Controller($scope, $rootScope, $window, $filter, $http, employeeService) {

	$scope.openned = false;
	
	$scope.init = function() {
		
		employeeService.list(function(data) {
			$scope.employees = data;
		});
		
		employeeService.listFunctions(function(data) {
			$scope.functions = data;
		});
		
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
			employeeService.update($scope.employee, function() {
				Utils.replaceItemById($scope.employee, $scope.employees);
				$scope.closeForm();
			});
		} else {
			employeeService.create($scope.employee, function(data) {
				Utils.addItem(data, $scope.employees);
				$scope.closeForm();
			});
		}

	}

	$scope.remove = function() {
		if ($scope.employee && $scope.employee.id) {
			employeeService.remove($scope.employee,function() {
				Utils.removeItemById($scope.employee, $scope.employees);
				$scope.closeForm();
			});
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