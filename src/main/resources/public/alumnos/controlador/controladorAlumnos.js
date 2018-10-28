'use strict';

angular.module('alumnos', [ 'ngRoute' ]).config(
		[ '$routeProvider', function($routeProvider) {
			$routeProvider.when('/alumnos', {
				templateUrl : 'alumnos/vista/alumnos.html',
				controller : 'controladorAlumnos'
			});
		} ]).controller(
		'controladorAlumnos',
		function($scope, $rootScope, $http) {
			$scope.alumnoSeleccionado = {};
			$scope.listaAlumnos = [];
			$scope.status = {};

			// Clase utilitaria
			function getResponse(response) {
				$scope.status.code = response.status;
				$scope.status.text = response.statusText;
				console.log($scope.status);
			}

			$scope.listarAlumnos = function() {
				$http.get('/rest/alumnos').then(function mySuccess(response) {
					getResponse(response);
					$scope.listaAlumnos = response.data;
				}, function myError(response) {
					getResponse(response);
				});
			};

			$scope.getAlumno = function(alumno) {
				angular.copy(alumno, $scope.alumnoSeleccionado);
				angular.copy(alumno, $rootScope.alumno);
			}

			$scope.nuevoAlumno = function() {
				$scope.alumnoSeleccionado = {};
			}

			$scope.guardarAlumno = function() {
				// En caso de nuevo alumno
				console.log($scope.alumnoSeleccionado);
				if ($scope.alumnoSeleccionado.id == undefined) {
					$http.post('/rest/alumno', $scope.alumnoSeleccionado).then(
							function mySuccess(response) {
								// Validar retorno
								getResponse(response);
								$scope.alumnoSeleccionado = response.data;
								// Refresca la lista
								$scope.listarAlumnos();
							}, function myError(response) {
								getResponse(response);
							});
				} else {
					$http.put('/rest/alumno/' + $scope.alumnoSeleccionado.id,
							$scope.alumnoSeleccionado).then(
							function mySuccess(response) {
								// Validar retorno
								getResponse(response);
								$scope.alumnoSeleccionado = response.data;
								// Refresca la lista
								$scope.listarAlumnos();
							}, function myError(response) {
								getResponse(response);
							})
				}
			}

		});