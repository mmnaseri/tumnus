/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/20/17, 1:51 PM)
 */
(function () {
    var module = angular.module('Login', []);
    module.controller('LoginController', ['$scope', '$http', function ($scope, $http) {
        $scope.user = {
            email: '',
            password: ''
        };
        $scope.error = false;
        $scope.submitLogin = function () {
            $scope.error = false;
            $http.post('/rest/v1/sessions', {
                email: $scope.user.email,
                password: $scope.user.password
            }).then(function () {
                $scope.error = false;
                window.location = '/' + window.location.hash;
            }, function () {
                $scope.error = true;
            });
        };
    }]);
})();