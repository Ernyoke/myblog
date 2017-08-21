angular.module('blogPostService', [])
    .service('BlogPostService', function ($http) {
        this.getAllPosts = function () {
            return $http.get('/myblog/posts');
        };
        this.getSelectedPost = function (id) {
            return $http.get('/myblog/post?id=' +  id);
        };
        this.createPost = function (post) {
            return $http.post('/myblog/insertpost', post);
        };
        this.updatePost = function (post) {
            return $http.put('/myblog/updatepost', post);
        };
        this.deletePost = function (id) {
            //return $http.delete('/myblog/deletepost', id);
        };
    });