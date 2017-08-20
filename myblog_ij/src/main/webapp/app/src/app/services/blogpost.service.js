angular.module('blogPostService', [])
    .service('BlogPostService', function ($http) {
        this.getAllPosts = function () {
            return $http.get('/myblog/posts');
        };
        this.getSelectedPost = function (id) {
            return $http.get('/myblog/post?id=' +  id);
        };
        this.createPost = function (fields) {
            return $http.post('/myblog/insertpost', fields);
        };
    });