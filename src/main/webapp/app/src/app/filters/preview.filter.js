angular.module('previewFilter', [])
    .filter('preview', function () {
        return function (text) {
            var chars = 200;
            if (text !== undefined) {
                return text.slice(0, chars) + '...';
            }
            return text;
        };
    });