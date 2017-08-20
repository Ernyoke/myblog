
angular.module( 'logoutModule', [
    'ui.router'
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

    .controller( 'LogoutCtrl', function LogoutCtrl( $scope ) {
        $scope.message = "Logout works";

    });