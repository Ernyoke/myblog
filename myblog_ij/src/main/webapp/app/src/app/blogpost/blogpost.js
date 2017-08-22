
angular.module("blogPostModule", [
    'ui.router',
    'ui.bootstrap',
    'blogPostService',
    'toastr',
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
            data:{ pageTitle: 'Post' },
            abstract: true
        });
    })

    .config(function config( $stateProvider ) {
        $stateProvider.state( 'blogpost.show', {
            url: '/show',
            views: {
                "blogpost": {
                    controller: 'BlogPostCtrlShow',
                    templateUrl: 'blogpost/blogpost.show.tpl.html'
                }
            },
            data:{ pageTitle: 'Post.show' }
        });
    })

    .config(function config( $stateProvider ) {
        $stateProvider.state( 'blogpost.edit', {
            url: '/edit',
            views: {
                "blogpost": {
                    controller: 'BlogPostCtrlEdit',
                    templateUrl: 'blogpost/blogpost.edit.tpl.html'
                }
            },
            data:{ pageTitle: 'Post.edit' }
        });
    })

    .controller( 'BlogPostCtrl', ['$scope', '$stateParams', 'toastr', 'BlogPostService',
        function ( $scope , $stateParams, toastr, BlogPostService) {

            BlogPostService.getSelectedPost($stateParams.id).then(function (response) {
                    $scope.post = response.data;
                    console.log($scope.post);
                },
                function (error) {
                    console.log(error);
                    $scope.error = "Could not get data from server!";
                });

            $scope.deletePost = function () {
                console.log("DELETED");
                BlogPostService.deletePost($scope.post.id).then(function (response) {
                        $scope.post = response.data;
                        toastr.success('Post deleted successfully!');
                    },
                    function (error) {
                        console.log(error);
                        toastr.error('Could not delete post!');
                    });
            };
        }])

    .controller( 'BlogPostCtrlShow', ['$scope', '$stateParams',
        function ( $scope , $stateParams, BlogPostService) {

        }])

    .controller( 'BlogPostCtrlEdit', ['$scope', '$stateParams', 'toastr', 'BlogPostService',
        function ( $scope , $stateParams, toastr, BlogPostService) {
            $scope.submitUpdate = function () {

                var editedPost = $scope.post;
                if ($scope.fields.title !== undefined) {
                    editedPost.title = $scope.fields.title;
                }
                if ($scope.fields.content !== undefined) {
                    editedPost.content = $scope.fields.content;
                }

                BlogPostService.updatePost(editedPost).then(function (response) {
                        $scope.post = response.data;
                        toastr.success('Post updated successfully!');
                    },
                    function (error) {
                        console.log(error);
                        toastr.error('Could not update post!');
                    });
            };
        }]);