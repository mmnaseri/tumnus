/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/20/17, 8:33 PM)
 */
(function () {
    var module = angular.module('Bin');
    module.directive('multiSelect', function ($compile) {
        var itemsHtml = '<div class="ui fluid multiple search selection dropdown"><input type="hidden" class="value-target" name="{{name}}">' +
            '<div class="default text">{{placeholder}}</div><div class="menu">' +
            '<div ng-repeat="item in source" class="item" data-value="{{value(item)}}"><i ng-show="icon(item)" class="icon {{icon(item)}}"></i>{{label(item)}}</div>' +
            '</div></div>';
        return {
            restrict: 'EA',
            templateUrl: 'app/directives/multi-select.ng',
            require: '?ngModel',
            replace: true,
            scope: {
                ngModel: '=',
                source: '=',
                placeholder: '@',
                defaultIcon: '@',
                name: '@'
            },
            controller: function ($scope) {
                var getOrDefault = function (value, otherwise) {
                    return angular.isDefined(value) ? value : otherwise;
                };
                var defaultIcon = function () {
                    return getOrDefault($scope.defaultIcon, '');
                };
                $scope.icon = function (item) {
                    return angular.isString(item) ? defaultIcon() : getOrDefault(item.icon, defaultIcon());
                };
                $scope.value = function (item) {
                    return angular.isString(item) ? item : item.value;
                };
                $scope.label = function (item) {
                    return angular.isString(item) ? item : getOrDefault(item.label, item.value);
                };
            },
            link: function ($scope, $element, $attrs, ngModel) {
                ngModel.$render = function () {
                    var itemsCompiled = $compile(itemsHtml);
                    var rendered = itemsCompiled($scope);
                    $element.html('');
                    $element.append(rendered);
                    var dropdown = $element.find('.dropdown');
                    dropdown.dropdown({
                        allowAdditions: true,
                        sortSelect: true,
                        allowReselection: true
                    });
                    dropdown.dropdown('set selected', $scope.ngModel || []);
                };
                $element.on('keyup click', function () {
                    var values = [];
                    $element.find('a.ui.label').each(function () {
                        values.push(angular.element(this).attr('data-value'));
                    });
                    ngModel.$setViewValue(values);
                });
            }
        };
    });
})();