'use strict';

angular.module('cursos', [ 'ngRoute' ]).config(
		[ '$routeProvider', function($routeProvider) {
			$routeProvider.when('/cursos', {
				templateUrl : 'cursos/vista/cursos.html',
				controller : 'controladorCursos'
			});
		} ]).controller(
		'controladorCursos',
		function($scope, $rootScope, $http) {

			$scope.listaCursos = [];

			angular.element(document).ready(function() {
				$scope.listarCursos();
			});

			// Listado de cursos sin incluir los alumnos
			$scope.listarCursos = function() {
				$http.get('/rest/cursos?alumnos=true').then(function mySuccess(response) {
					$scope.listaCursos = response.data;
				}, function myError(response) {
					console.log(response);
				});
			};
		});