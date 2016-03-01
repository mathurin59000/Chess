angular.module('App').controller('NavController', function($scope, Auth, CurrentUser) {
	
	$scope.isCollapsed = true;
    $scope.auth = Auth;
    $scope.user = CurrentUser.user();
    
    $scope.logout = function() {
      Auth.logout();
      //windows.location.href="/";
    }
    
    if($scope.user.id&&$scope.user.token&&$scope.user.email&&$scope.user.username){
		  window.location.href="http://localhost:8080/ChessMaven2/#/messages";
	  }
	
 });