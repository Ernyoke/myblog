angular.module('authenticationService', ['ngStorage'])
    .service('LoginService', function ($http, $localStorage) {
        $localStorage.isLogedIn = false;
        this.login = function (credentials, successHandler, failureHandler) {
            console.log(credentials);
            if (credentials !== undefined) {
                $http.post('/myblog/login', credentials).then(function (response) {
                    $localStorage.isLogedIn = true;
                    successHandler(response);
                }, function (error) {
                    $localStorage.isLogedIn = false;
                    failureHandler(error);
                });
                
            }
            else {
                $localStorage.isLogedIn = false;
                failureHandler();
            }
        };

        this.logout = function (successHandler, failureHandler) {
            console.log("logout_init");
            $http.post('/myblog/logout').then(function (response) {
                console.log("logout_success");
                $localStorage.isLogedIn = false;
                successHandler(response);
            }, function (error) {
                console.log("logout_error");
                failureHandler(error);
            });
        };

        this.isLogedIn = function () {
            return $localStorage.isLogedIn;
        };
    });