/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/20/17, 7:33 PM)
 */
(function () {
    var module = angular.module('Bin.Editor', ['ui.ace']);
    module.directive('markdownEditor', function () {
        return {
            templateUrl: '/app/modules/markdownEditor.ng',
            restrict: 'EA',
            scope: {
                ngModel: '='
            },
            controller: function ($scope, $element, $attrs) {
                $scope.preview = false;
                $scope.options = {
                    useWrapMode : true,
                    showGutter: false,
                    theme:'tomorrow',
                    mode: 'markdown',
                    firstLineNumber: 1
                };
                $scope.togglePreview = function () {
                    $scope.preview = !$scope.preview;
                };
            }
        };
    });
})();