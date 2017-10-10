app.controller("inboxCtrl", ['AuthenticationService','$scope', '$http', '$location','$window', function(AuthenticationService,$scope, $http, $location, $window) {
		
		$scope.user = AuthenticationService.getCurrentUser();
		var url = $location.absUrl().split('Type=')[1];
		
		
		$scope.getFollowedSubForums = function(){
			var ur = "http://localhost:8080/forum161/rest/services/getFollowedSubForums/";
			if($scope.user){
					
			$http({
						method: 'GET',
						url: ur, 
						params : { "userID": $scope.user.username}
						}).then(function successCallback(response) {
						
							$scope.user.followedForums =response.data;
							
							
						  }, function errorCallback() {
							  console.log("Greska kod dobijanja subForuma");
							  location.reload();
						  });
			}
				
		}
		
		
		$scope.getFollowedThemes = function(){
			var ur = "http://localhost:8080/forum161/rest/services/getFollowedThemes/";
			if($scope.user){
					
			$http({
						method: 'GET',
						url: ur, 
						params : { "userID": $scope.user.username}
						}).then(function successCallback(response) {
						
							$scope.user.followedThemes =response.data;
							
							
						  }, function errorCallback() {
							  console.log("Greska kod dobijanja subForuma");
							  location.reload();
						  });
			}
				
		}
		
		
		$scope.getFollowedComments = function(){
			var ur = "http://localhost:8080/forum161/rest/services/getFollowedComments/";
			if($scope.user){
				
			
			$http({
						method: 'GET',
						url: ur, 
						params : { "userID": $scope.user.username}
						}).then(function successCallback(response) {
						
							$scope.user.followedComments =response.data;
							
							
						  }, function errorCallback() {
							  console.log("Greska kod dobijanja subForuma");
							  location.reload();
						  });

				
		}
		
		}
		
		
		$scope.getReviews = function(){
			var ur = "http://localhost:8080/forum161/rest/services/getReviews";
			if($scope.user){
				
				console.log("Getujem reviewove");
			$http({
						method: 'GET',
						url: ur, 
						params : { "userID": $scope.user.username}
						}).then(function successCallback(response) {
						
							$scope.user.reviews =response.data;
							console.log("Dodao sam ih :D");
							$scope.sortReviews();
							$scope.getThemes();
							$scope.getComments();
							
							
							
							
						  }, function errorCallback() {
							  console.log("Greska kod dobijanja subForuma");
						//	  location.reload();
						  });

				
		}
			
			
		
		}
		
	
		$scope.reviewThemesLiked={};
		$scope.reviewThemesDisliked={};
		$scope.reviewCommentsLiked={};
		$scope.reviewCommentsDisliked={};
		
		
		$scope.reviewCommentIDLike = [];
		$scope.reviewCommentIDDislike = [];
		$scope.reviewThemeIDDislike= [];
		$scope.reviewThemeIDLike=[];
		
		
		
		
		$scope.sortReviews = function(){
			
			$scope.reviewCommentIDLike = [];
			$scope.reviewCommentIDDislike = [];
			$scope.reviewThemeIDDislike= [];
			$scope.reviewThemeIDLike=[];
			
		//	console.log("Evo sortiram ih");
			
		//	console.log($scope.user.reviews);
			
			 angular.forEach($scope.user.reviews, function(value, key) {
		
				 
				 	if(value.forType === 'THEME'){
				 		if(value.type === 'LIKE'){
				 			$scope.reviewThemeIDLike.push(value.id);
				 		//	console.log("Nasao !!!");
				 		}else{
				 			$scope.reviewThemeIDDislike.push(value.id);
				 			//console.log("Nasao !!!");
				 		}
				 	}else{
				 		
				 		
				 		if(value.type === 'LIKE'){
				 			$scope.reviewCommentIDLike.push(value.id);
				 		//	console.log("Comm like !!!");
				 		}else{
				 			$scope.reviewCommentIDDislike.push(value.id);
				 		//	console.log("comm dislike !!!");
				 		}
				 		
				 	}
				
				
				});
			 
		}
		
		$scope.getThemes = function(){
		//console.log("U get themes");
			
			if($scope.reviewThemeIDDislike){
			//	console.log($scope.reviewThemeIDDislike);

			//	console.log("U get themes disliked");
					var ur = "http://localhost:8080/forum161/rest/services/getThemeList";
						$http({
							  method: 'POST',
							  url: ur,
							  data :angular.toJson( $scope.reviewThemeIDDislike)
							}).then(function successCallback(response) {
							//	console.log("Navodno je uspeo da pokupi teme :D");
								$scope.reviewThemesDisliked=response.data;

							  }, function errorCallback() {
								  console.log("Greska kod brisanja slanja poruke");
							//	  location.reload();
							  });

			}
			
			
			
			if($scope.reviewThemeIDLike){
			//	console.log($scope.reviewThemeIDLike);

			//	console.log("U get themes disliked");
					var ur = "http://localhost:8080/forum161/rest/services/getThemeList";
						$http({
							  method: 'POST',
							  url: ur,
							  data :angular.toJson( $scope.reviewThemeIDLike)
							}).then(function successCallback(response) {
							//	console.log("Navodno je uspeo da pokupi teme :D");
								$scope.reviewThemesLiked=response.data;

							  }, function errorCallback() {
								  console.log("Greska kod brisanja slanja poruke");
							//	  location.reload();
							  });

			}
			
			
		
		}
		
		
		$scope.getComments = function(){
		//	console.log("U get comments");
			if($scope.reviewCommentIDLike){

					var ur = "http://localhost:8080/forum161/rest/services/getCommentList";
						$http({
							  method: 'POST',
							  url: ur,
							  data :angular.toJson(  $scope.reviewCommentIDLike)
							}).then(function successCallback(response) {
								
								$scope.reviewCommentsLiked=response.data;
								
								
								

							  }, function errorCallback() {
								  console.log("Greska kod brisanja slanja poruke");
								  location.reload();
							  });
			}
			
			if($scope.reviewCommentIDDislike){
				
				
				var ur = "http://localhost:8080/forum161/rest/services/getCommentList";
					$http({
						  method: 'POST',
						  url: ur,
						  data :angular.toJson(  $scope.reviewCommentIDDislike)
						}).then(function successCallback(response) {
							
							$scope.reviewCommentsDisliked=response.data;

						  }, function errorCallback() {
							  console.log("Greska kod brisanja slanja poruke");
							  location.reload();
						  });
				}
			
			
		}
	
		
		
		console.log($scope.user);
		
		$scope.unreadTab = false;
		$scope.flagedTab = false;
		$scope.receivedTab = false;
		$scope.sentTab = false;
		$scope.newMessage = false;
		$scope.recommended = false;
		$scope.Review = false;
		
		$scope.viewMessages = function() {
			$scope.inbox = true;
			$scope.showThemes = false;
			$scope.showSubForums = false;
			$scope.showComments = false;
			$scope.recommended = false;
			$scope.Review = false;
				
		}

		$scope.viewFollowedSubForums = function() {
			$scope.inbox = false;
			$scope.showThemes = false;
			$scope.showSubForums = true;
			$scope.showComments = false;
			$scope.recommended = false;
			$scope.Review = false;
			$scope.getFollowedSubForums();
		//	console.log($scope.user);
		}
		
		$scope.viewFollowedThemes = function() {
			$scope.inbox = false;
			$scope.showThemes = true;
			$scope.showSubForums = false;
			$scope.showComments = false;
			$scope.recommended = false;
			$scope.Review = false;
			$scope.getFollowedThemes();
		//	console.log($scope.user);
			
		}
		
		$scope.viewFollowedComments = function() {
			$scope.inbox = false;
			$scope.showThemes = false;
			$scope.showSubForums = false;
			$scope.showComments = true;
			$scope.recommended = false;
			$scope.Review = false;
			$scope.getFollowedComments();
		//	console.log($scope.user);
		}
		
		
		$scope.viewRecommended = function() {
			$scope.inbox = false;
			$scope.showThemes = false;
			$scope.showSubForums = false;
			$scope.showComments = false;
			$scope.recommended = true;
			$scope.Review = false;
		}
		
		$scope.viewReview = function() {
			$scope.inbox = false;
			$scope.showThemes = false;
			$scope.showSubForums = false;
			$scope.showComments = false;
			$scope.recommended = false;
			$scope.getReviews();
			
			$scope.Review = true;
	
		}
		
		
		
		if(url=="SubForum"){
			$scope.viewFollowedSubForums();
		}else if(url=="Theme"){
			$scope.viewFollowedThemes();
		}else if(url=="Comment"){
			$scope.viewFollowedComments();
		}else if(url=="Recommended"){
			$scope.viewRecommended();
			
		}else if(url=="Review"){
			$scope.viewReview();
		}else{
			$scope.viewMessages();
		}
		
		
		
		
		
		$scope.viewUnreadMessages = function() {
			$scope.unreadTab = true;
			$scope.flagedTab = false;
			$scope.receivedTab = false;
			$scope.sentTab = false;
			$scope.newMessage = false;
			
		}
		
		$scope.viewFlaggedMessages = function() {
			$scope.unreadTab = false;
			$scope.flagedTab = true;
			$scope.receivedTab = false;
			$scope.sentTab = false;
			$scope.newMessage = false;
			
		}
		
		$scope.viewReceivedMessages = function() {
			$scope.unreadTab = false;
			$scope.flagedTab = false;
			$scope.receivedTab = true;
			$scope.sentTab = false;
			$scope.newMessage = false;
			
		}
		
		$scope.viewSentMessages = function() {
			$scope.unreadTab = false;
			$scope.flagedTab = false;
			$scope.receivedTab = false;
			$scope.sentTab = true;
			$scope.newMessage = false;
			
		}
		
		$scope.viewNewMessage = function() {
			$scope.unreadTab = false;
			$scope.flagedTab = false;
			$scope.receivedTab = false;
			$scope.sentTab = false;
			$scope.newMessage = true;
			
		}
	
		
		$scope.sendNewMessage = function(newMessageText, newReciver){
			if(newMessageText)
				if(newReciver){
					
					
					var ur = "http://localhost:8080/forum161/rest/services/sendMessage/";
					
					$http({
						  method: 'PUT',
						  url: ur,
						  data : newMessageText, 
						  params : { "reciverID": newReciver, "senderID": $scope.user.username}
						}).then(function successCallback(response) {
							console.log("Navodno je uspeo :D")
							
						//	console.log("Dobio subove");
						//	console.log("Obrisao SubForum");
						//	location.reload();
							$scope.messages.push(response.data);
							$scope.newMessageText = "";
							
						  }, function errorCallback() {
							  console.log("Greska kod brisanja slanja poruke");
							  location.reload();
						  });

				}
		}
		
		
		
		
		
		$scope.readMessage = function(message){
			var ur = "http://localhost:8080/forum161/rest/services/readMessage";	
			$http({
				  method: 'POST',
				  url: ur,
				  data : message 
				}).then(function successCallback(response) {
					//console.log("Navodno je uspeo da dobije poruke :D")
					message.read = true;
					$scope.viewReceivedMessages();
				}, function errorCallback() {
					  console.log("Greska kod brisanja dobijanja poruka");
					  location.reload();
				  });
		}
		
		
		
	//	replyToFlag(message, option.name)
		
		
		$scope.replyToFlag = function(message, option){
			
			
		var message2 =	{
				"content": message.content,
				"read" : false,
				"senderID" : message.senderID,
				"isFlag" : true,
				"receiverID" : message.receiverID,
				"reply" : false,
				"id" : message.id
			
		}
			if(option){
				
					var ur = "http://localhost:8080/forum161/rest/services/solveFlag/";
					
					$http({
						  method: 'POST',
						  url: ur,
						  data : message2, 
						  params : { "option": option}, 
						  headers: {
					            'Content-Type': 'application/json'
					        }
						}).then(function successCallback(response) {
							console.log("Navodno je uspeo :D")
							
						//	console.log("Dobio subove");
						//	console.log("Obrisao SubForum");
						//	location.reload();
						//	$scope.messages.push(response.data);
						//	$scope.newMessageText = "";
							
							$scope.readMessage(message);
							
						  }, function errorCallback() {
							  console.log("Greska kod brisanja slanja poruke");
							  location.reload();
						  });

				}
		}
		
		
		
		$scope.getMessages = function(userID){
			if(userID){
				var ur = "http://localhost:8080/forum161/rest/services/getMessages";	
				$http({
					  method: 'GET',
					  url: ur,
					  params:{"userID" : userID} 
					}).then(function successCallback(response) {
					//	console.log("Navodno je uspeo da dobije poruke :D")
						$scope.messages =[];
						$scope.user.messages = response.data;
						
						 angular.forEach($scope.user.messages, function(value, key) {
							 
							 if(value.isFlag){
								 var link = "/forum161/start/";
								 var array = value.content.split("\n");
								 var subid = array[0].split("SubForum ID : ")[1];
								 
						//		 console.log(array[0]);
						//		 console.log(array[1]);
						//		 console.log(array[2]);
								 
								 
								 if(array[1].indexOf("Theme ID : ") !==-1){
									 
							//		 console.log("UPAO SAM U OVAJ THEME ID DEO")
									 
									 var themeid = array[1].split("Theme ID : ")[1];
									 link = link + "theme.html?SubForumName=" +subid + "&ThemeID="+themeid ;
								 }else{
									 link = link + "subForum.html?SubForumName=" +subid;
								 }
							//	 console.log(link);
								 value.link = link;
								 
							 }
							 
							 
							 $scope.messages.unshift(value);
							 // theme.comments.push(value);
							
							});
						
						
					}, function errorCallback() {
						  console.log("Greska kod brisanja dobijanja poruka");
						  location.reload();
					  });
			}
		}
		if($scope.user)
		$scope.getMessages($scope.user.username);
		

		$scope.replyToMessage= function(replyMessageText, message){
			
			$scope.sendNewMessage(replyMessageText, message.senderID);
			
		}
		
		
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
	
	