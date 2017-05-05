package com.bdg.telkom.broadcast.t_satu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bdg.telkom.broadcast.t_satu.BackgroundService;

/**
 * Created by lacorp on 6/19/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent background = new Intent(context, BackgroundService.class);
        context.startService(background);
    }
}
