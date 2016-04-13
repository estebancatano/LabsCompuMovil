package co.edu.udea.compumovil.gr05.lab3pomodoro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView lblContador;
    private Button btnIniciar;
    private Button btnReiniciar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.pomodoro);

        lblContador = (TextView) findViewById(R.id.lbl_contador);
        btnIniciar = (Button) findViewById(R.id.btn_iniciar);
        btnReiniciar = (Button) findViewById(R.id.btn_reiniciar);

        btnIniciar.setOnClickListener(this);
        btnReiniciar.setOnClickListener(this);
    }




    //Settings
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_conf:
                Intent intent = new Intent(this, ConfiguracionActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Mostrar settings en la Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_opciones, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_iniciar:
                break;

            case R.id.btn_reiniciar:
                break;
        }
    }
}
