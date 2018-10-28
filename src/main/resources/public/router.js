ejercicio.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.when('/', {
            title: 'Panel principal',
            templateUrl: 'index.html',
            controller: 'controladorBase'
        }).when('/alumnos', {
            title: 'Alumnos',
            templateUrl: 'alumnos/vista/alumnos.html',
            controller: 'controladorAlumnos'
        }).when('/academicos', {
        	title: 'Registros Academicos',
        	templateUrl: 'academicos/vista/academicos.html',
        	controller: 'controladorAcademicos'
        }).when('/cursos', {
        	title: 'Cursos',
        	templateUrl: 'cursos/vista/cursos.html',
        	controller: 'controladorCursos'
        }).when('/inscripcion', {
        	title: 'Registros Academicos',
        	templateUrl: 'inscripcion/vista/inscripcion.html',
        	controller: 'controladorInscripcion'
        }).otherwise({
            redirectTo: '/'
        });
    }
]);