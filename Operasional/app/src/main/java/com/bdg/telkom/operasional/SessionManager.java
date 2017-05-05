package com.bdg.telkom.operasional;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lacorp on 5/15/2016.
 * TODO set username/user_id for each session
 */
public class SessionManager {


    public void setPreferences(Context context, String key, String value){
        SharedPreferences.Editor editor = context.getSharedPreferences("TrackingCar", Context.MODE_PRIVATE).edit();
        editor.putString(key,value);
        editor.commit();

    }

    public String getPreferences(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences("TrackingCar", Context.MODE_PRIVATE);
        String position = prefs.getString(key, "");
        return position;
    }

}
