package com.bdg.telkom.operasional;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by lacorp on 5/14/2016.
 */
public class tesAct extends AppCompatActivity{

    private EditText mCarName;
    private EditText mNopol;
    private EditText mAsal;
    private EditText mTujuan;
    private EditText mPeminjam;

    SessionManager manager;

    public static final String NAMA_MOBIL = "NAMA_MOBIL";
    private Button mStartTrack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_activity);
        mCarName = (EditText) findViewById(R.id.car);
        mNopol = (EditText) findViewById(R.id.nopol);
        mAsal = (EditText) findViewById(R.id.asal);
        mTujuan = (EditText) findViewById(R.id.tujuan);
        mPeminjam = (EditText) findViewById(R.id.peminjam);
        mStartTrack = (Button) findViewById(R.id.action_form);

        Toast toast = Toast.makeText(getApplicationContext(),"Please input data trip :)",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
        toast.show();
        manager = new SessionManager();

        manager.setPreferences(tesAct.this,"status","1");

        String status = manager.getPreferences(tesAct.this, "status");
        Log.d("status", status);

//        mStartTrack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                nama_mobil = mCarName.getText().toString();
//                nopol = mNopol.getText().toString();
//                asal = mAsal.getText().toString();
//                tujuan = mTujuan.getText().toString();
//                peminjam = mPeminjam.getText().toString();
//
//
//                insert(nama_mobil,nopol,asal,tujuan,peminjam);
//                Toast.makeText(getApplicationContext(),"dsd"+nama_mobil,Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    public void insertData(View view){
        String nama_mobil = mCarName.getText().toString();
        String nopol = mNopol.getText().toString();
        String  asal = mAsal.getText().toString();
        String  tujuan = mTujuan.getText().toString();
        insert(nama_mobil, nopol,asal,tujuan);
    }
    private void insert(final String nama_mobil, final String nopol,final String asal,final String tujuan){
        class InsertAsync extends AsyncTask<String, Void, String>{
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(tesAct.this, "Please wait ..", "Loading");
            }

            @Override
            protected String doInBackground(String... params) {
                String nama_mobil = params[0];
                String nopol = params[1];
                String asal = params[2];
                String tujuan = params[3];

                InputStream is= null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("nama_mobil", nama_mobil));
                nameValuePairs.add(new BasicNameValuePair("nopol", nopol));
                nameValuePairs.add(new BasicNameValuePair("asal", asal));
                nameValuePairs.add(new BasicNameValuePair("tujuan", tujuan));


                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://cerita-aje.esy.es/insert.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity= response.getEntity();
                    is= entity.getContent();
                    Log.e("pass 1", "connection success ");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"),8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line= reader.readLine())!=null){
                        sb.append(line+"\n");
                    }
                    result = sb.toString();
                }catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i(result,"result " +result);
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                loadingDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Data Inserted",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(tesAct.this, StartService.class);
                startActivity(intent);

            }
        }
        InsertAsync ia = new InsertAsync();
        ia.execute(nama_mobil, nopol,asal,tujuan);
    }

    public void skipForm(View view){
        Intent intent = new Intent(tesAct.this, StartService.class);
        startActivity(intent);
    }

    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
    }

}


