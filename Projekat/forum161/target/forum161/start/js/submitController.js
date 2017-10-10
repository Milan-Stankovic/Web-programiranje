var app = angular.module('app');


app.controller('submitController', ['$scope', 'multipartForm', function($scope, multipartForm){
	$scope.subForum = {};
	$scope.Submit = function(){
		var uploadUrl = '/forum161/rest/services/addSubForum';
		
		multipartForm.post(uploadUrl, $scope.subForum);
	}
}]);