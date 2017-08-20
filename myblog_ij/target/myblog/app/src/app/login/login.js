
angular.module( 'loginModule', [
    'ui.router'
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

    .controller( 'LoginCtrl', function LoginCtrl( $scope ) {
        $scope.message = "Login works";
    });