package co.edu.udea.compumovil.gr05.lab3pomodoro;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by joluditru on 15/04/2016.
 */
public class CounterThread extends Thread {
    private int tiempo;
    private boolean isDebug;
    private Context mContext;
    private boolean flag;

    public CounterThread(int t, boolean d, Context c) {
        tiempo = t;
        isDebug = d;
        mContext = c;
        flag = true;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        StringBuilder texto;
        StringBuilder textoMinutos;
        StringBuilder textoSegundos;
        try {
            Intent intentService = new Intent(CounterService.ACTION_SERVICE_RUN);
            if (!isDebug) {
                for (int minutos = tiempo - 1; minutos >= 0; minutos--) {
                    textoMinutos = new StringBuilder();
                    if (minutos > 9) {
                        textoMinutos.append(minutos);
                    } else {
                        textoMinutos.append(0);
                        textoMinutos.append(minutos);
                    }
                    textoMinutos.append(":");
                    for (int segundos = 59; segundos >= 0; segundos--) {
                        if (flag) {
                            Thread.sleep(1000);
                            if (flag) {
                                textoSegundos = new StringBuilder();
                                if (segundos > 9) {
                                    textoSegundos.append(segundos);
                                } else {
                                    textoSegundos.append(0);
                                    textoSegundos.append(segundos);
                                }
                                texto = new StringBuilder();
                                texto.append(textoMinutos.toString());
                                texto.append(textoSegundos.toString());
                                intentService.putExtra(CounterService.TAG_ESTADO_TIEMPO, texto.toString());
                                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intentService);
                            }
                        }
                    }
                }
            } else {
                textoSegundos = new StringBuilder(":00");
                for (int segundos = tiempo - 1; segundos >= 0; segundos--) {
                    if (flag) {
                        Thread.sleep(1000);
                        if (flag) {
                            textoMinutos = new StringBuilder();
                            if (segundos > 9) {
                                textoMinutos.append(segundos);
                            } else {
                                textoMinutos.append(0);
                                textoMinutos.append(segundos);
                            }
                            texto = new StringBuilder();
                            texto.append(textoMinutos.toString());
                            texto.append(textoSegundos.toString());
                            intentService.putExtra(CounterService.TAG_ESTADO_TIEMPO, texto.toString());
                            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intentService);
                        }
                    }
                }
            }
            if (flag){
                Intent intent = new Intent(CounterService.ACTION_SERVICE_EXIT);
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
