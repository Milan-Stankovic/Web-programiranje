app.controller("themeCtrl", ['AuthenticationService','$scope', '$http','$location','$window','commentService', '$rootScope', function(AuthenticationService,$scope, $http, $location, $window, commentService,$rootScope) {
		
		$scope.user = AuthenticationService.getCurrentUser();
		//console.log($scope.user);
		
		//console.log("U theme controleru sam ! :D ");
		
		$scope.editThemeON = true;
		$scope.isCurrentMod= false;
		$scope.themes={};
		$scope.newCommentText = "";
		$scope.replyText = "";
		$scope.comments={};
		$scope.theme={};
		$scope.themeTypes = ["TEXT","IMAGE","LINK"];
		comments = {};
		
		
		$scope.startReply = function(comment){
			comment.reply = !comment.reply;
		}
		
		
		
		
		$scope.flagActivateCurrentTheme = function(){
			$scope.theme.flag = !$scope.theme.flag;
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
						$scope.flagActivateCurrentTheme();
						$scope.flagTextTT='';
					  }, function errorCallback() {
						  console.log("Greska kod premestanja");
						  location.reload();
					  });	
		}
		
	
		
		
		$scope.followTheme = function(theme){
			
			var ur = "http://localhost:8080/forum161/rest/services/addFavThemeForum" ;
			
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
				//	location.reload();
					
				  }, function errorCallback() {
					  console.log("Greska kod brisanja subForums");
					  location.reload();
				  });
			
			
		}
		
		
		
		$scope.followComment = function(comment){
			
			var ur = "http://localhost:8080/forum161/rest/services/addFavComment";
		
			$http({
				  method: 'PUT',
				  url: ur,
				  params:{
					  	  "subForumID": $scope.theme.subForum,
						  "themeID": $scope.theme.id, 
						  "userID" : $scope.user.username, 
						  "commentID" : comment.id,
						  "parentID" : comment.parentID
					  
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
		
		
		$scope.likeTheme = function(theme){
			
			
			var rew= {
					
					"id": theme.id, 
					"forType": "THEME"
			};
			
			//console.log("like");
			if($scope.user != null ){
			$http({
				method:'POST',
				url: "http://localhost:8080/forum161/rest/services/reviewLike",
				data: rew,
				params: {"userID": $scope.user.username}
				}).then(function successCallback(response){
					if(response.data.value === "DODAT"){
						theme.likes=theme.likes+1;
					}
					if(response.data.value === "IZBACEN"){
						theme.likes=theme.likes-1;
					}
							
				}, function errorCallback(response){
					console.log("Nesto je lose uneto kod like theme ");
					
				})
			}
		}
		
		
		
		$scope.viewReplys = function(comment){
			
			comment.viewAll = true;
			
			
		}
		
		$scope.hideReplys = function(comment){
			comment.viewAll = false;
		}
		
		$scope.likeComment = function(comment){
		//	console.log("upao u like za komentar");
		//	console.log(comment);
		//	console.log($scope.theme);
			
			var rew= {
					
					"id": comment.id, 
					"forType": "COMMENT"
			};
			
			//console.log("like");
			if($scope.user != null ){
			$http({
				method:'POST',
				url: "http://localhost:8080/forum161/rest/services/reviewLike",
				data: rew,
				params: {"userID": $scope.user.username}
				}).then(function successCallback(response){
			//		console.log("kao je uspeo like na kom");
			//		console.log(response.data.value);
			//		console.log(comment.likes);
					if(response.data.value === "DODAT"){
						comment.likes=comment.likes+1;
					}
					if(response.data.value === "IZBACEN"){
						comment.likes=comment.likes-1;
					}
				//	console.log(comment.likes);
							
				}, function errorCallback(response){
					console.log("Nesto je lose uneto kod like comment ");
					
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
		
		
		
		$scope.logicalDeleteComment = function(comment){

			$http({
				method:'POST',
				url: "http://localhost:8080/forum161/rest/services/logicalDeleteComment",
				data: comment,
				params: {"subForumID": $scope.theme.subForum, "themeID" : $scope.theme.id}
				}).then(function successCallback(response){
					if(comment.status === 'LOGICKIOBRISAN')
						comment.status = 'AKTIVAN';
					else
						comment.status = 'LOGICKIOBRISAN';
							
				}, function errorCallback(response){
					console.log("Nesto je lose kod logickog brisanja komentara ");
					
				})

		}
		
		
		
		$scope.saveEditComment = function(comment){
			
			
			$http({
				method:'POST',
				url: "http://localhost:8080/forum161/rest/services/editComment",
				data: comment,
				params: {"subForumID": $scope.theme.subForum, "themeID" : $scope.theme.id, "userID" : $scope.user.username}
				}).then(function successCallback(response){
					comment.edit= !comment.edit;
							
				}, function errorCallback(response){
					console.log("Nesto je lose kod edita commenta ");
					
				})
			
			
			
		}
		
		
		$scope.dislikeTheme = function(theme){
			
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
					console.log("Nesto je lose uneto kod dislike teme ");
					
				})
			}
			
		}
		
		
		$scope.dislikeComment = function(comment){
			
			var rew= {
					
					"id": comment.id, 
					"forType": "COMMENT"
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
						comment.dislikes=comment.dislikes+1;
					}
					if(response.data.value === "IZBACEN"){
						comment.dislikes=comment.dislikes-1;
					}
					
							
				}, function errorCallback(response){
					console.log("Nesto je lose uneto kod dislike comment ");
					
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
				//	console.log(response.data);
					
					
				  }, function errorCallback() {
					  console.log("Greska kod brisanja subForums");
					  
				  });
			}
			
		}
		
		getUser();
		
		$scope.removeCurrentTheme = function(){
			
	//		console.log("Poziva se brisanje");
			
			
			
			var ur = "http://localhost:8080/forum161/rest/services/RemoveTheme/" + $scope.theme.subForum;
			
		//	console.log($scope.theme.subForum);
		//	console.log(theme.id);
			
			$http({
				  method: 'DELETE',
				  url: ur,
				  params: {"subForumID": $scope.theme.subForum, "themeID" : $scope.theme.id}
				}).then(function successCallback() {
					
		
					//console.log("Kao uspelo brisanje");
				//	console.log("Obrisao SubForum");
					$scope.goBackToSubForum();
					
				  }, function errorCallback() {
					  console.log("Greska kod brisanja subForums");
					  location.reload();
				  });
			
		}
		
		
		
		$scope.removeComment = function(comment){
			
			//		console.log("Poziva se brisanje");
					
					
					
					var ur = "http://localhost:8080/forum161/rest/services/RemoveComment/";
					
				//	console.log($scope.theme.subForum);
				//	console.log(theme.id);
					
					$http({
						  method: 'POST',
						  url: ur,
						  params: {"subForumID": $scope.theme.subForum, "themeID" : $scope.theme.id}, 
						  data : comment
						}).then(function successCallback() {
							comment.status = 'OBRISAN';
							
						  }, function errorCallback() {
							  console.log("Greska kod brisanja komentara");
							  location.reload();
						  });
					
			}
		
		
		
	
		
		$scope.editTheme = function(){
		//	console.log("Upao sam u edit theme");
		///	console.log($scope.theme);
		//	console.log($scope.theme.edit);
			$scope.theme.edit = !$scope.theme.edit;
			
			
			
		//	console.log($scope.theme.edit);
			
		}
		
		
		
		$scope.editComment = function(comment){
		//	console.log("Upao sam u edit theme");
		///	console.log($scope.theme);
		//	console.log($scope.theme.edit);
			comment.edit = !comment.edit;
			
			
			
		//	console.log($scope.theme.edit);
			
		}
		
		
		var url1 = $location.absUrl().split('SubForumName=')[1];
		var subForumName = url1.split('&')[0];
		var themeID = url1.split('ThemeID=')[1]; 
		
		//console.log(themeID);
		//console.log(subForumName);
		
		
		
		
		
		var getOneSubForum = function(u){
			
			//console.log("getuje on to :D");
			
		//	console.log("U get one subForum");
			
			var ur = "http://localhost:8080/forum161/rest/services/getOneSubForum";
			$http({
				method:'GET',
				url: ur,
				params: {"subForumID" : u}
				}).then(function successCallback(response){
					
					$scope.subForum = response.data;
					$scope.themes=$scope.subForum.themes;
					$rootScope.$broadcast('subForumAdded', response.data);
					
					if($scope.subForum.mainModerator.username === $scope.user.username)
						$scope.isCurrentMod= true;
					
				//	console.log("cak i uspeva");
				//	console.log(response.data);
				//	console.log($scope.subForum);
				//	console.log($scope.subForum.themes);
				}, function errorCallback(response){
					console.log("Nesto je lose uneto kod dobijanja jednog suba ");
					
				})
			
		}
		if(subForumName)
		getOneSubForum(subForumName);
		
		
		
		//console.log($scope.subForum);
		
		
		var getOneTheme = function(u){
			
			//console.log("getuje on to :D");
			
			//console.log("U get one theme");
			//console.log(u);
			
			
			var ur = "http://localhost:8080/forum161/rest/services/getOneTheme/" +u;
			$http({
				method:'GET',
				url: ur,
				 params:{
				  	  "subForumID": subForumName
				  	  }
				}).then(function successCallback(response){
					
				//	console.log(response.data);
			//		console.log("U get one theme");
					$scope.theme = response.data;
					
					commentService.setTheme(response.data);
					$rootScope.$broadcast('dataAdded', response.data);
					$scope.comments = $scope.theme.comments;
				//	console.log($scope.comments);
					comments = $scope.comments;
					//console.log("cak i uspeva");
				//	console.log(response.data);
				//	console.log($scope.theme);
				
				}, function errorCallback(response){
					console.log("Nesto je lose uneto kod dobijanja jedne teme ");
					
				})
			
		}
		if(themeID)
		getOneTheme(themeID);
		
		
		
	//	console.log($scope.user);
		
		
		$scope.goBackToSubForum = function() {
			
	
			
				var ur = "http://localhost:8080/forum161/rest/services/SubForum/" +$scope.subForum.name;
				$http({
					method:'GET',
					url: ur,
					params:{
						"subForumID" : $scope.subForum.name
					}
					}).then(function successCallback() {
					//	$window.location.href = ur;
						$window.location.href = "/forum161/start/subForum.html?SubForumName="+$scope.subForum.name;
						//?SubForumName="+theme.subForum+"&ThemeID="+theme.id
						//	console.log("Dobio subove");
						//	console.log("Uspeo je :D ");
						//	console.log(ur);
						//	console.log($location);
						//	console.log($window.location.href);
							//location.reload();
							
						  }, function errorCallback() {
							  console.log("Greska kod premestanja");
							  location.reload();
						  });	
				
			
				
				
			
			
			
			
		}
		
		
		
		
		
		
		
		$scope.addComment = function(newCommentText){
			
		//	console.log("dodaje komentar");	
		//	console.log(newCommentText);
		
			if(newCommentText){
				
			//	console.log($scope.theFile);
				
			
	
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
				
				
	
				
				var comment = {
						"text": newCommentText,
					    "parentID":  $scope.theme.id,
					    "author": user
					    
				};
				$scope.newCommentText = "";
				
			
				//	console.log(comment);
				//	console.log($scope.subForum.name);
				//	console.log($scope.theme.id);
				
			//	console.log(theme);
					$http({
						method:'POST',
						url: 'http://localhost:8080/forum161/rest/services/addComment',
						 params:{
						  	  "subForumID": $scope.subForum.name,
							  "themeID": $scope.theme.id
						  
						 },
						data: comment
						}).then(function successCallback(response){
							
					//		console.log("GG ");
							$scope.addedSub = true;
							$scope.errorOnAddSub = false;
							
						//	console.log("evo sata se desilo nakon dodavanja");
							
						//	console.log(response.data);
							
							
						//	location.reload();
							
							$scope.newCommentText='';
							$rootScope.$broadcast('commentAdded', response.data);
							
							
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
		
		
		
	$scope.addCommentToComment = function(replyText, parentComment){
			
		//	console.log("dodaje komentar na komentar");	
		//	console.log(replyText);
		//	console.log(parentComment);
			if(replyText){
				
			//	console.log($scope.theFile);
				
			
	
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
				
				
	
				
				var comment = {
						"text": replyText,
					    "parentID":  parentComment.id,
					    "author": user
					    
				};
				
				
				
				$scope.replyText='';
				//	console.log(comment);
				//	console.log($scope.subForum.name);
				//	console.log($scope.theme.id);
				
			//	console.log(theme);
					$http({
						method:'POST',
						url: 'http://localhost:8080/forum161/rest/services/addCommentToComment',
						 params:{
						  	  "subForumID": $scope.subForum.name,
							  "themeID": $scope.theme.id
					
						 },
						data: comment
						}).then(function successCallback(response){
							
					//		console.log("GG ");
							$scope.addedSub = true;
							$scope.errorOnAddSub = false;
							
						//	console.log("evo sata se desilo nakon dodavanja");
							
						//	console.log(response.data);
							
							
					//		location.reload();
							getOneTheme(themeID);
							parentComment.reply = !parentComment.reply;
					
							
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
		
		
		
		
		
	
		
		
		
	}]);


app.service('commentService', function() {
	  var theme = [];
	  var comments = [];

	  var setTheme = function(newObj) {
	      theme=newObj;
	      
	  };

	  var getTheme = function(){
	      return theme;
	  };
	  
	

	  return {
		  setTheme: setTheme,
		  getTheme: getTheme
	  };

	});


app.controller('AppCtrl', function($scope, commentService,$rootScope, $http, AuthenticationService){
	
	
	$scope.user = AuthenticationService.getCurrentUser();
	
	$scope.comments=[];
	
	$scope.$on('dataAdded', function(event,data){
		 $scope.theme = commentService.getTheme();
		 $scope.comments =[];
		 angular.forEach($scope.theme.comments, function(value, key) {
			 $scope.comments.push(value);
			 // theme.comments.push(value);
			
			});
	});
	
	$scope.$on('commentAdded', function(event,data){
		$scope.comments.push(data);		
	})
	
	
	
	$scope.$on('subForumAdded', function(event,data){
		$scope.subForum = data;		
		if($scope.subForum.mainModerator.username === $scope.user.username)
			$scope.isCurrentMod= true;
	})
	
	
	$scope.flagActivateComment = function(comment){
		comment.flag = !comment.flag;
	}

	
	
	$scope.flagComment = function(comment, text) {
			
			var data ={
					
					"message" : text,
					"subForum" : $scope.subForum,
					"theme" : $scope.theme,
					"comment" : comment
					
			};
			
			var ur = "http://localhost:8080/forum161/rest/services/flagComment";
			$http({
				method:'POST',
				url: ur,
				data : data, 
				params : {"userID" : $scope.user.username}
				}).then(function successCallback() {
					$scope.flagTextC = ''
						$scope.flagActivateComment(comment);
					  }, function errorCallback() {
						  console.log("Greska kod premestanja");
						  location.reload();
					  });	
		}
	

	
	
});






