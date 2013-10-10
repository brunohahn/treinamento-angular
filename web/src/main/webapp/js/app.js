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
	return function(input) {
		return input;
	};
});
appModule.directive("icon", function() {
	return {
		restrict : 'E',
	}
});
