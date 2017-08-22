angular.module('loginService', [])
    .service('LoginService', function ($http) {

        var isLogedInFlag = false;

        this.login = function (credentials) {
            return $http.post("/login", credentials);
        };

        this.isLogedIn = function () {
            return isLogedInFlag;
        };
    });