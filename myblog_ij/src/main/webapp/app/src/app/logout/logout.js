
angular.module( 'logoutModule', [
    'ui.router',
    'authenticationService'
])
    .config(function config( $stateProvider ) {
        $stateProvider.state( 'logout', {
            url: '/logout',
            views: {
                "main": {
                    controller: 'LogoutCtrl',
                    templateUrl: 'logout/logout.tpl.html'
                }
            },
            data:{ pageTitle: 'Logout' }
        });
    })

    .controller( 'LogoutCtrl', function LogoutCtrl( $scope, $location, LoginService ) {
        var successHandler = function (response) {
            $location.path('/');
        };
        var failureHandler = function (error) {
            //
        };
        $scope.logout = function() {
            LoginService.logout(successHandler, failureHandler);
        };
    });