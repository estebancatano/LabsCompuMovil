package co.edu.udea.compumovil.gr05.lab2apprun;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;

import co.edu.udea.compumovil.gr05.lab2apprun.dbapprun.DBHelper;
import co.edu.udea.compumovil.gr05.lab2apprun.model.Usuario;

public class InicioActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText txtUsuario;
    private EditText txtContrasena;
    private Button btnEntrar;
    private Button btnRegistro;
    private DBHelper dbHelper;

    public static final String TAG_USUARIO = "Usuario";
    public static final String TAG_CONTRASENA = "Contrasena";
    public static final String TAG_CORREO = "Correo";
    public static final String TAG_FOTO = "Foto";
    public static final String TAG_PREFERENCIAS = "PreferenciasUsuario";
    public static final String TAG_USUARIO_DEFECTO = "usuarioDefecto";
    public static int REQUEST_CODE = 5;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferencias = getSharedPreferences(TAG_PREFERENCIAS,Context.MODE_PRIVATE);
        String correo = preferencias.getString(TAG_USUARIO, TAG_USUARIO_DEFECTO);
        if (TAG_USUARIO_DEFECTO.compareTo(correo) == 0) {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra(InicioActivity.TAG_USUARIO)) {
               txtUsuario.setText(data.getExtras().getString(InicioActivity.TAG_USUARIO));
            }
            if (data.hasExtra(InicioActivity.TAG_CONTRASENA)) {
                txtContrasena.setText(data.getExtras().getString(InicioActivity.TAG_CONTRASENA));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_entrar:
                String usuario;
                String contrasena;
                Usuario usuarioDB;
                // Extraer los datos ingresados
                usuario = txtUsuario.getText().toString();
                contrasena = txtContrasena.getText().toString();

                // Verificar que se ingresen los datos
                if ("".compareTo(usuario) == 0) {
                    String mensaje = getResources().getString(R.string.no_texto) + ": " +
                            getResources().getString(R.string.usuario);
                    Snackbar.make(v, mensaje, Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if ("".compareTo(contrasena) == 0) {
                    String mensaje = getResources().getString(R.string.no_texto) + ": " +
                            getResources().getString(R.string.contrasena);
                    Snackbar.make(v, mensaje, Snackbar.LENGTH_SHORT).show();
                    return;
                }
                usuarioDB = dbHelper.consultarUsuarioInicio(usuario, contrasena);
                if (usuarioDB == null) {
                    String mensaje = getResources().getString(R.string.no_usuario);
                    Snackbar.make(v, mensaje, Snackbar.LENGTH_SHORT).show();
                    return;
                } else {
                    Bundle parametros = new Bundle();
                    parametros.putString(TAG_USUARIO, usuarioDB.getUsuario());
                    parametros.putString(TAG_CONTRASENA, usuarioDB.getContrasena());
                    parametros.putString(TAG_CORREO, usuarioDB.getEmail());
                    parametros.putByteArray(TAG_FOTO, usuarioDB.getFoto());
                    SharedPreferences preferencias = getSharedPreferences(TAG_PREFERENCIAS, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferencias.edit();
                    editor.putString(TAG_USUARIO, usuarioDB.getUsuario());
                    editor.putString(TAG_CORREO, usuarioDB.getEmail());
                    editor.commit();
                    Intent intentEntrar = new Intent(this, NavigationDrawerActivity.class);
                    intentEntrar.putExtras(parametros);
                    startActivity(intentEntrar);
                    this.finish();
                }
                break;

            case R.id.btn_registro:
                txtUsuario.setText(null);
                txtContrasena.setText(null);
                Intent intentRegistro = new Intent(this, RegistroActivity.class);
                startActivityForResult(intentRegistro, REQUEST_CODE);
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