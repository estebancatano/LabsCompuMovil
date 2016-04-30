package co.edu.udea.compumovil.gr05.lab4weather;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import co.edu.udea.compumovil.gr05.lab4weather.Modelo.DatosClima;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    public static final String TAG_TEMP = "TAG_TEMP";
    public static final String TAG_HUMEDAD = "TAG_HUMEDAD";
    public static final String TAG_DESCRIPCION = "TAG_DESCRIPCION";
    public static final String TAG_IMAGEN = "TAG_IMAGEN";
    private AutoCompleteTextView txtCiudad;
    private Button btnBuscar;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    public static final String TAG_INFO = "TAG_INFO";
    public static final String TAG_CIUDAD = "TAG_CIUDAD";

    private static final String API_KEY = "&appid=5d99375cb63bf3ace57f574cc67fa8aa";
    private static final String URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static final String PARAMS ="&lang=es&units=metric";
    private String peticion;
    private DatosClima datosClima;
    private RequestQueue requestQueue;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();

        txtCiudad = (AutoCompleteTextView) findViewById(R.id.txt_ciudad);
        btnBuscar = (Button) findViewById(R.id.btn_buscar);

        // Obtiene el string array
        String[] paises = getResources().getStringArray(R.array.lista_paises);

        // Crea el adaptador para los países
        ArrayAdapter<String> adaptadorPaises = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, paises);
        txtCiudad.setAdapter(adaptadorPaises);

        txtCiudad.setOnEditorActionListener(this);
        btnBuscar.setOnClickListener(this);
    }

    private void setToolbar() {
        //Crea el widget Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        //Referencia la ActionBar como Toolbar
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            //Atributos de la Toolbar
            ab.setTitle(R.string.app_name);
            ab.setIcon(R.mipmap.logo_grupo5);
        }
    }

    @Override
    public void onClick(View v) {
        buscarClima();
    }

    private String quitarEspacios(String cadena){
        StringBuilder stringBuilder = new StringBuilder();
        int tam = cadena.length();
        for (int i = 0; i < tam; i++) {
            char c = cadena.charAt(i);
            if (c != ' '){
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    private void sendRequest(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, peticion,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        gson = new Gson();

                        JsonParser jsonParser = new JsonParser();
                        JsonObject object = (JsonObject) jsonParser.parse(response.toString());
                        datosClima = gson.fromJson(object,DatosClima.class);
                        if (datosClima != null){
                            if (!datosClima.getCod().equals("404")){
                                Bundle bundle = new Bundle();
                                bundle.putString(TAG_CIUDAD, datosClima.getName()+","
                                        +datosClima.getSystem().getCountry().toUpperCase());
                                bundle.putString(TAG_TEMP, datosClima.getMain().getTemp());
                                bundle.putString(TAG_HUMEDAD, datosClima.getMain().getHumidity());
                                bundle.putString(TAG_DESCRIPCION, datosClima.getWeather()[0].getDescription());
                                bundle.putString(TAG_IMAGEN, datosClima.getWeather()[0].getIcon());

                                FragmentInformation fragmentInformation = new FragmentInformation();
                                fragmentInformation.setArguments(bundle);
                                fragmentInformation.show(fragmentManager, TAG_INFO);
                            }else{
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_clima),
                                        Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_clima),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_clima),
                        Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(objectRequest);
    }

    private void buscarClima(){
        String ciudad;
        ciudad=txtCiudad.getText().toString();

        peticion = URL + quitarEspacios(ciudad) + API_KEY + PARAMS;

        sendRequest();
        txtCiudad.setText(null);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH){
            buscarClima();
        }
        return false;
    }
}
