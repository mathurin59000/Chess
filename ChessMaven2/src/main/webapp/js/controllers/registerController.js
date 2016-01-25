angular.module('App').controller('RegisterController', function($scope, $http, LocalService) {
	$scope.errors = [];
	
	$scope.register = function() {
		$scope.errors = [];
	      $http({
	    	  method: 'POST',
	    	  url: '/ChessMaven2/register',
	    	  params: {username: $scope.user.username, email: $scope.user.email, password: $scope.user.password, confirmedPassword: $scope.user.confirmedPassword}
	    	}).then(function successCallback(response) {
	    		if(response.data){
	    			if(response.data.error){
	    				$scope.errors.push(response.data.error);
	    			}
	    			else{
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