package co.edu.udea.compumovil.gr05.lab4weather;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtCiudad;
    private Button btnBuscar;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    public static String TAG_INFO = "TAG_INFO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();

        txtCiudad = (EditText) findViewById(R.id.txt_ciudad);
        btnBuscar = (Button) findViewById(R.id.btn_buscar);

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
        FragmentInformation fragmentInformation = new FragmentInformation();
        fragmentInformation.show(fragmentManager, TAG_INFO);
    }
}
