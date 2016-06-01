var URL_CLIMA = 'http://openweathermap.org/img/w/';
var latitud = 25.26;
var longitud = 55.3;
console.log('Inicio');

if(navigator.geolocation){
    navigator.geolocation.getCurrentPosition(onSuccess, onError);
}else{
    alert('Lo sentimos, su navegador no soporta servicios de geolocalizacion');
}

angular.module('app.controllers', [])
  
.controller('estadoDelClimaCtrl', function($scope, Clima, ClimaGPS) {
    $scope.mostrarClimaGPS = function(){
        ClimaGPS.buscarClimaGPS(latitud,longitud).success(function(date){
            console.log("Aqui estoy");
            console.log(date);
            mostrarDatos(date);
       
        });
    }
    $scope.mostrarClima = function(){
        Clima.buscarClima($scope.ciudad).success(function(date){
           mostrarDatos(date);
        });
    };
    function mostrarDatos(date){
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
    }
});

function onSuccess(position){
    latitud = position.coords.latitude;
    longitud = position.coords.longitude;
    console.log('En el GPS');
    angular.element(document.getElementById('controllerClima')).scope().mostrarClimaGPS();
    
} 

function onError(error){
    alert('codigo: ' + error.code + '\n' + 
        'mensaje: ' + error.message + '\n');
}
 