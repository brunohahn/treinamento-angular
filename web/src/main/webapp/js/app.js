var appModule = angular.module('app', []);

appModule.filter("reverse", function() {
	return function(input) {
		return input;
	};
});

appModule.directive("tooltip", function() {
	return {
		restrict : 'A',
		link : function(scope, element, attributes) {
		}
	}
});

appModule.directive("alert", function() {
	return {
		restrict : 'E',
		link : function(scope, element, attributes) {
		}
	}
});
