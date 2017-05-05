package com.bdg.telkom.broadcast.t_satu.temp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bdg.telkom.broadcast.R;
import com.bdg.telkom.broadcast.t_satu.AlarmReceiver;

public class MainActivity extends AppCompatActivity {

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;
        Intent alarm = new Intent(this.context, AlarmReceiver.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(this.context,0,alarm,PendingIntent.FLAG_NO_CREATE)!=null);
        if (alarmRunning == false){
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context,0, alarm,0);
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(),5000, pendingIntent);
        }
    }
}
