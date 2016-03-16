package co.edu.udea.compumovil.gr05.lab2apprun;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr05.lab2apprun.dbapprun.DBHelper;
import co.edu.udea.compumovil.gr05.lab2apprun.model.Usuario;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnGuardar;
    private EditText txtUsuario;
    private EditText txtContrasena;
    private EditText txtCorreo;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btnGuardar = (Button) findViewById(R.id.btn_guardar);
        txtUsuario = (EditText) findViewById(R.id.txt_usuario);
        txtContrasena = (EditText) findViewById(R.id.txt_contrasena);
        txtCorreo = (EditText) findViewById(R.id.txt_correo);
        setToolbar();

        btnGuardar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String usuario;
        String contrasena;
        String correo;
        ArrayList<Usuario> listaUsuario;
        // Extraer los datos ingresados
        usuario = txtUsuario.getText().toString();
        contrasena = txtContrasena.getText().toString();
        correo = txtUsuario.getText().toString();
        boolean existeUsuario = false;
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
        if ("".compareTo(correo) == 0) {
            String mensaje = getResources().getString(R.string.no_texto) + ": " +
                    getResources().getString(R.string.correo);
            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
            return;
        }
        existeUsuario = dbHelper.consultarUsuarioRegistro(usuario);
        if (existeUsuario) {
            String mensaje = getResources().getString(R.string.no_usuario);
            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
            return;
        } else {
            Bundle parametros = new Bundle();
            //parametros.putString(InicioActivity.TAG_USUARIO, listaUsuario.get(0).getUsuario());
            //parametros.putString(InicioActivity.TAG_CONTRASENA, listaUsuario.get(0).getContrasena());
            Intent intentEntrar = new Intent(this, NavigationDrawerActivity.class);
            intentEntrar.putExtras(parametros);
            startActivity(intentEntrar);
        }
    }

    private void setToolbar() {
        //Crea el widget Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        if(toolbar != null) {
            //Referencia la ActionBar como Toolbar
            setSupportActionBar(toolbar);
            //Atributos de la Toolbar
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setIcon(R.mipmap.logo_grupo5);

        }
    }
}
