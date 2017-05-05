package com.bdg.telkom.broadcast.t_satu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bdg.telkom.broadcast.R;

/**
 * Created by lacorp on 6/19/2016.
 */
public class Tampil extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        startService(new Intent(this, BackgroundService.class));
        startService(new Intent(this, SitePoint.class));
    }
}

