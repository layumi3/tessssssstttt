package com.bdg.telkom.operasional;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText mUsernameView;
    private EditText mPasswordView;
    private Button mSignInButton;
    private Button mTes;

    public static final String USER_NAME = "USERNAME";

    SessionManager manager;
    String username;
    String password;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*set the login*/
        mUsernameView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
        mSignInButton = (Button) findViewById(R.id.btn_login);
        mTes = (Button) findViewById(R.id.tes);

    }

    public void invokeLogin(View view){
        username = mUsernameView.getText().toString();
        password = mPasswordView.getText().toString();

        manager = new SessionManager();

        if (mUsernameView.getText().toString().isEmpty() || mPasswordView.getText().toString().isEmpty()){
            Toast.makeText(MainActivity.this, "Enter value in all field", Toast.LENGTH_SHORT).show();
        }else {
            manager.setPreferences(MainActivity.this,"status","1");

            String status = manager.getPreferences(MainActivity.this, "status");
            Log.d("status", status);
        }

        login(username,password);
    }

    private void login(final String username, final String password){
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(MainActivity.this, "Please wait ..", "Loading");

            }

            @Override
            protected String doInBackground(String... params) {
                String usename = params[0];
                String pass = params[1];

                InputStream is= null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username",usename));
                nameValuePairs.add(new BasicNameValuePair("password",pass));

                if (usename.isEmpty() || pass.isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter value in all field", Toast.LENGTH_SHORT).show();
                }
                manager.setPreferences(MainActivity.this,"status","1");

                String status = manager.getPreferences(MainActivity.this, "status");
                Log.d("status", status);


                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://cerita-aje.esy.es/logins.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity= response.getEntity();
                    is= entity.getContent();

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
                Log.i(result,"restult ya" +result);
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                String s = result.trim();
                loadingDialog.dismiss();
                if(s.equalsIgnoreCase("success")){
                    Intent intent = new Intent(MainActivity.this, tesAct.class);
                    intent.putExtra(USER_NAME, username);
                    finish();
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"Invalid username or password",Toast.LENGTH_SHORT).show();
                }
                Log.i(username,"ini user" + username + password);

            }
        }
        LoginAsync la = new LoginAsync();
        la.execute(username, password);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*Infplate the menu; this adds items to the action bar if it is present.*/
//        getMenuInflater().inflate(R.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*Handle action bar item clicks here, the action bar will automatically handle clicks on the home/up button, so long as you specify a parent activity in androidManifest.xml */
        int id = item.getItemId();

        /*noinspection SimplifiableIfStatement*/
//        if (id==R.id.action_setting){
//            return true;
//        }
        return super.onOptionsItemSelected(item);
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