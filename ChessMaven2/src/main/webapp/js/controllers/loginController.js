angular.module('App').controller('LoginController', function($scope) {
	$scope.errors = [];

    $scope.login = function() {
      $scope.errors = [];
    }
  });