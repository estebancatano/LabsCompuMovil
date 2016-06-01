var API_KEY = '5d99375cb63bf3ace57f574cc67fa8aa';
var URL = 'http://api.openweathermap.org/data/2.5/weather';
var URL_CLIMA = 'http://openweathermap.org/img/w/';
var PARAMS ='&lang=es&units=metric';
angular.module('app.controllers', [])
  
.controller('estadoDelClimaCtrl', function($scope, Clima, ImagenClima) {
    $scope.mostrarClima = function(){
        Clima.buscarClima($scope.ciudad).success(function(date){
            console.log(date);
            if(date.cod == 200){
                document.getElementById("headerTituloTemperatura").innerHTML = 'Temperatura';
                document.getElementById("headerTituloHumedad").innerHTML = 'Humedad';
                document.getElementById("headerTituloDescripcion").innerHTML = 'Descripción';
                document.getElementById("headerTemperatura").innerHTML = ''.concat(date.main.temp).concat(' °C');
                document.getElementById("headerHumedad").innerHTML = ''.concat(date.main.humidity).concat(' %');
                document.getElementById("headerDescripcion").innerHTML = date.weather[0].description;
                $scope.ciudad = date.name;
                var imagen = date.weather[0].icon;
                document.getElementById('imagenClima').src = URL_CLIMA.concat(imagen).concat('.png');
            }else{
                $scope.respuesta = 'La ciudad ingresada no fue encontrada';
                document.getElementById("headerTituloTemperatura").innerHTML = '';
                document.getElementById("headerTituloHumedad").innerHTML = '';
                document.getElementById("headerTituloDescripcion").innerHTML = '';
                document.getElementById("headerTemperatura").innerHTML = '';
                document.getElementById("headerHumedad").innerHTML = '';
                document.getElementById("headerDescripcion").innerHTML = '';
                document.getElementById('imagenClima').src = '';
            }
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
 