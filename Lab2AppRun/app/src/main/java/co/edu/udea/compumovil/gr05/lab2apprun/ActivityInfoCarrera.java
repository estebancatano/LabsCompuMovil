package co.edu.udea.compumovil.gr05.lab2apprun;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class ActivityInfoCarrera extends AppCompatActivity {

    private TextView lblNombre;
    private TextView lblDistancia;
    private TextView lblLugar;
    private TextView lblFecha;
    private TextView lblDescripcion;
    private TextView lblTelefono;
    private TextView lblCorreo;

    private String nombre;
    private String distancia;
    private String lugar;
    private String fecha;
    private String descripcion;
    private String telefono;
    private String correo;

    public static final String TAG_NOMBRE_CARRERA = "nombreCarrera";
    public static final String TAG_DISTANCIA_CARRERA = "distanciaCarrera";
    public static final String TAG_LUGAR_CARRERA = "lugarCarrera";
    public static final String TAG_FECHA_CARRERA = "fechaCarrera";
    public static final String TAG_DESCRIPCION_CARRERA = "descripcionCarrera";
    public static final String TAG_TELEFONO_CARRERA = "telefonoCarrera";
    public static final String TAG_CORREO_CARRERA = "correoCarrera";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_carrera);
        setToolbar();

        lblNombre = (TextView) findViewById(R.id.lbl_nombre_carrera);
        lblDistancia = (TextView) findViewById(R.id.lbl_distancia_carrera);
        lblLugar = (TextView) findViewById(R.id.lbl_lugar_carrera);
        lblFecha = (TextView) findViewById(R.id.lbl_fecha_carrera);
        lblDescripcion = (TextView) findViewById(R.id.lbl_descripcion_carrera);
        lblTelefono = (TextView) findViewById(R.id.lbl_telefono_carrera);
        lblCorreo = (TextView) findViewById(R.id.lbl_correo_carrera);

        Bundle extras = getIntent().getExtras();

        nombre = extras.getString(ActivityInfoCarrera.TAG_NOMBRE_CARRERA);
        distancia = extras.getString(ActivityInfoCarrera.TAG_DISTANCIA_CARRERA);
        lugar = extras.getString(ActivityInfoCarrera.TAG_LUGAR_CARRERA);
        fecha = extras.getString(ActivityInfoCarrera.TAG_FECHA_CARRERA);
        descripcion = extras.getString(ActivityInfoCarrera.TAG_DESCRIPCION_CARRERA);
        telefono = extras.getString(ActivityInfoCarrera.TAG_TELEFONO_CARRERA);
        correo = extras.getString(ActivityInfoCarrera.TAG_CORREO_CARRERA);

        lblNombre.setText(nombre);
        lblDistancia.setText(distancia);
        lblLugar.setText(lugar);
        lblFecha.setText(fecha);
        lblDescripcion.setText(descripcion);
        lblTelefono.setText(telefono);
        lblCorreo.setText(correo);
    }

    private void setToolbar() {
        //Crea el widget Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        //Referencia la ActionBar como Toolbar
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            //Atributos de la Toolbar
            ab.setTitle(R.string.title_info_evento);
            ab.setIcon(R.mipmap.logo_grupo5);
        }
    }
}
