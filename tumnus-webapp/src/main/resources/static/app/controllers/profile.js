/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/21/17, 9:42 PM)
 */
(function () {
    var module = angular.module('Bin');
    module.controller('ProfileController', function ($scope, $http) {
        $scope.user = {};
        $scope.loading = true;
        $scope.saved = false;
        $scope.error = '';
        $http.get('/rest/v1/users/me').then(function (response) {
            $scope.user.name = response.data.name;
            $scope.user.email = response.data.email;
            $scope.loading = false;
        });
        $scope.saveProfile = function () {
            $scope.error = '';
            $scope.loading = true;
            $http.post('/rest/v1/users/me', {
                name: $scope.user.name,
                email: $scope.user.email,
                currentPassword: $scope.user.currentPassword,
                newPassword: $scope.user.newPassword
            }).then(function (reponse) {
                $scope.saved = true;
                $scope.loading = false;
            }, function (response) {
                console.log(response.data.message);
                $scope.saved = false;
                $scope.error = response.data.message;
                $scope.loading = false;
            });
        };
    });
})();