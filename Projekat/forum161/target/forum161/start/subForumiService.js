/*(function(angular) {
	app.controller("subForumCtrl", ['$scope', '$http', function($scope, $http) {
	
		console.log("Upad kod GET subForums");
		
		$scope.subForumi={};

		
		
		var getSubForumi = function() {
			
			$http({
				  method: 'GET',
				  url: 'http://localhost:8080/forum161/rest/services/allSubForums'
				}).then(function successCallback(response) {
					$scope.korpa = response.data;
				  }, function errorCallback(response) {
					  console.log("Greska kod GET subForums");
				  });
			
		}
		getSubForumi();
	
	
	}]);
	


});*/