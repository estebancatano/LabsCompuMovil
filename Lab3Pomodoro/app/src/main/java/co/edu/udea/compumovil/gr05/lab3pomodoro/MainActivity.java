package co.edu.udea.compumovil.gr05.lab3pomodoro;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG_NUM_TOMATE = "Tomate";
    private final String TAG_ESTADO = "Estado";


    private TextView lblContador;
    private Button btnIniciar;
    private Button btnReiniciar;

    private int numeroTomates;
    private int estado;  //0: Tomate ; 1: Short ; 2: Long
    private int shortBreak;
    private int longBreak;
    private boolean isDebug;
    private int tiempoTomate;
    private int tiempo;
    private StringBuilder texto;

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

        tiempoTomate = 25;
        if (savedInstanceState != null){
            numeroTomates = savedInstanceState.getInt(TAG_NUM_TOMATE);
            estado = savedInstanceState.getInt(TAG_ESTADO);
            procesarEstado();
        }else{
            numeroTomates = 0;
            estado = 0;
            tiempo = tiempoTomate;
        }
        formatearTextoTiempo();
        lblContador.setText(texto);
        IntentFilter filter = new IntentFilter(CounterService.ACTION_SERVICE_RUN);
        filter.addAction(CounterService.ACTION_SERVICE_EXIT);

        CounterReceiver receiver = new CounterReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,filter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferencesSettings = getSharedPreferences(ConfiguracionActivity.TAG_PREFERENCIAS,
                Context.MODE_PRIVATE);
        int valueVolumen = preferencesSettings.getInt(ConfiguracionActivity.TAG_VOLUMEN,
                ConfiguracionActivity.TAG_VOLUMEN_DEFECTO);
        int valueVibracion = preferencesSettings.getInt(ConfiguracionActivity.TAG_VIBRACION,
                ConfiguracionActivity.TAG_VIBRACION_DEFECTO);
        int valueShortBreak = preferencesSettings.getInt(ConfiguracionActivity.TAG_SHORT_BREAK,
                ConfiguracionActivity.TAG_SHORT_BREAK_DEFECTO);
        int valueLongBreak = preferencesSettings.getInt(ConfiguracionActivity.TAG_LONG_BREAK,
                ConfiguracionActivity.TAG_LONG_BREAK_DEFECTO);
        boolean valueDebug = preferencesSettings.getBoolean(ConfiguracionActivity.TAG_DEBUG,
                ConfiguracionActivity.TAG_DEBUG_DEFECTO);
        shortBreak = valueShortBreak + 3;
        longBreak = 10 + (valueLongBreak * 5);
        isDebug = valueDebug;
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
        Intent intent = new Intent(this,CounterService.class);
        switch (v.getId()){
            case R.id.btn_iniciar:
                intent = new Intent(this,CounterService.class);
                intent.setAction(CounterService.ACTION_SERVICE_RUN);
                intent.putExtra(CounterService.TAG_DEBUG, isDebug);
                intent.putExtra(CounterService.TAG_TIEMPO,tiempo);
                startService(intent);
                break;

            case R.id.btn_reiniciar:
                numeroTomates = 0;
                estado = 0;
                tiempo = 25;
                formatearTextoTiempo();
                lblContador.setText(texto.toString());
                stopService(intent);
                break;
        }
    }



    // Broadcast que recibe la señal con el tiempo
    private class CounterReceiver extends BroadcastReceiver{
        public CounterReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case CounterService.ACTION_SERVICE_RUN:
                    lblContador.setText(intent.getStringExtra(CounterService.TAG_ESTADO_TIEMPO));
                    break;
                case CounterService.ACTION_SERVICE_EXIT:
                    procesarEstado();
                    formatearTextoTiempo();
                    lblContador.setText(texto.toString());
                    break;
            }
        }
    }

    private void formatearTextoTiempo(){
        texto = new StringBuilder();
        if (tiempo < 10){
            texto.append("0");
        }
        texto.append(tiempo);
        texto.append(":00");
    }

    private void procesarEstado(){
        switch (estado){
            case 0:
                numeroTomates = numeroTomates + 1;
                if (numeroTomates == 4){
                    estado = 2;
                    tiempo = longBreak;
                }else{
                    estado = 1;
                    tiempo = shortBreak;
                }
                break;
            case 1:
                estado = 0;
                tiempo = tiempoTomate;
                break;
            case 2:
                estado = 0;
                tiempo = tiempoTomate;
                numeroTomates = 0;
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(TAG_NUM_TOMATE,numeroTomates);
        outState.putInt(TAG_ESTADO, estado);
        super.onSaveInstanceState(outState);
    }
}
