
angular.module( 'loginModule', [
    'ui.router',
    'toastr',
    'authenticationService'
])
    .config(function config( $stateProvider ) {
        $stateProvider.state( 'login', {
            url: '/login',
            views: {
                "main": {
                    controller: 'LoginCtrl',
                    templateUrl: 'login/login.tpl.html'
                }
            },
            data:{ pageTitle: 'Login' }
        });
    })

    .controller( 'LoginCtrl', function LoginCtrl( $scope, $location, toastr, LoginService ) {
        var successHandler = function (response) {
            $location.path('/');
        };
        var failureHandler = function (error) {
            toastr.error('Invalid username or password!');
        };
        $scope.login = function() {
            LoginService.login($scope.credentials, successHandler, failureHandler);
        };
    });