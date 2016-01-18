<html>
<head>
    <title>Plug Dj</title>

	<!--Init css-->
	<link href="css/bootstrap.css" rel="stylesheet">
	<link rel="stylesheet" href="css/bootstrap-theme.min.css">
	<link rel="stylesheet" href="css/font-awesome.min.css">
	
	<!--Init scripts-->
    <script src="js/dependencies/jquery-1.11.3.min.js"></script>
	<script src="js/dependencies/angular.min.js"></script>
	<script src="js/dependencies/angular-route.js"></script>
	<script src="js/dependencies/bootstrap.min.js"></script>
	<script src="js/app.js"></script>
	<script src="js/controllers/youtubeController.js"></script>
	<script src="js/controllers/messagesController.js"></script>
	<script src="js/controllers/navController.js"></script>
	<script src="js/controllers/registerController.js"></script>
	<script src="js/controllers/loginController.js"></script>
</head>
<body ng-app="App">
<nav class="navbar navbar-default" role="navigation" ng-controller="NavController">
   <div class="container-fluid">
     <!-- Brand and toggle get grouped for better mobile display -->
     <div class="navbar-header">
       <button type="button" class="navbar-toggle" ng-click="isCollapsed = !isCollapsed">
         <span class="sr-only">Toggle navigation</span>
         <span class="icon-bar"></span>
         <span class="icon-bar"></span>
         <span class="icon-bar"></span>
       </button>
       <a class="navbar-brand" href="#/">PlugDj v1.00</a>
     </div>

     <!-- Collect the nav links, forms, and other content for toggling -->
     <div class="collapse navbar-collapse" collapse="isCollapsed">
       <ul class="nav navbar-nav">
         <li ng-show="auth.isAuthenticated()"><a href="#/messages"><i class="fa fa-users"></i> La salle ma couille !!</a></li>
         <li ng-show="auth.isAuthenticated()"><a href="#/playlists"><i class="fa fa-tasks"></i> My playlists</a></li>
       </ul>
       <ul class="nav navbar-nav navbar-right">
         <li ng-hide="auth.isAuthenticated()"><a href="#/login">Login</a></li>
         <li ng-hide="auth.isAuthenticated()"><a href="#/register">Register</a></li>
         <li ng-hide="auth.isAuthenticated()"><p class="navbar-text">Hello, <strong>{{testMessages}}</strong></p></li>
         <li ng-show="auth.isAuthenticated()"><a href="#/" ng-click="logout()">Logout</a></li>
       </ul>
     </div><!-- /.navbar-collapse -->
   </div><!-- /.container-fluid -->
</nav>
<div ng-view></div>


</body>
</html>
