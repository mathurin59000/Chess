angular.module('App').controller('YoutubeController', function($scope) {
	
	$scope.urls=[];
	$scope.url = {
			url:"test.fr",
			email:"mathurin ramart"
	};
	$scope.urls.push($scope.url);
	var url2 = {
			url:"test2.fr",
			email:"arnaud tricard"
	};
	$scope.urls.push(url2);	
	
	$scope.clickBoutonRouge = function(){
		alert("Clic sur le bouton rouge !");
	};
  });