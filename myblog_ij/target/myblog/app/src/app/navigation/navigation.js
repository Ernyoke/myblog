angular.module("navigationModule", [
    'ui.router',
    'ui.bootstrap',
    'authenticationService'
])

    .directive('navBar', function () {
        return {
            scope: {},
            templateUrl: 'navigation/navigation.tpl.html',
            replace: true,
            restrict: 'EA',
            controller: function ($scope, LoginService) {
                $scope.isLogedIn = function () {
                    return LoginService.isLogedIn();
                };
            }
        };
    });

    // .controller('LoginCtrl', ['$scope', 'LoginService'], function ($scope, LoginService) {});