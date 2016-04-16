package co.edu.udea.compumovil.gr05.lab3pomodoro;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.app.NotificationCompat;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class CounterService extends Service {

    public static final String ACTION_SERVICE_RUN = "co.edu.udea.compumovil.gr05.RUN_SERVICE";
    public static final String ACTION_SERVICE_EXIT = "co.edu.udea.compumovil.gr05.EXIT_SERVICE";
    public static final String TAG_TIEMPO = "TIEMPO";
    public static final String TAG_DEBUG = "DEBUG";
    public static final String TAG_ESTADO_TIEMPO = "ESTADO_TIEMPO";

    private int tiempo;
    private boolean isDebug;
    private CounterThread mCounter;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null){
            final String action = intent.getAction();
            if (ACTION_SERVICE_RUN.equals(action)){
                tiempo = intent.getIntExtra(TAG_TIEMPO, 0);
                isDebug = intent.getBooleanExtra(TAG_DEBUG, false);
                mCounter = new CounterThread(tiempo,isDebug,this);
                mCounter.start();
            }
        }
        return START_STICKY;
    }

    @Override
    public boolean stopService(Intent name) {
        mCounter.setFlag(false);
        return super.stopService(name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCounter.setFlag(false);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
