package co.edu.udea.compumovil.gr05.lab3pomodoro;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.app.NotificationCompat;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class CounterService extends IntentService {

    public static final String ACTION_SERVICE_RUN = "co.edu.udea.compumovil.gr05.RUN_SERVICE";
    public static final String ACTION_SERVICE_EXIT = "co.edu.udea.compumovil.gr05.EXIT_SERVICE";
    public static final String TAG_TIEMPO = "TIEMPO";
    public static final String TAG_DEBUG = "DEBUG";
    public static final String TAG_ESTADO_TIEMPO = "ESTADO_TIEMPO";

    private int tiempo;
    private int estado;
    private boolean isDebug;

    public CounterService() {
        super("CounterService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null){
            final String action = intent.getAction();
            if (ACTION_SERVICE_RUN.equals(action)){
                tiempo = intent.getIntExtra(TAG_TIEMPO, 0);
                isDebug = intent.getBooleanExtra(TAG_DEBUG, false);
                ejecutarServicio();

                //Construcción de notificación
                estado = intent.getIntExtra(TAG_ESTADO_TIEMPO,0);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                builder.setSmallIcon(android.R.drawable.stat_notify_error);
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
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(1, builder.build());
            }
        }
    }

    private void ejecutarServicio() {
        StringBuilder texto;
        StringBuilder textoMinutos;
        StringBuilder textoSegundos;
        try{
            Intent intentService = new Intent(ACTION_SERVICE_RUN);
            if (!isDebug){
                for (int minutos=tiempo-1; minutos>=0  ;minutos--){
                    textoMinutos = new StringBuilder();
                    if(minutos > 9){
                        textoMinutos.append(minutos);
                    }else{
                        textoMinutos.append(0);
                        textoMinutos.append(minutos);
                    }
                    textoMinutos.append(":");
                    for (int segundos = 50; segundos >=0; segundos--) {
                        textoSegundos = new StringBuilder();
                        if(segundos > 9){
                            textoSegundos.append(segundos);
                        }else{
                            textoSegundos.append(0);
                            textoSegundos.append(segundos);
                        }
                        texto = new StringBuilder();
                        texto.append(textoMinutos.toString());
                        texto.append(textoSegundos.toString());
                        intentService.putExtra(TAG_ESTADO_TIEMPO, texto.toString());
                        LocalBroadcastManager.getInstance(this).sendBroadcast(intentService);
                        Thread.sleep(1000);
                    }
                }
            }else {
                textoSegundos = new StringBuilder(":00");
                for (int segundos=tiempo; segundos>=0;segundos--){
                    textoMinutos = new StringBuilder();
                    if(segundos > 9){
                        textoMinutos.append(segundos);
                    }else{
                        textoMinutos.append(0);
                        textoMinutos.append(segundos);
                    }
                    texto = new StringBuilder();
                    texto.append(textoMinutos.toString());
                    texto.append(textoSegundos.toString());
                    intentService.putExtra(TAG_ESTADO_TIEMPO, texto.toString());
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intentService);
                    Thread.sleep(1000);
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        Intent intentService = new Intent(ACTION_SERVICE_EXIT);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intentService);
    }
}
