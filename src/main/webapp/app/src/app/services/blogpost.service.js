angular.module('blogPostService', [])
    .service('BlogPostService', function ($http) {
        this.getPosts = function (page) {
            return $http.get('/myblog/posts', {params: {page: page}});
        };
        this.getSelectedPost = function (id) {
            return $http.get('/myblog/post', {params: {id: id}});
        };
        this.createPost = function (post) {
            return $http.post('/myblog/insertpost', post);
        };
        this.updatePost = function (post) {
            return $http.put('/myblog/updatepost', post);
        };
        this.deletePost = function (id) {
            return $http['delete']('/myblog/deletepost', {params: {id: id}});
        };
    });