package co.edu.udea.compumovil.gr05.lab3pomodoro;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG_NUM_TOMATE = "TAG_TOMATE";
    private final String TAG_ESTADO = "TAG_ESTADO";

    private TextView lblActividad;
    private TextView lblContador;
    private Button btnIniciar;
    private Button btnReiniciar;

    private int numeroTomates;
    private int estado;  //0: Tomate ; 1: Short ; 2: Long
    private int shortBreak;
    private int longBreak;
    private boolean isDebug;
    private final int tiempoTomate = 25;
    private int tiempo;
    private StringBuilder texto;
    private int valueVolumen;
    private int valueVibracion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.pomodoro);

        setToolbar();

        lblActividad = (TextView) findViewById(R.id.lbl_actividad);
        lblContador = (TextView) findViewById(R.id.lbl_contador);
        btnIniciar = (Button) findViewById(R.id.btn_iniciar);
        btnReiniciar = (Button) findViewById(R.id.btn_reiniciar);

        btnIniciar.setOnClickListener(this);
        btnReiniciar.setOnClickListener(this);

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
        valueVolumen = preferencesSettings.getInt(ConfiguracionActivity.TAG_VOLUMEN,
                ConfiguracionActivity.TAG_VOLUMEN_DEFECTO);
        valueVibracion = preferencesSettings.getInt(ConfiguracionActivity.TAG_VIBRACION,
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
                btnIniciar.setEnabled(false);
                btnIniciar.setBackgroundColor(getResources().getColor(R.color.gray_button));
                intent.setAction(CounterService.ACTION_SERVICE_RUN);
                intent.putExtra(CounterService.TAG_DEBUG, isDebug);
                intent.putExtra(CounterService.TAG_TIEMPO,tiempo);
                intent.putExtra(CounterService.TAG_ESTADO_TIEMPO,estado);
                intent.putExtra(ConfiguracionActivity.TAG_VOLUMEN,valueVolumen);
                intent.putExtra(ConfiguracionActivity.TAG_VIBRACION, valueVibracion);
                startService(intent);
                break;

            case R.id.btn_reiniciar:

                stopService(intent);
                numeroTomates = 0;
                estado = 0;
                tiempo = tiempoTomate;
                formatearTextoTiempo();
                lblContador.setText(texto.toString());
                lblActividad.setText(getResources().getString(R.string.tomate));
                btnIniciar.setEnabled(true);
                btnIniciar.setBackgroundColor(getResources().getColor(R.color.green_button));
                break;
        }
    }

    private void ejecutarNotificacion(){

        //Construcción de notificación
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_tomato);

        switch (estado){
            case 0:
                builder.setContentTitle(getResources().getString(R.string.tomate_finalizado));
                builder.setContentText(getResources().getString(R.string.tomate_descanso));
                break;
            case 1:
                builder.setContentTitle(getResources().getString(R.string.short_finalizado));
                builder.setContentText(getResources().getString(R.string.continuar_tomate));
                break;
            case 2:
                builder.setContentTitle(getResources().getString(R.string.long_finalizado));
                builder.setContentText(getResources().getString(R.string.continuar_tomate));
                break;
        }
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, valueVolumen, 0);

        builder.setSound(soundUri);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        notification.defaults = Notification.DEFAULT_SOUND;
        notificationManager.notify(1, builder.build());
        Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        switch (valueVibracion){
            case 1:
                vibrator.vibrate(1000);
                break;
            case 2:
                vibrator.vibrate(3000);
                break;
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
                    lblActividad.setText(getResources().getString(R.string.long_break));
                }else{
                    estado = 1;
                    tiempo = shortBreak;
                    lblActividad.setText(getResources().getString(R.string.short_break));
                }
                break;
            case 1:
                estado = 0;
                tiempo = tiempoTomate;
                lblActividad.setText(getResources().getString(R.string.tomate));
                break;
            case 2:
                estado = 0;
                tiempo = tiempoTomate;
                numeroTomates = 0;
                lblActividad.setText(getResources().getString(R.string.tomate));
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(TAG_NUM_TOMATE,numeroTomates);
        outState.putInt(TAG_ESTADO, estado);
        super.onSaveInstanceState(outState);
    }

    private void setToolbar() {
        //Crea el widget Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        //Referencia la ActionBar como Toolbar
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            //Atributos de la Toolbar
            ab.setTitle(R.string.pomodoro);
            ab.setIcon(R.mipmap.logo_grupo5);
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
                    btnIniciar.setEnabled(true);
                    btnIniciar.setBackgroundColor(getResources().getColor(R.color.green_button));
                    ejecutarNotificacion();
                    procesarEstado();
                    formatearTextoTiempo();
                    lblContador.setText(texto.toString());
                    break;
            }
        }
    }
}
