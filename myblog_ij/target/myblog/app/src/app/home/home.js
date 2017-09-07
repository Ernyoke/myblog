angular.module( 'homeModule', [
    'ui.router',
    'blogPostModule',
    'blogPostService',
    'plusOne'
])
    .config(function config( $stateProvider ) {
        $stateProvider.state( 'home', {
            url: '/home',
            views: {
                "main": {
                    controller: 'HomeCtrl',
                    templateUrl: 'home/home.tpl.html'
                }
            },
            data:{ pageTitle: 'Home' }
        });
    })
    .controller( 'HomeCtrl', ['$scope', 'BlogPostService', function ( $scope , BlogPostService) {
        $scope.page = 1;
        BlogPostService.getPosts($scope.page).then(function (response) {
                $scope.posts = response.data;
            },
            function (error) {
                console.log(error);
                $scope.error = "Could not get data from server!";
            });
    }]);

