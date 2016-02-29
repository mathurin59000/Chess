angular.module('App').controller('MessagesController', function($scope, CurrentUser) {
	
	var user = CurrentUser.user();
	$scope.messages = [];
	var socket = new WebSocket("ws://localhost:8080/ChessMaven2/chat");
	
	socket.onopen=function(message){
		console.log(message);
	};
	
	
	socket.onmessage = function(message){
		var json = {
				username: message.data.split("/", 2)[0],
				data: message.data.split("/", 2)[1]
		};
		$scope.messages.push(json);
		$scope.$apply();
	};
	
	$scope.addMessage = function(){
		if($scope.message.length > 0 && typeof($scope.message)=="string"){
			var mess = user.username+"/"+$scope.message;
			socket.send(mess);
			var json = {
					username: user.username,
					data: $scope.message
			};
			$scope.messages.push(json);
			$scope.message="";
		}
	};
	
	socket.onclose = function(e){
		socket.close();
	};
	
	
 });