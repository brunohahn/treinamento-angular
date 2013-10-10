var appModule = angular.module('app', []);

appModule.service("employeeService", function($http, $log){
	return new EmployeeService($http, $log);
})

appModule.filter("reverse", function() {
	return function(input) {
		return input.split("").reverse().join("");
	};
});
appModule.directive("tooltip", function() {
	return {
		restrict : 'A',
		link : function(scope, element, attributes) {
			element.tooltip({
				trigger : 'hover',
				title : attributes.tooltip,
				container : 'body'
			});
		}
	}
});
appModule.directive("alert", function() {
	return {
		restrict : 'E',
		replace: true,
	    transclude: true,
		template: '<div class="alert"><a href="#" class="close" data-dismiss="alert">&times;</a><strong ng-transclude></strong></div>'
	}
});

appModule.filter("abbreviate", function() {
	return function(input, max) {
		var sufix = '';
		max = max ? max : 15;
		if (input.length > max) {
			sufix = '...';
		}
		return input.substring(0, max) + sufix;
	};
});
appModule.directive("icon", function() {
	return {
		restrict : 'E',
		replace : true,
		transclude : true,
		template : '<a href="#"><i class="icon-{{type}}"></i></a>',
		scope : {
			type : '@'
		}
	}
});
