angular.module('App').controller('LoginController', function($scope, $http, LocalService) {
	$scope.errors = [];

    $scope.login = function() {
      $scope.errors = [];
      $http({
    	  method: 'POST',
    	  url: '/ChessMaven2/auth',
    	  params: {email: $scope.user.email, password: $scope.user.password}
    	}).then(function successCallback(response) {
    		if(response.data){
    			if(response.data.error){
    				$scope.errors.push(response.data.error);
    				console.log($scope.errors);
    			}
    			else{
    				console.log(response.data);
    				LocalService.set('auth_token', JSON.stringify(response.data));
    				window.location.href = "http://localhost:8080/ChessMaven2/#/messages";
    			}
    		}
    		else{
    			
    		}
    	  }, function errorCallback(response) {
    		  console.log("error :");
    		  console.log(response);
    	  });
    }
  });