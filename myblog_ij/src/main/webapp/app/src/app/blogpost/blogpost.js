
angular.module("blogPostModule", ['ui.router',
    'ui.bootstrap',
    'blogPostService',
    'dateFilter'
])
    .directive('blogPost', function () {
    return {
        scope: {
            post: '=blogpost'
        },
        templateUrl: 'blogpost/blogpost.tpl.html',
        replace: true,
        restrict: 'EA',
        controller: ['$scope', function ($scope) {
        }]
    };
})
    .config(function config( $stateProvider ) {
        $stateProvider.state( 'blogpost', {
            url: '/blogpost/:id',
            views: {
                "main": {
                    controller: 'BlogPostCtrl',
                    templateUrl: 'blogpost/blogpost.detailed.tpl.html'
                }
            },
            data:{ pageTitle: 'Post' }
        });
    })

    .controller( 'BlogPostCtrl', ['$scope', '$stateParams', 'BlogPostService',
        function ( $scope , $stateParams, BlogPostService) {

        BlogPostService.getSelectedPost($stateParams.id).then(function (response) {
                $scope.post = response.data;
                console.log($scope.post);
            },
            function (error) {
                console.log(error);
                $scope.error = "Could not get data from server!";
            });
    }]);