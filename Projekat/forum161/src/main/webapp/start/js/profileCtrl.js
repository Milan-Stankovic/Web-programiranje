	app.controller("profileCtrl", ['AuthenticationService','$scope', '$http', '$location','$window', function(AuthenticationService,$scope, $http, $location, $window) {
		
		$scope.user = AuthenticationService.getCurrentUser();
		$scope.editActivate = false;
		$scope.confirmedPass=""
		$scope.editFail = false;
		$scope.passwordStars = "";
		$scope.passwordStars = $scope.user.password.replace(/./gi, '*');
		$scope.userControl = false;
		$scope.addAdmin = false;
		$scope.addModerator = false;
		$scope.modName = '';
		$scope.adminName ='';
		$scope.removeName='';
		$scope.removeTitleName='';
		$scope.removeUser=false;
		$scope.removeTitle=false;
		
		$scope.userControlActivate = function(){
			$scope.userControl = !$scope.userControl;
			$scope.modName = '';
			$scope.adminName ='';
			$scope.removeName='';
			$scope.removeTitleName='';
			
		}
		
		$scope.addAdminActivate = function(){
			$scope.addAdmin = !$scope.addAdmin;
			$scope.adminName ='';
			
		}
		
		$scope.removeUserActivate = function(){
			$scope.removeUser = !$scope.removeUser;
			$scope.removeName ='';
			
		}
		
		$scope.removeTitleActivate = function(){
			$scope.removeTitle = !$scope.removeTitle;
			$scope.removeTitleName ='';
			
		}
		
		
		$scope.addModeratorActivate = function(){
			$scope.addModerator = !$scope.addModerator;
			$scope.modName = '';
		}

		$scope.editProfile = function(){
			$scope.editActivate = !$scope.editActivate;
			$scope.editFail=false;
			$scope.removeUser=false;
			$scope.removeTitle=false;
			$scope.addAdmin = false;
			$scope.addModerator = false;
			$scope.modName = '';
			$scope.adminName ='';
			$scope.removeName='';
			$scope.removeTitleName='';
		}
		
		$scope.saveAddAdmin = function(adminName){
			
			if(adminName){
				$http({
					method:'PUT',
					url: "http://localhost:8080/forum161/rest/services/addAdmin",
					params : {"userID" : adminName}
					}).then(function successCallback(response){
					//	console.log("Uspeo");
						$scope.addAdminActivate();
								
					}, function errorCallback(response){
						console.log("Nesto je lose kod edita usera ");
						
					})

			}
		}
		
		
		$scope.saveAddMod = function(modName){
			
			if(modName){
				$http({
					method:'PUT',
					url: "http://localhost:8080/forum161/rest/services/addMod",
					params : {"userID" : modName}
					}).then(function successCallback(response){
					//	console.log("Uspeo");
						$scope.addModeratorActivate();
								
					}, function errorCallback(response){
						console.log("Nesto je lose kod edita usera ");
						
					})

			}
		}
		
		$scope.saveRemoveUser = function(removeName){
			if(removeName){
				$http({
					method:'PUT',
					url: "http://localhost:8080/forum161/rest/services/removeUser",
					params : {"userID" : removeName}
					}).then(function successCallback(response){
					//	console.log("Uspeo");
						$scope.removeUserActivate();
								
					}, function errorCallback(response){
						console.log("Nesto je lose kod edita usera ");
						
					})

			}
		}
		


	$scope.saveRemoveTitle = function(removeTitleName){
	
		if(removeTitleName){
			$http({
				method:'PUT',
				url: "http://localhost:8080/forum161/rest/services/removeTitle",
				params : {"userID" : removeTitleName}
				}).then(function successCallback(response){
					//	console.log("Uspeo");
					$scope.removeTitleActivate();
						
				}, function errorCallback(response){
					console.log("Nesto je lose kod edita usera ");
				
				})

		}
	}

		
		
		
		
		$scope.saveEditProfile = function(){
			//console.log("U edit profile sam");
			//console.log($scope.user);
			//console.log($scope.confirmedPass);
			if($scope.confirmedPass === $scope.user.password){
				//console.log("ISTI SU :D");
				var user=
				  {
					    "email": $scope.user.email,
					    "firstName": $scope.user.firstname,
					    "lastName": $scope.user.lastname,
					    "password": $scope.password,
					    "phone": $scope.phonenumber,
					    "username": $scope.username
				  };
				
				$http({
						method:'POST',
						url: "http://localhost:8080/forum161/rest/services/editUser",
						data: user
						}).then(function successCallback(response){
						//	console.log("Uspeo");
							$scope.editProfile();
							$editFail = false;
									
						}, function errorCallback(response){
							console.log("Nesto je lose kod edita usera ");
							
						})
	
			}else{
				$scope.editFail = true;
			}
		}
		
		
	}]);
	
	