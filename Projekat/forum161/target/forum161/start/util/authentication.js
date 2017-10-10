(function () {
    angular
        .module('authentication',['ngStorage', 'ui.router', 'angular-jwt'])
        .factory('AuthenticationService', Service);

    function Service($http, $localStorage, $log, $state, jwtHelper) {
        var service = {};

        service.login = login;
        service.logout = logout;
        service.getCurrentUser = getCurrentUser;

        return service;

        function login(korisnickoIme, lozinka, callback) {
        	
        	var user=
			  {
				    "email": "",
				    "firstName": "",
				    "lastName": "",
				    "password": lozinka,
				    "phone": "",
				    "username": korisnickoIme
			  };

        	$http({
  			  method: 'POST',
  			  url: 'http://localhost:8080/forum161/rest/services/login',
  			  data: user
  			}).then(function successCallback(response) {
  				
  				//console.log("U loginu");
                // ukoliko postoji token, prijava je uspecna
  				
  			//	console.log( "U loginu");
                if (response.data.token) {
                	console.log( "dobija token");
                    // korisnicko ime, token i rola (ako postoji) cuvaju se u lokalnom skladištu
                    var currentUser = response.data.user;
                    currentUser.token = response.data.token;
                    var tokenPayload = jwtHelper.decodeToken(response.data.token);
                    if(tokenPayload.role){
                        currentUser.role = tokenPayload.role;
                     //   console.log( "dobija neki role");
                    }
                    // prijavljenog korisnika cuva u lokalnom skladistu
                    $localStorage.currentUser = currentUser;
                //    console.log( "dobija localstorage");
              	 // 	console.log( $localStorage.currentUser);
                    
                    // jwt token dodajemo u to auth header za sve $http zahteve
                    $http.defaults.headers.common.Authorization = response.data.token;
                    // callback za uspesan login
                    callback(true);
                    switch (currentUser.role) {
    				case "ADMIN":
    				
    					break;
    				case "MODERATOR":
    			
    					break;
    				case "REGULAR":
    		
    					break;
    				}
                }
            }, function errorCallback(response) {
            	callback(false);
              });
        }

        function logout() {
            // uklonimo korisnika iz lokalnog skladišta
        	var temp = $localStorage.currentUser;
        	
        	 var logoutUser=
			  {
				    "email": temp.email,
				    "firstName": temp.firstName,
				    "lastName": temp.lastName,
				    "password": temp.password,
				    "phone": temp.phone,
				    "username": temp.username,
				    "role" : temp.role
			  };
        	
        //	 console.log("Upao u autenth logout");
            delete $localStorage.currentUser;
            $http.defaults.headers.common.Authorization = '';
            $http({
    			  method: 'POST',
    			  url: 'http://localhost:8080/forum161/rest/services/logout',
    			  data : logoutUser
    			}).then(function successCallback(response) {
					
    				location.reload();
					
				  }, function errorCallback(response) {
					  console.log("Greska kod logouta ");
					  location.reload();
				
				  });
      
        }

        function getCurrentUser() {
            return $localStorage.currentUser;
        }
    }
})();