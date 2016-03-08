package co.edu.udea.compumovil.gr05.lab2apprun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Crea el widget Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        if(toolbar != null) {
            //Referencia la ActionBar como Toolbar
            setSupportActionBar(toolbar);
            //Atributos de la Toolbar
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setIcon(R.mipmap.logo_grupo5);
        }

        EditText txtUsuario;
        EditText txtContrasena;
        Button btnEntrar;
        Button btnRegistro;

        btnEntrar = (Button) findViewById(R.id.btn_entrar);
        btnRegistro = (Button) findViewById(R.id.btn_registro);

        btnRegistro.setOnClickListener(this);
        btnEntrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_entrar:  break;

            case R.id.btn_registro:
                Intent intent = new Intent(this, RegistroActivity.class);
                startActivity(intent);
                this.finish();
                break;
        }
    }
}
