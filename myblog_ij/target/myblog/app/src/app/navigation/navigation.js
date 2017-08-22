angular.module("navigationModule", [
    'ui.router',
    'ui.bootstrap',
    'loginService'
])

    .directive('navBar', function () {
        return {
            scope: {},
            templateUrl: 'navigation/navigation.tpl.html',
            replace: true,
            restrict: 'EA',
            controller: function ($scope, LoginService) {
            }
        };
    });

    // .controller('LoginCtrl', ['$scope', 'LoginService'], function ($scope, LoginService) {});