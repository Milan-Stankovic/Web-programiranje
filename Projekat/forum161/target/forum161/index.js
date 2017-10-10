(function(angular) {
  var app = angular.module("app",['ui.router', 'checklist-model', 'authentication']);
  app.run(run);
		  
 // console.log("TEST");

  function run($rootScope, $http, $location, $localStorage, AuthenticationService, $state) {
      // postavljanje tokena nakon refresh
	  
	 // console.log("RUN TEST");
	  
      if ($localStorage.currentUser) {
         $http.defaults.headers.common.Authorization = $localStorage.currentUser.token;
     }
  }
  
  
  app.controller('nalogCtrl', ['$rootScope', '$scope', '$http', function($rootScope, $scope, $http) {
	  
		 
		  $rootScope.logout = function () {
	          AuthenticationService.logout();
	      }
	      $rootScope.getCurrentUserRole = function () {
	          if (!AuthenticationService.getCurrentUser()){
	            return undefined;
	          }
	          else{
	            return AuthenticationService.getCurrentUser().role;
	          }
	      }
	      
	      $rootScope.getCurrentUser = function () {
	          if (!AuthenticationService.getCurrentUser()){
	            return undefined;
	          }
	          else{
	            return AuthenticationService.getCurrentUser();
	          }
	      }
	      
	      $rootScope.isLoggedIn = function () {
	          if (AuthenticationService.getCurrentUser()){
	            return true;
	          }
	          else{
	            return false;
	          }
	      }
		  
		  
	  
	 
	      }]);
  
  
	 
  
      
     
     
  
})(angular);
