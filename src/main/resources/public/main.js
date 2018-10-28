'use strict';

// Declare app level module which depends on views, and components
var app = angular.module('ejercicio', [ 'ngRoute', 'alumnos', 'inscripcion', 'cursos' ]);

app.config(['$locationProvider', '$routeProvider',
function($locationProvider, $routeProvider) {
	$locationProvider.hashPrefix('');
	$routeProvider.otherwise({
		redirectTo : '/'
	});
}]);

app.controller('controladorBase', function($scope, $rootScope) {

});