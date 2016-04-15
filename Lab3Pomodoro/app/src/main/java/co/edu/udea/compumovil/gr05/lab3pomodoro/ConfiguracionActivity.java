package co.edu.udea.compumovil.gr05.lab3pomodoro;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class ConfiguracionActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    public static final String TAG_PREFERENCIAS = "PreferenciasUsuario";
    public static final String TAG_VOLUMEN = "volumen";
    public static final int TAG_VOLUMEN_DEFECTO = 50;
    public static final String TAG_VIBRACION = "vibracion";
    public static final int TAG_VIBRACION_DEFECTO = 0;
    public static final String TAG_SHORT_BREAK = "short_break";
    public static final int TAG_SHORT_BREAK_DEFECTO = 2;
    public static final String TAG_LONG_BREAK = "long_break";
    public static final int TAG_LONG_BREAK_DEFECTO = 0;
    public static final String TAG_DEBUG = "debug";
    public static final boolean TAG_DEBUG_DEFECTO = false;

    private SeekBar volumenSeekBar;
    private AudioManager audioManager;
    private RadioGroup rgVibracion;
    private RadioButton rbVibracion;
    private RadioGroup rgShortBreak;
    private RadioButton rbShortBreak;
    private RadioGroup rgLongBreak;
    private RadioButton rbLongBreak;
    private CheckBox cbDebug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        setTitle(R.string.configuracion);

        rgVibracion = (RadioGroup) findViewById(R.id.rg_vibracion);
        rgShortBreak = (RadioGroup) findViewById(R.id.rg_short_break);
        rgLongBreak = (RadioGroup) findViewById(R.id.rg_long_break);
        cbDebug = (CheckBox) findViewById(R.id.cb_debug);

        SharedPreferences preferencesSettings = getSharedPreferences(TAG_PREFERENCIAS,
                Context.MODE_PRIVATE);
        int valueVolumen = preferencesSettings.getInt(TAG_VOLUMEN, TAG_VOLUMEN_DEFECTO);
        int valueVibracion = preferencesSettings.getInt(TAG_VIBRACION, TAG_VIBRACION_DEFECTO);
        int valueShortBreak = preferencesSettings.getInt(TAG_SHORT_BREAK, TAG_SHORT_BREAK_DEFECTO);
        int valueLongBreak = preferencesSettings.getInt(TAG_LONG_BREAK, TAG_LONG_BREAK_DEFECTO);
        boolean valueDebug = preferencesSettings.getBoolean(TAG_DEBUG, TAG_DEBUG_DEFECTO);

        //Seleccionar valueVolumen
        volumenSeekBar = (SeekBar) findViewById(R.id.seekBar);
        volumenSeekBar.setProgress(valueVolumen);
/*
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        int streamMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);b
        volumenSeekBar.setMax(streamMaxVolume);

        int streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumenSeekBar.setProgress(streamVolume);
*/

        //Seleccionar vibración
        if (valueVibracion != -1){
            rbVibracion = (RadioButton) rgVibracion.getChildAt(valueVibracion);
            rbVibracion.setChecked(true);
        }

        //Seleccionar short break
        if (valueShortBreak != -1){
            rbShortBreak = (RadioButton) rgShortBreak.getChildAt(valueShortBreak);
            rbShortBreak.setChecked(true);
        }

        //Seleccionar long break
        if (valueLongBreak != -1){
            rbLongBreak = (RadioButton) rgLongBreak.getChildAt(valueLongBreak);
            rbLongBreak.setChecked(true);
        }

        cbDebug.setChecked(valueDebug);

        //Listener
        volumenSeekBar.setOnSeekBarChangeListener(this);

    }

    @Override
    protected void onPause() {
        super.onPause();

        int vibracionChecked;
        int shortChecked;
        int longChecked;

        SharedPreferences preferencias = getSharedPreferences(TAG_PREFERENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        //Valor de Vibración
        vibracionChecked = rgVibracion.getCheckedRadioButtonId();
        rbVibracion = (RadioButton) findViewById(vibracionChecked);
        editor.putInt(TAG_VIBRACION, rgVibracion.indexOfChild(rbVibracion));

        //Valor de ShortBreak
        shortChecked = rgShortBreak.getCheckedRadioButtonId();
        rbShortBreak = (RadioButton) findViewById(shortChecked);
        editor.putInt(TAG_SHORT_BREAK,rgShortBreak.indexOfChild(rbShortBreak));

        //Valor de LongBreak
        longChecked = rgLongBreak.getCheckedRadioButtonId();
        rbLongBreak = (RadioButton) findViewById(longChecked);
        editor.putInt(TAG_LONG_BREAK,rgLongBreak.indexOfChild(rbLongBreak));

        //Valor volumen
        int index = volumenSeekBar.getProgress();
        editor.putInt(TAG_VOLUMEN, index);

        //Seleecion debug
        editor.putBoolean(TAG_DEBUG, cbDebug.isChecked());

        editor.commit();


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        SharedPreferences preferencias = getSharedPreferences(TAG_PREFERENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putInt(TAG_VOLUMEN,progress);
        editor.commit();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
}
