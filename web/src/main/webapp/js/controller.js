function Controller($scope) {

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

	// Utilizar funções do Utils para manipular os arrays e formatar datas

}