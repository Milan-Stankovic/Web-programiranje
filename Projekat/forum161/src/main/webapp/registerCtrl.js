var app = angular.module('app');


app.directive('demoFileModel', function ($parse) {
    return {
        restrict: 'A', //the directive can be used as an attribute only

        /*
         link is a function that defines functionality of directive
         scope: scope associated with the element
         element: element on which this directive used
         attrs: key value pair of element attributes
         */
        link: function (scope, element, attrs) {
            var model = $parse(attrs.demoFileModel),
                modelSetter = model.assign; //define a setter for demoFileModel

            //Bind change event on the element
            element.bind('change', function () {
                //Call apply on scope, it checks for value changes and reflect them on UI
                scope.$apply(function () {
                    //set the model value
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
});


app.controller("registerCtrl", ['$scope', '$http', function($scope, $http) {
	
	$scope.greskaRegistracija = false;
	 $scope.uspesnaRegistracija = false;
	
	$scope.register= function(newUsername,newPassword,repeatPassword,newFirstName,newLastName,newPhone,newEmail) {
		  
		
		  function validateEmail(email) {
			    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			    return re.test(email);
			}
		
		  var b= false;
		  if(newUsername)
			  if(newPassword)
				  if(repeatPassword)
					  if(newFirstName)
						  if(newLastName)
							  if(newPhone)
								  if(newEmail)
									  if(!isNaN(newPhone))
										  if(newPassword===repeatPassword)
											  if(validateEmail(newEmail))
												  b=true;
		  
		  console.log("Prosla validacija za registraciju");
		  console.log(b);
		

		  console.log("U registraciji");
		  console.log(b);
		  if(b){
			  var user=
					  {
						    "email": newEmail,
						    "firstName": newFirstName,
						    "lastName": newLastName,
						    "password": newPassword,
						    "phone": newPhone,
						    "username": newUsername
					  };

		      	$http({
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
		  }
		  $scope.greskaRegistracija =true;
		  $scope.uspesnaRegistracija = false;
	
	}
}]);