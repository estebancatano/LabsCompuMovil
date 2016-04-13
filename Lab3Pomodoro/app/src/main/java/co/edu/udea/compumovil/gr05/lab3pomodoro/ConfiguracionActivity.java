package co.edu.udea.compumovil.gr05.lab3pomodoro;

import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class ConfiguracionActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private SeekBar volumenSeekBar;
    private AudioManager audioManager;
    private RadioGroup rgVibracion;
    private RadioButton rbVibracion;
    private RadioGroup rgShortBreak;
    private RadioButton rbShortBreak;
    private RadioGroup rgLongBreak;
    private RadioButton rbLongBreak;

    //Testing
    private TextView lblVolumen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        setTitle(R.string.configuracion);

        volumenSeekBar = (SeekBar) findViewById(R.id.seekBar);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        /*
        int streamMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volumenSeekBar.setMax(streamMaxVolume);

        int streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumenSeekBar.setProgress(streamVolume);
        */
        
        volumenSeekBar.setOnSeekBarChangeListener(this);


        rgVibracion = (RadioGroup) findViewById(R.id.rg_vibracion);
        rgShortBreak = (RadioGroup) findViewById(R.id.rg_short_break);
        rgLongBreak = (RadioGroup) findViewById(R.id.rg_long_break);


        //Testing
        lblVolumen = (TextView) findViewById(R.id.lbl_volumen);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("PAUSE", "onPause");
        int vibracionChecked;
        int shortChecked;
        int longChecked;
        //Valor de Vibración
        vibracionChecked = rgVibracion.getCheckedRadioButtonId();
        rbVibracion = (RadioButton) findViewById(vibracionChecked);
        Toast.makeText(this, rbVibracion.getText(), Toast.LENGTH_SHORT).show();

        //Valor de ShortBreak
        shortChecked = rgShortBreak.getCheckedRadioButtonId();
        rbShortBreak = (RadioButton) findViewById(shortChecked);
        Toast.makeText(this, rbShortBreak.getText(), Toast.LENGTH_SHORT).show();

        //Valor de LongBreak
        longChecked = rgLongBreak.getCheckedRadioButtonId();
        rbLongBreak = (RadioButton) findViewById(longChecked);
        Toast.makeText(this, rbLongBreak.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        lblVolumen.setText(String.valueOf(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
