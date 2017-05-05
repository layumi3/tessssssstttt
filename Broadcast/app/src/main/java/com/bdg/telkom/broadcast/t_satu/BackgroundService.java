package com.bdg.telkom.broadcast.t_satu;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.*;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lacorp on 6/19/2016.
 */
public class BackgroundService extends Service {


    public static final long NOTIFY_INTERVAL = 5 * 1000; // 10 seconds

    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;
    private boolean isRunning;
    private Context context;
    private Thread backgroundThread;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new Thread(myTask);

        if (mTimer != null){
            mTimer.cancel();
        }else {
            mTimer = new Timer();
        }
        mTimer.scheduleAtFixedRate(new TimeDisplayTask(),0,NOTIFY_INTERVAL);

    }


    class TimeDisplayTask extends TimerTask{

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {

                    Toast.makeText(getApplicationContext(), getDateTime(),Toast.LENGTH_SHORT).show();
                }
            });
        }
        private String getDateTime(){
            SimpleDateFormat df = new SimpleDateFormat("[yyyy/MM/dd - HH:mm:ss]");
            return df.format(new Date());
        }
    }

    private Runnable myTask = new Runnable() {
        @Override
        public void run() {
            /*do something*/
//            Toast.makeText(getApplicationContext(),"toas",Toast.LENGTH_SHORT).show();
            Log.i("SS","thread task");
//            Intent i = new Intent(BackgroundService.class, Tampil.class);
//            startActivity(i);
            stopSelf();
        }
    };

    @Override
    public void onDestroy() {
        this.isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!this.isRunning){
            this.isRunning = true;
            this.backgroundThread.start();

        }
        return START_STICKY;
    }
}

