package com.bdg.telkom.operasional;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by lacorp on 5/15/2016.
 */
public class Begin extends AppCompatActivity {

    private Button mBegin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.begin_activity);
        mBegin= (Button)findViewById(R.id.goLogin);

        mBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent begin = new Intent(Begin.this, MainActivity.class );
                startActivity(begin);
            }
        });
    }
}
