package com.bdg.telkom.broadcast.t_satu;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.bdg.telkom.broadcast.R;
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
public class Location extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String TAG ="Locations";
    private LocationRequest mLocationRequest;
    private DateFormat mLastUpdateTime;
    private GoogleApiClient mGoogleApiClient;

    public static final long NOTIFY_INTERVAL = 5 * 1000; // 10 seconds

    private boolean isRunning;
    private Context context;
    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(3000);
//        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        mLastUpdateTime = new DateFormat();
        String mLatitudeTextView = String.valueOf(location.getLatitude());
        String mLongitudeTextView = String.valueOf(location.getLongitude());
//        startService(new Intent(this, BackgroundService.class));
        Toast.makeText(this,"updated: "+mLastUpdateTime + mLatitudeTextView + " "+mLongitudeTextView,Toast.LENGTH_SHORT).show();

    }
//
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed error: " + connectionResult.getErrorCode());
    }
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect
                ();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//    @Override
//    public void onCreate() {
//        this.context = this;
//        this.isRunning = false;
////        this.backgroundThread = new Thread(myTask);
//
//        if (mTimer != null){
//            mTimer.cancel();
//        }else {
//            mTimer = new Timer();
//        }
//        mTimer.scheduleAtFixedRate(new TimeDisplayTask(),0,NOTIFY_INTERVAL);
//    }
//
//    class TimeDisplayTask extends TimerTask {
//
//        @Override
//        public void run() {
//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(getApplicationContext(), getDateTime(),Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//        private String getDateTime(){
//            SimpleDateFormat df = new SimpleDateFormat("[yyyy/MM/dd - HH:mm:ss]");
//            return df.format(new Date());
//        }
//    }
//
//    private Runnable myTask = new Runnable() {
//        @Override
//        public void run() {
//            /*do something*/
////            Toast.makeText(getApplicationContext(),"toas",Toast.LENGTH_SHORT).show();
//            Log.i("SS","thread task");
////            Intent i = new Intent(BackgroundService.class, Tampil.class);
////            startActivity(i);
//            stopSelf();
//        }
//    };
//
//    @Override
//    public void onDestroy() {
//
//        this.isRunning = false;
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (!this.isRunning){
//            this.isRunning = true;
//
//        }
//        return START_STICKY;
//    }
}
