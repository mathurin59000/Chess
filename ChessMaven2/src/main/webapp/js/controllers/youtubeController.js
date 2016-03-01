angular.module('App').controller('YoutubeController', function($scope, CurrentUser, YT_event) {
	var socketVote = new WebSocket("ws://localhost:8080/ChessMaven2/vote");
	var votes = [];
	socketVote.onopen=function(message){
		console.log(message);
	};
	
	$scope.sendControlEvent = function(ctrlEvent){
		this.$broadcast(ctrlEvent);
	}
	
	socketVote.onmessage = function(message){
		if(message.data.split("/", 2)[1]=="+1"){
			$scope.likes++;
			$scope.$apply();
		}
		else if(message.data.split("/", 2)[1]=="-1"){
			$scope.unlikes++;
			$scope.$apply();
		}
		var json = {
				email: message.data.split("/", 2)[0],
				data: message.data.split("/", 2)[1]
		};
		votes.push(json);
	};
	
	$scope.addVote = function(vote){
		    var ok=true;
			votes.some(function name(element, index, array){
				if(element.email==$scope.user.email){
					ok=false
					return true;
				}
			});
			if(ok){
				if(vote=="+1"){
					$scope.likes++;
				}
				else if(vote=="-1"){
					$scope.unlikes++;
				}
				var mess = $scope.user.email+"/"+vote;
				socketVote.send(mess);
				var json = {
						email: $scope.user.email,
						data: vote
				};
				votes.push(json);
			}
	};
	
	socketVote.onclose = function(e){
		socketVote.close();
	};
	
	//--------------------------------------------------------------------------------------------------
	
	var socketUrl = new WebSocket("ws://localhost:8080/ChessMaven2/url");
	socketUrl.onopen=function(message){
		console.log(message);
	};
	var started = false;
	
	socketUrl.onmessage = function(message){
		console.log(message.data);
		if(message.data.indexOf("error")<=0){
			if(message.data.indexOf("delete")<0){
				console.log(message.data);
				var tab = message.data.split("|");
				for(var i=0;i<tab.length;i++){
					var json = JSON.parse(tab[i]);
					$scope.urls.push(json);
				}
				console.log(tab);
				$scope.$apply();
				if ($scope.urls.length > 0 && !started){
					$scope.yt.videoid=$scope.urls[0].urlMinify;
					started = true;
					$scope.sendControlEvent(1);
					$scope.$apply();
				}
			}
			else{
				console.log("delete detected");
				var tab = message.data.split("!");
				for(var i=0;i<tab.length;i++){
					console.log(tab[i]);
				}
				var json = JSON.parse(tab[1]);
				$scope.urls.some(function name(element, index, array){
					if(element.id==json.id){
						$scope.urls.splice(index, 1);
						return true;
					}
				});
				console.log("reception d'une url à supprimer :");
				console.log($scope.urls);
			}
			
		}
		
	};
			
	
	socketUrl.onclose = function(e){
		socketUrl.close();
	};
	
	//---------------------------------------------------------------------------------------
	
	$scope.yt = {
	    width: 545, 
	    height: 370, 
	    videoid: "",
	    playerStatus: "NOT PLAYING"
	  };

	  $scope.urls = [];
	  $scope.urlsFailed = [];
	  $scope.user = CurrentUser.user();
	  console.log("user");
	  console.log($scope.user);
	  if(!$scope.user.id||!$scope.user.token||!$scope.user.email||!$scope.user.username){
		  window.location.href="http://localhost:8080/ChessMaven2/#/login";
	  }
	  $scope.likes = 0;
	  $scope.unlikes = 0;
	  $scope.stopMode = false;
	  $scope.voted=false;
	  
	  $scope.$on(YT_event.STATUS_CHANGE, function(event, data) {
	      $scope.yt.playerStatus = data;
	      console.log("something appenned !");
	      console.log(data);
	      if(data=="NOT PLAYING"){
	        if($scope.stopMode == true){
	          $scope.stopMode = false;
	        }
	        else if($scope.urls[0]){
	          $scope.yt.videoid = $scope.urls[0].urlMinify;
	          console.log($scope.yt);
	          $scope.sendControlEvent(1);
	        }
	      }
	      if(data==""){
	        $scope.sendControlEvent(1);
	      }
	      if(data=="ENDED"){
	        console.log(typeof $scope.urls);
	        console.log($scope.yt);
	        if($scope.yt.videoid==$scope.urls[0].urlMinify){
	        	socketUrl.send("delete:id="+$scope.urls[0].id);
		        $scope.urls.shift();
	        }
	        $scope.likes = 0;
	        $scope.unlikes = 0;
	        if($scope.urls[0]){
	          $scope.yt.videoid = $scope.urls[0].urlMinify;
	          console.log($scope.yt);
	          $scope.sendControlEvent(1);
	        }
	      }
	  });
	  
	  $scope.add = function(){
	      console.log("ON AJOUTE");
	      var urlMinify="";
	      if($scope.newUrl){
	        if(typeof $scope.newUrl != "undefined"){
	          if(typeof $scope.newUrl == "string"){
	            if ($scope.newUrl.indexOf('youtube')>0) {
	              if($scope.newUrl.indexOf('v=')>0){
	                var tab = $scope.newUrl.split('v=');
	                if(tab[1].indexOf("&")){
	                  var tab2 = tab[1].split('&');
	                  urlMinify=tab2[0];
	                }
	                else{
	                  urlMinify = tab[1];
	                }
	              }
	            }
	          }
	        }
	      }
	      var mess = "id="+$scope.user.id+"&username="+$scope.user.username+"&url="+$scope.newUrl+"&urlMinify="+urlMinify;
	      socketUrl.send(mess);
	      /*var item = {
	    		  id: "",
	    		  username: $scope.user.username,
	    		  url: $scope.newUrl,
	    		  urlMinify: urlMinify
	      };*/
	      //$scope.urls.push(item);
	      $scope.newUrl = "";
	      $scope.sendControlEvent(1);
	    };

	    $scope.remove = function(item){
	      console.log("Not implemented...")
	    }

	  $scope.YT_event = YT_event;
	  
	  $scope.refresh = function(){
		    console.log("Not implemented...");
	  }

	  $scope.stopDisplay = function(){
	    console.log("Not implemented...");
	    $scope.sendControlEvent(0);
	    $scope.stopMode = true;
	  }

	  $scope.resetList = function(){
	    /*$scope.urls.forEach(function name(element, index, array){
	      io.socket.delete('/url/addConv/',{id: element.id, url: element.url, urlMinify: element.urlMinify, email: element.email});
	    });
	    $scope.votes.forEach(function name(element, index, array){
	      io.socket.delete('/vote/addConv/',{id: element.id, vote: element.vote, email: element.email});
	    });*/
	    $scope.likes = 0;
	    $scope.unlikes = 0;
	    $scope.sendControlEvent(0);
	  }

	  $scope.pass = function(){
	    console.log("Not implemented...");
	    //$scope.sendControlEvent(0);
	    /*if($scope.urls.length>0){
	      io.socket.delete('/url/addConv/',{id: $scope.urls[0].id, url: $scope.urls[0].url, urlMinify: $scope.urls[0].urlMinify, email: $scope.urls[0].email});
	    }
	    $scope.votes.forEach(function name(element, index, array){
	      io.socket.delete('/vote/addConv/',{id: element.id, vote: element.vote, email: element.email});
	    });
	    $scope.likes = 0;
	    $scope.unlikes = 0;*/
	  }
	
  });