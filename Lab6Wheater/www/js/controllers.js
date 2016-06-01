var API_KEY = '5d99375cb63bf3ace57f574cc67fa8aa';
var URL = 'http://api.openweathermap.org/data/2.5/weather';
var PARAMS ='&lang=es&units=metric';
angular.module('app.controllers', [])
  
.controller('estadoDelClimaCtrl', function($scope, Clima) {
    $scope.mostrarClima = function(){
        Clima.buscarClima($scope.ciudad).success(function(date){
            console.log(date);
            document.getElementById("headerTemperatura").innerHTML = ''.concat(date.main.temp).concat(' °C');
            document.getElementById("headerHumedad").innerHTML = ''.concat(date.main.humidity).concat(' %');;
            //console.log(date.weather[0].description);
            document.getElementById("headerDescripcion").innerHTML = date.weather[0].description;
            $scope.ciudad = date.name;
        });
        
    };
})

.service('Clima',function($http){
	this.buscarClima = function(ciudad){
		return $http({
			url: URL, 
            method: "GET",
            params: {q: ciudad,
                lang: 'es',
                units: 'metric',
                appid: API_KEY
            }
		});
	};
});
 