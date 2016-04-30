package co.edu.udea.compumovil.gr05.lab4weather.Modelo;

/**
 * Created by joluditru on 28/04/2016.
 */
public class DatosClima {
    private Clima[] weather;
    private Principal main;
    private Sistema sys;
    private String name;
    private String cod;

    public DatosClima() {
    }

    public Clima[] getWeather() {
        return weather;
    }

    public void setWeather(Clima[] weather) {
        this.weather = weather;
    }

    public Principal getMain() {
        return main;
    }

    public void setMain(Principal main) {
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Sistema getSystem() {
        return sys;
    }

    public void setSystem(Sistema sys) {
        this.sys = sys;
    }
}
