	app.controller("searchCtrl", ['$http','AuthenticationService','$scope','$location', '$window', function($http,AuthenticationService, $scope, $location, $window) {
		

		var url1 = $location.absUrl().split('3ast3r3gg=')[1];
		console.log(url1);
		
		
		$scope.search = function(string){
			if(string!='')
			if(string.trim() != ''){
				var ur = "http://localhost:8080/forum161/rest/services/search";
				$http({
					method:'POST',
					url: ur,
					data : string, 
					}).then(function successCallback(response) {
						$scope.searchResult= response.data;
						console.log(response.data);
						  }, function errorCallback() {
							  console.log("Greska kod searcha");
							  location.reload();
						  });	
			}
		}
		
		$scope.search(url1);
		
		
		
		$scope.goToSubForum = function(subForum) {
			
			var ur = "http://localhost:8080/forum161/rest/services/SubForum/" +subForum.name;
			$http({
				method:'GET',
				url: ur,
				params:{
					"subForumID" : subForum.name
				}
				}).then(function successCallback() {
					$window.location.href = "/forum161/start/subForum.html?SubForumName="+subForum.name;
						
					  }, function errorCallback() {
						  console.log("Greska kod premestanja");
						  location.reload();
					  });	
		}
		
		
		$scope.goToTheme = function(theme) {
				
				var ur = "http://localhost:8080/forum161/rest/services/Theme/" +theme.id;
				$http({
					method:'GET',
					url: ur, 
					params: {"subForumID": theme.subForum, "themeID" : theme.id  },
					}).then(function successCallback() {
						$window.location.href = ur;
						$window.location.href = "/forum161/start/theme.html?SubForumName="+theme.subForum+"&ThemeID="+theme.id;
							
						  }, function errorCallback() {
							  console.log("Greska kod premestanja");
							  location.reload();
						  });
				
			
			
				}
		
		
	}]);