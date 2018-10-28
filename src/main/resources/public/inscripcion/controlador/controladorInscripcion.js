'use strict';

angular.module('inscripcion', [ 'ngRoute' ]).config(
		[ '$routeProvider', function($routeProvider) {
			$routeProvider.when('/inscripcion', {
				templateUrl : 'inscripcion/vista/inscripcion.html',
				controller : 'controladorInscripcion'
			});
		} ]).controller(
		'controladorInscripcion',
		function($scope, $rootScope, $http) {

			$scope.alumnoSeleccionado = {};
			$scope.cursoSeleccionado = {};
			$scope.listaCursos = [];
			$scope.busquedaId = "";

			// Busqueda del alumno por incribir
			$scope.buscarAlumnoPorId = function(id) {
				$http.get('/rest/alumno/' + $scope.busquedaId).then(
						function mySuccess(response) {
							console.log(response);
							$scope.alumnoSeleccionado = response.data;
							$scope.busquedaId = "";
						}, function myError(response) {
							console.log(response);
						});
			};

			angular.element(document).ready(function() {
				$scope.listarCursos();
			});

			// Listado de cursos sin incluir los alumnos
			$scope.listarCursos = function() {
				$http.get('/rest/cursos?alumnos=false').then(
						function mySuccess(response) {
							$scope.listaCursos = response.data;
						}, function myError(response) {
							console.log(response);
						});
			};

			// Accion de inscribir los alumnos
			$scope.inscribirse = function() {
				$http.post(
						'/rest/inscripcion/alumno/'
								+ $scope.alumnoSeleccionado.id + '/curso/'
								+ $scope.cursoSeleccionado).then(
						function mySuccess(response) {
							$scope.listaCursos = response.data;
						}, function myError(response) {
							console.log(response);
						});
			}

		});