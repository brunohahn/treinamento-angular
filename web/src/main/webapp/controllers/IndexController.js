function IndexController($scope, $rootScope, $window, appConfig, employeeService, functionService) {

	$scope.openned = false;

	$scope.init = function() {
		$scope.name = appConfig.appName;
		$scope.message = 'Hello ' + appConfig.appName;
		employeeService.list().then(function(employees) {
			$scope.employees = employees;
		});
		functionService.list().then(function(functions) {
			$scope.functions = functions;
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
		if ($scope.employee.id) {
			employeeService.update($scope.employee).then(function() {
				Utils.replaceItemById($scope.employee, $scope.employees);
				$scope.closeForm();
			});
		} else {
			employeeService.create($scope.employee).then(function(result) {
				Utils.addItem(result, $scope.employees);
				$scope.closeForm();
			});
		}
	}

	$scope.remove = function() {
		if ($scope.employee && $scope.employee.id) {
			employeeService.remove($scope.employee).then(function() {
				Utils.removeItemById($scope.employee, $scope.employees);
				$scope.closeForm();
			});
		}
	}

	$scope.openForm = function(employee) {
		$scope.employee = employee;
		$scope.openned = true;
	}

	$scope.closeForm = function() {
		$scope.employee = {};
		$scope.openned = false;
	}

}