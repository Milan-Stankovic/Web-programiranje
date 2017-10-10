(function(angular) {
	
	
	app.directive('ngConfirmClick', [
	    function(){
	        return {
	            link: function (scope, element, attr) {
	                var msg = attr.ngConfirmClick || "Are you sure?";
	                var clickAction = attr.confirmedClick;
	                element.bind('click',function (event) {
	                    if ( window.confirm(msg) ) {
	                        scope.$eval(clickAction)
	                    }
	                });
	            }
	        };
	}])
	
	
	app.controller("loginCtrl", ['$http','AuthenticationService','$scope','$window', function($http,AuthenticationService, $scope, $window) {
		
		//console.log("U novom login ctrlu sam");
		
		$scope.searchResult = {};
		$scope.search = function(string){
			
			$window.location.href = "/forum161/start/search.html?3ast3r3gg="+string;
			
			
		}
		
		$scope.logedIn = false ;
		$scope.isAdmin= false;
		$scope.isModerator = false;
		$scope.isModOrAdm = false;
		$scope.clickedAddForum = false;
		$scope.user = AuthenticationService.getCurrentUser();
		$scope.theFile = null;
		$scope.addedSub = false;
		$scope.errorOnAddSub = false;
		// console.log("Pa sta mi je user ? " + $scope.user.firstName);

		if($scope.user){
			$scope.logedIn = true ;
	
		//	console.log("Provera za login " + $scope.user.role);
			if($scope.user.role === "ADMIN"){
				
				$scope.isAdmin= true;
				$scope.isModOrAdm = true;
			//	console.log("Okej znas da sam admin" + $scope.isModOrAdm);
				
			}
			if($scope.user.role === "MODERATOR"){
				
				$scope.isModerator= true;
				$scope.isModOrAdm = true;
			}
		}
		
		

		var goodType = function(filename){
			
			var ext = filename.substr(filename.lastIndexOf('.')+1);
			switch (ext) {
			case "jpg":
				return true;
				
			case "JPG":
				return true;
				
			case "png":
				return true;
				
			case "PNG" :
				return true;

			default:
				return false;
			}
		}
		
		
	
		

		$scope.editSubForum = function(subForum){
			console.log("upao u edit");
			subForum.edit = !subForum.edit;
			//$scope.editThemeON = !$scope.editThemeON;
			
		}
		
		
		$scope.logout = function() {
			 console.log("Upao u logout");
			AuthenticationService.logout();
		}
		
		
		$scope.uploadFile = function(files) {
		  //  var fd = new FormData();
		    //Take the first selected file
		    //fd.append("file", files[0]);
		    
		    $scope.subForumIcon= files[0];
		    //console.log("Os li uploadovati ?");
		   // console.log(files[0]);
		 /*   $http.post(uploadUrl, fd, {
		        withCredentials: true,
		        headers: {'Content-Type': undefined },
		        transformRequest: angular.identity
		    }).success( ...all right!... ).error( ..damn!... );
		    */
		  /*  $http({
				  method: 'POST',
				  url: 'http://localhost:8080/forum161/rest/services/register',
				  data: user
				}).then(function successCallback(response) {
				
					 $scope.greskaRegistracija = false;
					 $scope.uspesnaRegistracija = true;
					
				  }, function errorCallback(response) {
					  console.log("Greska kod registracije ");
					  $scope.greskaRegistracija =true;
					  $scope.uspesnaRegistracija = false;
				  });
			*/
		};
		
		//console.log("U login ctrl-u");
		
		$scope.login = function(korisnickoIme, lozinka) {
			AuthenticationService.login(korisnickoIme, lozinka,
				function(uspeo) {
				//	console.log(uspeo);
					$scope.logedIn = true;
					//console.log("Radi li ovo uopste ? ");
					//console.log("scope ? " + $scope.logedIn);
					
			//		console.log("U login ctrl-u");
					
				 var user = AuthenticationService.getCurrentUser();
		//		 console.log(user);	
				 
				 switch (user.role) {
 				case "REGULAR":
 					console.log("user ? " + user.role);
 					break;
 				case "ADMIN":
 					$scope.isAdmin = true;
 					$scope.isModerator = false;
 					$scope.isModOrAdm = true;
 			//		console.log("Admin ? " + $scope.isModOrAdm);
 					
 					break;
 				case "MODERATOR":
 					$scope.isModerator = true;
 					$scope.isAdmin= false;
 					$scope.isModOrAdm = true;
 					break;
				 }
				 location.reload();
				});
		};
		
		
	}]);
	
})(angular);