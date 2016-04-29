package co.edu.udea.compumovil.gr05.lab4weather;

/**
 * Created by joluditru on 28/04/2016.
 */
public class Clima {
    private String id;
    private String main;
    private String description;
    private String icon;

    public Clima() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
