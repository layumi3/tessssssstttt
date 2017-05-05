package com.bdg.telkom.operasional;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by lacorp on 5/15/2016.
 */
public class Finish  extends AppCompatActivity{

    SessionManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logout_activity);
        manager = new SessionManager();

        Button button = (Button)findViewById(R.id.btn_logout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                manager.setPreferences(Finish.this, "status","0");

                String status = manager.getPreferences(Finish.this, "status");
                Log.d("status", status);

            }
        });
    }
    //onBackpressed to stop user from going MainActivity to Login Screen on back button press
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
    }
}
