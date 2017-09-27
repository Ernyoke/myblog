angular.module( 'composeModule', [
    'ui.router',
    'toastr'
])
    .config(function config( $stateProvider ) {
        $stateProvider.state( 'compose', {
            url: '/compose',
            views: {
                "main": {
                    controller: 'ComposeCtrl',
                    templateUrl: 'compose/compose.tpl.html'
                }
            },
            data:{ pageTitle: 'Compose' }
        });
    })
    .controller( 'ComposeCtrl', function ComposeController( $scope, toastr, BlogPostService) {
        $scope.submitBlogPost = function () {
            BlogPostService.createPost($scope.fields).then(function (response) {
                    toastr.success('Post created successfully!');
                    console.log(response);
                },
                function (error) {
                    console.log(error);
                    toastr.error('Could not create post!');
                });
        };
    });