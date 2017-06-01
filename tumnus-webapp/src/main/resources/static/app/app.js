/**
    * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
    * @since 1.0 (5/16/17, 5:43 AM)
    */
(function () {
    var module = angular.module('Bin', ['ngRoute', 'ngSanitize', 'Bin.Editor', 'markdown']);
    module.config(function ($routeProvider) {
        $routeProvider.when('/', {
            templateUrl: 'app/views/home.ng'
        });
        $routeProvider.when('/add', {
            templateUrl: 'app/views/edit.ng'
        });
        $routeProvider.when('/profile', {
            templateUrl: 'app/views/profile.ng',
            controller: 'ProfileController'
        });
        $routeProvider.otherwise('/');
    });
})();