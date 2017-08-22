angular.module('authenticationService', [])
    .service('LoginService', function ($http) {

        var isLogedInFlag = false;

        this.login = function (credentials) {
            isLogedInFlag = true;
            return $http.post('/login', credentials);
        };

        this.logout = function () {
            isLogedInFlag = false;
            return $http.post('/logout');
        };

        this.isLogedIn = function () {
            return isLogedInFlag;
        };
    });