angular.module('dextra.i18n', []).factory("dxI18n", function(dxBundle) {
	return function(key) {
		var prefixs = key.split('.');
		var object = dxBundle;
		for ( var i = 0; i < prefixs.length; i++) {
			var p = prefixs[i];
			if (object) {
				object = object[p];
			} else {
				break;
			}
		}
		return object && object != '' ? object : key;
	};
}).filter('dxI18n', function(dxI18n) {
	return function(input) {
		return dxI18n(input);
	}
}).directive('dxI18n', function(dxI18n) {
	return {
		restrict : 'A',
		link : function(scope, elm, attrs, ctrl) {
			elm.html(dxI18n(attrs.dxI18n));
		}
	}
}).directive('dxI18n', function(dxI18n) {
	return {
		restrict : 'E',
		link : function(scope, elm, attrs, ctrl) {
			elm.html(dxI18n(elm.html()));
		}
	}
}).value("dxBundle", {}).run(function($rootScope, dxI18n) {
	$rootScope.i18n = dxI18n;
});