package com.bdg.telkom.operasional;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by lacorp on 5/15/2016.
 * references
 * bug session
 * http://www.androidwarriors.com/2015/12/session-management-in-android-example.html
 */
public class Splash extends AppCompatActivity {
    SessionManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        manager = new SessionManager();

        Thread background = new Thread(){
            public void run(){
                try {
                    /*Thread will sleep for 2 seconds*/
                    sleep(3*1000);

                    String status = manager.getPreferences(Splash.this,"status");
                    Log.d("status", status);
                    if (status.equals("1")){
                        Intent i = new Intent(Splash.this, tesAct.class);
                        Toast.makeText(getApplication(),"You got no session to this",Toast.LENGTH_SHORT).show();
                        startActivity(i);
                    }else if(status.equals("0")) {
                        Intent i = new Intent(Splash.this, Begin.class);
                        Toast.makeText(getApplication(),"Succes, Got your session Login",Toast.LENGTH_SHORT).show();
                        startActivity(i);
                    }
                    /*remove activity*/
                    finish();
                }catch (Exception e){

                }
            }
        };

        /*start thread*/
        background.start();
    }

    public void onBackPressed(){
     super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); /*Changging*/
        startActivity(intent);
        finish();
        System.exit(0);
    }
}
