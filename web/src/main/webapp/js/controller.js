function Controller($scope, $rootScope, $window, $filter) {

	$scope.openned = false;

	$scope.employees = [ {
		"id" : 1,
		"name" : "Fulano",
		"birthday" : new Date(),
		"salary" : 1000.0,
		"function" : "DEVELOPER"
	}, {
		"id" : 2,
		"name" : "Beltrano",
		"birthday" : new Date(),
		"salary" : 5000.0,
		"function" : "PO"
	}, {
		"id" : 3,
		"name" : "Ciclano",
		"birthday" : new Date(),
		"salary" : 10000.0,
		"function" : "MANAGER"
	} ];

	$scope.functions = [ "MANAGER", "PO", "DEVELOPER" ];

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
			Utils.replaceItemById($scope.employee, $scope.employees);
		} else {
			Utils.addItem(result, $scope.employees);
		}

		$scope.closeForm();

	}

	$scope.remove = function() {
		if ($scope.employee && $scope.employee.id) {
			Utils.removeItemById($scope.employee, $scope.employees);
		}
		$scope.closeForm();
	}

	$scope.openForm = function(employee) {
		$scope.employee = employee;
		// $scope.formatedBirthday = $filter('date')($scope.employee.birthday,
		// 'dd/MM/yyyy');
		$scope.formatedBirthday = Utils.formatDate($scope.employee.birthday);
		$scope.openned = true;
	}

	$scope.closeForm = function() {
		$scope.employee = {};
		$scope.openned = false;
	}

}