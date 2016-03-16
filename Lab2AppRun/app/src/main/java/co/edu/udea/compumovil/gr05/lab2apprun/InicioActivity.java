package co.edu.udea.compumovil.gr05.lab2apprun;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr05.lab2apprun.dbapprun.DBAppRun;
import co.edu.udea.compumovil.gr05.lab2apprun.dbapprun.DBHelper;
import co.edu.udea.compumovil.gr05.lab2apprun.dbapprun.TableColumnsUser;
import co.edu.udea.compumovil.gr05.lab2apprun.model.Usuario;

public class InicioActivity extends AppCompatActivity implements View.OnClickListener {
    EditText txtUsuario;
    EditText txtContrasena;
    Button btnEntrar;
    Button btnRegistro;
    DBHelper dbHelper;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferencias = getSharedPreferences("PreferenciasUsuario",Context.MODE_PRIVATE);
        String correo = preferencias.getString("usuario", "usuarioDefecto");
        if ("usuarioDefecto".compareTo(correo) == 0) {
            setToolbar();

            dbHelper = new DBHelper(this);
            dbHelper.insertInitialDates();

            btnEntrar = (Button) findViewById(R.id.btn_entrar);
            btnRegistro = (Button) findViewById(R.id.btn_registro);
            txtUsuario = (EditText) findViewById(R.id.txt_usuario);
            txtContrasena = (EditText) findViewById(R.id.txt_contrasena);
            btnRegistro.setOnClickListener(this);
            btnEntrar.setOnClickListener(this);
        }else{
            Intent intentEntrar = new Intent(this, NavigationDrawerActivity.class);
            startActivity(intentEntrar);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_entrar:
                String usuario;
                String contrasena;
                ArrayList<Usuario> listaUsuario;
                // Extraer los datos ingresados
                usuario = txtUsuario.getText().toString();
                contrasena = txtContrasena.getText().toString();

                // Verificar que se ingresen los datos
                if ("".compareTo(usuario) == 0) {
                    String mensaje = getResources().getString(R.string.no_texto) + ": " +
                            getResources().getString(R.string.usuario);
                    Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("".compareTo(contrasena) == 0) {
                    String mensaje = getResources().getString(R.string.no_texto) + ": " +
                            getResources().getString(R.string.contrasena);
                    Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                    return;
                }
                listaUsuario = dbHelper.consultarUsuario(dbHelper,usuario, contrasena);
                if (listaUsuario.isEmpty()) {
                    String mensaje = getResources().getString(R.string.no_usuario);
                    Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Bundle parametros = new Bundle();
                    parametros.putString("usuario", listaUsuario.get(0).getUsuario());
                    parametros.putString("contrasena", listaUsuario.get(0).getContrasena());
                    parametros.putString("email", listaUsuario.get(0).getEmail());
                    parametros.putByteArray("foto", listaUsuario.get(0).getFoto());
                    SharedPreferences preferencias = getSharedPreferences("PreferenciasUsuario", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferencias.edit();
                    editor.putString("usuario", listaUsuario.get(0).getUsuario());
                    editor.putString("email", listaUsuario.get(0).getUsuario());
                    editor.commit();
                    Intent intentEntrar = new Intent(this, NavigationDrawerActivity.class);
                    intentEntrar.putExtras(parametros);
                    startActivity(intentEntrar);
                }
                break;

            case R.id.btn_registro:
                Intent intentRegistro = new Intent(this, RegistroActivity.class);
                startActivity(intentRegistro);
                break;
        }
    }

    private void setToolbar() {
        //Crea el widget Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        if (toolbar != null) {
            //Referencia la ActionBar como Toolbar
            setSupportActionBar(toolbar);
            //Atributos de la Toolbar
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setIcon(R.mipmap.logo_grupo5);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Inicio Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://co.edu.udea.compumovil.gr05.lab2apprun/http/host/path")
        );
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Inicio Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://co.edu.udea.compumovil.gr05.lab2apprun/http/host/path")
        );
    }
}
