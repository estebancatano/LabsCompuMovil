package co.edu.udea.compumovil.gr05.lab2apprun;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;

import co.edu.udea.compumovil.gr05.lab2apprun.dbapprun.DBHelper;

public class ActivityLogin extends AppCompatActivity implements View.OnClickListener {
    private EditText txtUsuario;
    private EditText txtContrasena;
    private Button btnEntrar;
    private Button btnRegistro;
    private DBHelper dbHelper;

    public static final String TAG_USUARIO = "Usuario";
    public static final String TAG_CONTRASENA = "Contrasena";
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
        setContentView(R.layout.activity_login);

        SharedPreferences preferencias = getSharedPreferences(TAG_PREFERENCIAS,Context.MODE_PRIVATE);
        String usuario = preferencias.getString(TAG_USUARIO, TAG_USUARIO_DEFECTO);
        if (TAG_USUARIO_DEFECTO.compareTo(usuario) == 0) {
            setToolbar();

            dbHelper = new DBHelper(this);

            btnEntrar = (Button) findViewById(R.id.btn_entrar);
            btnRegistro = (Button) findViewById(R.id.btn_registro);
            txtUsuario = (EditText) findViewById(R.id.txt_usuario);
            txtUsuario.requestFocus();
            txtContrasena = (EditText) findViewById(R.id.txt_contrasena);
            btnRegistro.setOnClickListener(this);
            btnEntrar.setOnClickListener(this);
        }else{
            Intent intentEntrar = new Intent(this, NavigationDrawerActivity.class);
            intentEntrar.putExtra(TAG_USUARIO,usuario);
            startActivity(intentEntrar);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra(ActivityLogin.TAG_USUARIO)) {
               txtUsuario.setText(data.getExtras().getString(ActivityLogin.TAG_USUARIO));
            }
            if (data.hasExtra(ActivityLogin.TAG_CONTRASENA)) {
                txtContrasena.setText(data.getExtras().getString(ActivityLogin.TAG_CONTRASENA));
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
                boolean existeUsuario;
                // Extraer los datos ingresados
                usuario = txtUsuario.getText().toString();
                contrasena = txtContrasena.getText().toString();

                //Obtiene el dato y valida el campo
                if (!validarCampo(usuario,R.string.usuario)){
                    return;
                }
                //Obtiene el dato y valida el campo
                if (!validarCampo(contrasena,R.string.contrasena)){
                    return;
                }
                existeUsuario = dbHelper.consultarUsuarioInicio(usuario,contrasena);
                if (!existeUsuario) {
                    String mensaje = getResources().getString(R.string.no_usuario);
                    Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG).show();
                    return;
                } else {
                    SharedPreferences preferencias = getSharedPreferences(TAG_PREFERENCIAS, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferencias.edit();
                    editor.putString(TAG_USUARIO, usuario);
                    editor.commit();
                    Intent intentEntrar = new Intent(this, NavigationDrawerActivity.class);
                    intentEntrar.putExtra(TAG_USUARIO,usuario);
                    startActivity(intentEntrar);
                    this.finish();
                }
                break;

            case R.id.btn_registro:
                txtUsuario.setText(null);
                txtContrasena.setText(null);
                Intent intentRegistro = new Intent(this, ActivityRegistro.class);
                startActivityForResult(intentRegistro, REQUEST_CODE);
                break;
        }
    }

    private void setToolbar() {
        //Crea el widget Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        //Referencia la ActionBar como Toolbar
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            //Atributos de la Toolbar
            ab.setTitle(R.string.title_login);
            ab.setIcon(R.mipmap.logo_grupo5);
        }
    }

    //Verifica si un campo de texto es vacío
    private boolean validarCampo(String campo, int referencia){
        String mensaje;
        boolean retorno = true;
        if (campo.trim().isEmpty()){
            mensaje = getResources().getString(R.string.no_texto)+ ": " + getResources().getString(referencia);
            Snackbar.make(findViewById(R.id.layout_login), mensaje, Snackbar.LENGTH_SHORT).show();
            retorno = false;
        }
        return retorno;
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
