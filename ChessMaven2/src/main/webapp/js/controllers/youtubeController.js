angular.module('App').controller('YoutubeController', function($scope) {
	
	$scope.urls=[];
	$scope.url = {
			url:"https://www.youtube.com/watch?v=yBwtAySu7Mg",
			email:"mathurin ramart"
	};
	$scope.urls.push($scope.url);
	var url2 = {
			url:"https://www.youtube.com/watch?v=1b0Eh4iELqQ",
			email:"arnaud tricard"
	};
	$scope.urls.push(url2);	
	
  });