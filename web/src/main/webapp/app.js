var appModule = angular.module('app', [ 'ngCookies', 'ui.bootstrap', 'dextra.input', 'dextra.http', 'dextra.i18n' ]);

appModule.service('employeeService', function($http, $q, appConfig) {
	return new EmployeeService($http, $q, appConfig);
});

appModule.service('functionService', function($http, $q, appConfig) {
	return new FunctionService($http, $q, appConfig);
});

appModule.value("dxBundle", bundle);
appModule.value("appConfig", config);