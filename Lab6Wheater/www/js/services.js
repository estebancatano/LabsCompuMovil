var API_KEY = '5d99375cb63bf3ace57f574cc67fa8aa';
var URL = 'http://api.openweathermap.org/data/2.5/weather';

angular.module('app.services', [])

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
})
.service('ClimaGPS',function($http){
    this.buscarClimaGPS = function(latitud,longitud){
        return $http({
			url: URL, 
            method: "GET",
            params: {
                lat: latitud,
                lon: longitud,
                lang: 'es',
                units: 'metric',
                appid: API_KEY
            }
		});
    }
});

