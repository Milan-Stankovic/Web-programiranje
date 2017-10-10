	app.controller("subForumCtrl", ['AuthenticationService','$scope', '$http', '$location','$window', function(AuthenticationService,$scope, $http, $location, $window) {
		
	//	$scope.user= {}
		
		$scope.subForumi=[];
		$scope.themes=[];
		$scope.user = AuthenticationService.getCurrentUser();
		
		
		//if($scope.user.role === "ADMIN")
		//	console.log("admin je 2");
		
		
		$scope.editSubForum = function(subForum){
			//console.log("upao u edit");
			subForum.edit = !subForum.edit;
			//$scope.editThemeON = !$scope.editThemeON;
			
		}
		
		
		$scope.flagSubForum = function(subForum, text) {
		
			var data ={
					
					"message" : text,
					"subForum" : subForum,
					"theme" : null,
					"comment" : null
					
			};
			var ur = "http://localhost:8080/forum161/rest/services/flagSubForum";
			$http({
				method:'POST',
				url: ur,
				data : data, 
				params : {"userID" : $scope.user.username}
				}).then(function successCallback() {
						$scope.flagActivate(subForum);
						$scope.flagText='';
					  }, function errorCallback() {
						  console.log("Greska kod premestanja");
						  location.reload();
					  });	
		}
		
		$scope.flagActivate = function(subForum){
			subForum.flag = !subForum.flag;
		}
		
		$scope.flagActivateMainSub = function(){
			$scope.subForum.flag = !$scope.subForum.flag;
		}
		
		
		
	
		
		
		//console.log("Zasto sada ovo ne koristis ????");
		
		$scope.goToSubForum = function(subForum) {
		
			var ur = "http://localhost:8080/forum161/rest/services/SubForum/" +subForum.name;
			$http({
				method:'GET',
				url: ur,
				params:{
					"subForumID" : subForum.name
				}
				}).then(function successCallback() {
				//	$window.location.href = ur;
					$window.location.href = "/forum161/start/subForum.html?SubForumName="+subForum.name;
					//?SubForumName="+theme.subForum+"&ThemeID="+theme.id
					//	console.log("Dobio subove");
				//		console.log("Uspeo je :D ");
				//		console.log(ur);
				//		console.log($location);
				//		console.log($window.location.href);
						//location.reload();
						
					  }, function errorCallback() {
						  console.log("Greska kod premestanja");
						  location.reload();
					  });	
			
		
			
			
		}
		
		
		$scope.addSubForumBttn = function(){
			
			//	console.log("Admin u dugmetu ? " + $scope.isModOrAdm);
				
				$scope.user = AuthenticationService.getCurrentUser();
				 if($scope.user.role ==="ADMIN" || $scope.user.role === "MODERATOR"){
					 
					 //console.log("Kliknuto ");
					 
					// console.log($scope.clickedAddForum);
					 if($scope.clickedAddForum){
						// console.log("ULAZIM LI TUUUU ?");
						 $scope.clickedAddForum = false;
						 	$scope.newForumName = "";
							$scope.newForumDescription = "";  
							$scope.newForumRules = "";

					 }else
					$scope.clickedAddForum = true;
						$scope.newForumName='';
						$scope.newForumDescription='';
						$scope.newForumRules='';
				 }
				
			}
		
		

		
		
		
		$scope.addSubForum = function(newForumName,newForumDescription, newForumRules ){
			var b= false;
			if(newForumName)
				if(newForumDescription)
					if(newForumRules)
					//	if($scope.theFile)
						//	if($scope.theFile.size<2100000)
							//	if(goodType($scope.theFile.name))
									b=true;
			b=true;
			//console.log($scope.theFile);	
			if(b){
				
			//	console.log($scope.theFile);
				
			//	console.log($scope.user);
	
				var user=
				  {
					    "email": $scope.user.email,
					    "firstName": $scope.user.firstName,
					    "lastName": $scope.user.lastName,
					    "password": $scope.user.password,
					    "phone": $scope.user.phone,
					    "username": $scope.user.username,
					    "registrationDate": $scope.user.registrationDate,
					    "role": $scope.user.role
				  };
				
				var subforum = {
						"description": newForumDescription,
					    "icon":  null,
					    "name": newForumName,
					    "rules": newForumRules,
					    "moderators" : null,
					    "mainModerator": user
				}
					
				
			//	console.log(subforum);
					$http({
						method:'POST',
						url: 'http://localhost:8080/forum161/rest/services/addSubForum',
						data: subforum
						}).then(function successCallback(response){
							$scope.newForumName='';
							$scope.newForumDescription='';
							$scope.newForumRules='';
							//console.log("GG ");
							$scope.addedSub = true;
							$scope.errorOnAddSub = false;
							console.log(response.data);
							console.log($scope.subForumi);
							$scope.subForumi.push(response.data);
							$scope.addSubForumBttn();
							
							//location.reload();
							
					
							
						}, function errorCallback(response){
							console.log("Nesto je lose uneto kod addSubForm - index2.js ");
							$scope.addedSub = false;
							$scope.errorOnAddSub = true;
							//location.reload();
							
							
							
						} );
			}else{
			 console.log("Nesto je lose uneto kod addSubForm - index2.js ");
				$scope.addedSub = false;
				$scope.errorOnAddSub = true;
			
			}
			
		}
		
		
		
		$scope.saveEditSubForum = function(subForum){
			
			console.log("U edit subforum :D ");
			
			$http({
				method:'POST',
				url: "http://localhost:8080/forum161/rest/services/editSubForum",
				data: subForum
				}).then(function successCallback(response){
					console.log("Kao uspeo edit :D ");
					subForum.edit = true;
			//		location.reload();
							
				}, function errorCallback(response){
					console.log("Nesto je lose kod edita subForuma ");
					
				})
			
			
			
		}
		
		
		

		
		var url = $location.absUrl().split('SubForumName=')[1];
		
		$scope.subForum;
		
		
		//console.log($scope.user);
		
		var getOneSubForum = function(u){
			
			console.log("Uzimam subforum za dodavanje");
			console.log(u);
			
			var ur = "http://localhost:8080/forum161/rest/services/getOneSubForum";
			$http({
				method:'GET',
				url: ur,
				params : {"subForumID" : u}
				}).then(function successCallback(response){
					
					$scope.subForum = response.data;
			//		console.log($scope.subForum);
					
				}, function errorCallback(response){
					console.log("Nesto je lose uneto kod dobijanja jednog suba ");
					
				})
			
		};
		if(url)
		getOneSubForum(url);
		
		
		
		
		
		
		
	//	console.log("Upad kod GET subForums");
		
		$scope.subForumi={};

		//console.log($scope.subForumi);
		$scope.user = AuthenticationService.getCurrentUser();
		
		
		$scope.subForum;
		
		
		
		
		
	
		
		
		var getSubForumi = function() {
		//	console.log("Trazi subove");
			$http({
				  method: 'GET',
				  url: 'http://localhost:8080/forum161/rest/services/allSubForums'
				}).then(function successCallback(response) {
					$scope.subForumi = response.data;
					
				//	console.log("Dobio subove");
				//	console.log($scope.subForumi);
				
					
				  }, function errorCallback(response) {
					  console.log("Greska kod GET subForums");
				  });
			
		}
		getSubForumi();
		
		
		
		$scope.followSub = function(subForum){
			var ur = "http://localhost:8080/forum161/rest/services/addFavSubForum/";
			
			console.log("U follow sub");
			
			var user=
			  {
				    "email": $scope.user.email,
				    "firstName": $scope.user.firstName,
				    "lastName": $scope.user.lastName,
				    "password": $scope.user.password,
				    "phone": $scope.user.phone,
				    "username": $scope.user.username,
				    "registrationDate": $scope.user.registrationDate,
				    "role": $scope.user.role
			  };
			
			$http({
				  method: 'PUT',
				  url: ur,
				  data : user,
				  params : {
					  "subForumID" :  subForum.name
				  }
				}).then(function successCallback() {
					
		
					console.log("Navodno dodao u fav");
				//	console.log("Obrisao SubForum");
				//	location.reload();
					
				  }, function errorCallback() {
					  console.log("Greska kod brisanja subForums");
					  location.reload();
				  });
			
		}
		
		
		$scope.removeSub = function(subForum){
			
			var ur = "http://localhost:8080/forum161/rest/services/RemoveSubForum/" + subForum.name;
			
			$http({
				  method: 'DELETE',
				  url: ur
				}).then(function successCallback() {
					
		
				//	console.log("Dobio subove");
				//	console.log("Obrisao SubForum");
				
					 var index = $scope.subForumi.indexOf(subForum);
					$scope.subForumi.splice(index,1);
				//	console.log("Testiram ovaj splice :D")
					
					
					//location.reload();
					
				  }, function errorCallback() {
					  console.log("Greska kod brisanja subForums");
					  location.reload();
				  });
			
		}
		
		
		
		$scope.removeCurrentSub = function(){
			
			if($scope.subForum.name){
			
			var ur = "http://localhost:8080/forum161/rest/services/RemoveSubForum/" + $scope.subForum.name;
			
			$http({
				  method: 'DELETE',
				  url: ur
				}).then(function successCallback() {
					
		
				//	console.log("Dobio subove");
				//	console.log("Obrisao SubForum");
					
					$window.location.href = "/forum161/start/";
					
				  }, function errorCallback() {
					  console.log("Greska kod brisanja subForums");
					  location.reload();
				  });
			}
		}
		
	
	
	}]);
	
	
	
app.controller("showThemesCtrl", ['AuthenticationService','$scope', '$http','$location','$window', function(AuthenticationService,$scope, $http, $location, $window) {
		
		$scope.user = AuthenticationService.getCurrentUser();
		console.log(" U DOBROM CONTROLERU SAM");
		
		$scope.editThemeON = true;
		$scope.isCurrentMod= false;
		$scope.themes=[];
		$scope.clickedAddTheme = false;
		
		$scope.themeTypes = ["TEXT","IMAGE","LINK"];
		$scope.selectedType;
		$scope.newThemeContent = {text: ""}
		
		$scope.flagActivateTheme = function(theme){
			theme.flag = !theme.flag;
		}
		
		
		
		$scope.flagTheme = function(theme, text) {
			
			var data ={
					
					"message" : text,
					"subForum" : $scope.subForum,
					"theme" : theme,
					"comment" : null
					
			};
			var ur = "http://localhost:8080/forum161/rest/services/flagTheme";
			$http({
				method:'POST',
				url: ur,
				data : data, 
				params : {"userID" : $scope.user.username}
				}).then(function successCallback() {
						$scope.flagTextT='';
						$scope.flagActivateTheme(theme);
					  }, function errorCallback() {
						  console.log("Greska kod premestanja");
						  location.reload();
					  });	
		}
		
		
		
		
		$scope.addThemeBttn = function(){
			
			$scope.clickedAddTheme  = ! $scope.clickedAddTheme;
		
			
		}
		
		$scope.addTheme = function(newThemeTitle, newThemeContent, selectedType ){
			var b= false;
			if(newThemeTitle)
				if($scope.newThemeContent)
					if(	$scope.user.username)
							b=true;
			b=true;
		//	console.log("dodaje temu : " + b);	
			if(b){
				
			//	console.log($scope.theFile);
				
			//	console.log($scope.newThemeContent.text);
	
				var user=
				  {
					    "email": $scope.user.email,
					    "firstName": $scope.user.firstName,
					    "lastName": $scope.user.lastName,
					    "password": $scope.user.password,
					    "phone": $scope.user.phone,
					    "username": $scope.user.username,
					    "registrationDate": $scope.user.registrationDate,
					    "role": $scope.user.role
				  };
				if(url)
				getOneSubForum(url);
				
		//		console.log($scope.subForum.name);
		//		console.log($scope.subForum);
				
				var theme = {
						"title": newThemeTitle,
					    "subForum":  $scope.subForum.name,
					    "author": user,
					    "content": $scope.newThemeContent.text,
					    "type": selectedType
				};
				
				var test = {
						
						"title": "hi",
						"forum" : $scope.subForum
						
				};
					
				
			//	console.log(theme);
					$http({
						method:'POST',
						url: 'http://localhost:8080/forum161/rest/services/addTheme',
						data: theme
						}).then(function successCallback(response){
							
					//		console.log("GG ");
							$scope.addedSub = true;
							$scope.errorOnAddSub = false;
							
							
							
					//		console.log("evo sata se desilo nakon dodavanja");
							
						//	console.log(response.data);
							
							
							$scope.themes.unshift(response.data);
							//location.reload();
							$scope.addThemeBttn();
							
						}, function errorCallback(response){
							console.log("Nesto je lose uneto kod addSubForm - index2.js ");
							$scope.addedSub = false;
							$scope.errorOnAddSub = true;
							location.reload();
							
							
							
						} );
			}else{
			 console.log("Nesto je lose uneto kod addSubForm - index2.js ");
				$scope.addedSub = false;
				$scope.errorOnAddSub = true;
			
			}
			
		}
		
		
		
		
		
		
		
		
		$scope.followTheme = function(theme){
			
			var ur = "http://localhost:8080/forum161/rest/services/addFavThemeForum/";
			
			var user=
			  {
				    "email": $scope.user.email,
				    "firstName": $scope.user.firstName,
				    "lastName": $scope.user.lastName,
				    "password": $scope.user.password,
				    "phone": $scope.user.phone,
				    "username": $scope.user.username,
				    "registrationDate": $scope.user.registrationDate,
				    "role": $scope.user.role,
				    
			  };
			
			$http({
				  method: 'PUT',
				  url: ur,
				  data : user, 
				  params:{
					  	  "subForumID": $scope.subForum.name,
						  "themeID": theme.id
					  
				  }
				}).then(function successCallback() {
					
		
				//	console.log("Dobio subove");
				//	console.log("Obrisao SubForum");
			//		location.reload();
					
				  }, function errorCallback() {
					  console.log("Greska kod brisanja subForums");
					  location.reload();
				  });
			
			
		}
		
		//console.log("Ulazim u ovaj show Themes :D");
		
		
		$scope.like = function(theme){
			
			
			var rew= {
					
					"id": theme.id, 
					"forType": "THEME"
			};
			
			console.log("like");
			if($scope.user != null ){
			$http({
				method:'POST',
				url: "http://localhost:8080/forum161/rest/services/reviewLike",
				data: rew,
				params: {"userID": $scope.user.username}
				}).then(function successCallback(response){
					
				//	console.log(response.data);
					if(response.data.value === "DODAT"){
						theme.likes=theme.likes+1;
					}
					if(response.data.value === "IZBACEN"){
						theme.likes=theme.likes-1;
					}
					
				//	location.reload();
							
				}, function errorCallback(response){
					console.log("Nesto je lose uneto kod dobijanja like theme u sub ");
					
				})
			}
		}
		
		
		
		$scope.saveEdit = function(theme){
			
			
			$http({
				method:'POST',
				url: "http://localhost:8080/forum161/rest/services/editTheme",
				data: theme
				}).then(function successCallback(response){
					theme.edit= !theme.edit;
							
				}, function errorCallback(response){
					console.log("Nesto je lose kod edita teme ");
					
				})
			
			
			
		}
		
		
		
		
		
		$scope.dislike = function(theme){
			
			var rew= {
					
					"id": theme.id, 
					"forType": "THEME"
			};
			
			//console.log("like");
			if($scope.user != null ){
			$http({
				method:'POST',
				url: "http://localhost:8080/forum161/rest/services/reviewDislike",
				data: rew,
				params: {"userID": $scope.user.username}
				}).then(function successCallback(response){
					
					if(response.data.value === "DODAT"){
						theme.dislikes=theme.dislikes+1;
					}
					if(response.data.value === "IZBACEN"){
						theme.dislikes=theme.dislikes-1;
					}
					
							
				}, function errorCallback(response){
					console.log("Nesto je lose uneto kod dislike theme kod suba");
					
				})
			}
			
		}
		
		
		var getUser = function() {
			
			if($scope.user != null ){
			
			$http({
				  method: 'GET',
				  url: "http://localhost:8080/forum161/rest/services/oneUser",
				  params: {"userID": $scope.user.username }
				}).then(function successCallback(response) {
					
					
				//	console.log("Dobio subove");
			//		console.log(response.data);
					
					
				  }, function errorCallback() {
					  console.log("Greska kod brisanja subForums");
					  
				  });
			}
			
		}
		
		getUser();
		
		$scope.getRecommended = function() {
			
			console.log("Evo me i u dobroj funkciji :D");
			$http({
				  method: 'GET',
				  url: "http://localhost:8080/forum161/rest/services/recommended"
				}).then(function successCallback(response) {
					
					console.log("Dobio recommended");
					console.log(response.data);
					$scope.recommendedThemes = response.data;
					
					
				  }, function errorCallback() {
					  console.log("Greska kod brisanja subForums");
					  
				  });
	
		}
		$scope.getRecommended();
		
		
		
		$scope.removeTheme = function(theme, index){
			
		//	console.log("Poziva se brisanje");
			
			var ur = "http://localhost:8080/forum161/rest/services/RemoveTheme/" + theme.subForum;
			
	//		console.log(theme);
	//		console.log(theme.id);
	//		console.log(theme.subForum);
			
			$http({
				  method: 'DELETE',
				  url: ur,
				  params: {"subForumID": theme.subForum, "themeID" : theme.id}
				}).then(function successCallback() {
					
		
				//	console.log("Dobio subove");
					console.log("Obrisao SubForum");
				//	location.reload();
					
				//	console.log(index);
				//	console.log($scope.themes);
					$scope.themes.splice(index,1);
				  }, function errorCallback() {
					  console.log("Greska kod brisanja subForums");
					  location.reload();
				  });
			
		}
		
		
		$scope.goToTheme = function(theme) {
		
		//	console.log(theme.subForum);
			
			var ur = "http://localhost:8080/forum161/rest/services/Theme/" +theme.id;
			$http({
				method:'GET',
				url: ur, 
				params: {"subForumID": theme.subForum, "themeID" : theme.id  },
				}).then(function successCallback() {
					$window.location.href = ur;
					$window.location.href = "/forum161/start/theme.html?SubForumName="+theme.subForum+"&ThemeID="+theme.id;
					//?SubForumName="+theme.subForum+"&ThemeID="+theme.id
					//	console.log("Dobio subove");
					//	console.log("Uspeo je :D ");
				//	console.log(ur);
				//		console.log($location);
				//		console.log($window.location.href);
						//location.reload();
						
					  }, function errorCallback() {
						  console.log("Greska kod premestanja");
						  location.reload();
					  });
			
		
		
			}
		
	
		
		$scope.editTheme = function(theme){
			theme.edit = !theme.edit;
			//$scope.editThemeON = !$scope.editThemeON;
			
		}
		
		

	
		
		var url = $location.absUrl().split('SubForumName=')[1];
		
		
		
		//console.log(url);
		
		
		var getOneSubForum = function(u){
			
			//console.log("getuje on to :D");
			
			var ur = "http://localhost:8080/forum161/rest/services/getOneSubForum";
			$http({
				method:'GET',
				url: ur,
				params: {"subForumID" : u}
				}).then(function successCallback(response){
					
					$scope.subForum = response.data;
					
					if($scope.subForum.mainModerator.username === $scope.user.username)
						$scope.isCurrentMod= true;
					
					//$scope.themes=$scope.subForum.themes; // tu postavljam teme
				//	console.log("cak i uspeva"); 
				//	console.log(response.data);
					
					$scope.themes = [];
					
					angular.forEach($scope.subForum.themes, function(value, key) {
						  $scope.themes.push(value);
						//  console.log(value);
						 // console.log(key);
						});
					
					
					
				//	console.log($scope.subForum);
				//	console.log($scope.subForum.themes);
				}, function errorCallback(response){
					console.log("Nesto je lose uneto kod dobijanja jednog suba ");
					
				})
			
		}
		if(url)
		getOneSubForum(url);
		//console.log($scope.subForum);
		
		
		
		//console.log($scope.user);
		
		
	}]);
	
	