angular.module('App').controller('YoutubeController', function($scope, CurrentUser, YT_event) {
	
	$scope.yt = {
	    width: 720, 
	    height: 480, 
	    videoid: "m8Mx3nWwDF0",
	    playerStatus: "NOT PLAYING"
	  };

	  $scope.urls = [];
	  $scope.urlsFailed = [];
	  $scope.user = CurrentUser.user();
	  $scope.likes = 0;
	  $scope.unlikes = 0;
	  $scope.stopMode = false;
	  $scope.voted=false;
	  
	  $scope.$on(YT_event.STATUS_CHANGE, function(event, data) {
	      $scope.yt.playerStatus = data;
	      console.log("quelque chose vient de se passer !");
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
	        /*io.socket.delete('/url/addConv', {id: $scope.urls[0].id, url: $scope.urls[0].url, urlMinify: $scope.urls[0].urlMinify, email: $scope.urls[0].email});
	        //$scope.urls.splice(0, 1);
	        $scope.votes.forEach(function name(element, index, array){
	          io.socket.delete('/vote/addConv/',{id: element.id, vote: element.vote, email: element.email});
	        });*/
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
	      var item = {
	        url: $scope.newUrl,
	        urlMinify: urlMinify,
	        email: $scope.user.email,
	        time: 0
	      };
	      //io.socket.post('/url/addConv', {email: item.email, url: item.url, urlMinify: item.urlMinify, time: item.time});
	      $scope.newUrl = "";
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