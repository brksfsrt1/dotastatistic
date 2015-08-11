package com.example.burak.dota2statistics;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    public static final String STEAM_KEY = "44FD2324B81A18F255A49BF33489C97A";
    public static final String EXTRA_MESSAGE = "steamId";

    public static String steamId;
    public static String checkIdUrl;

    //the status of steamID
    public static final int SUCCESS = 1;
    public static final int WRONG_ID = 2;
    public static final int EXCEPTION_ERROR = 3;

    private  Context mContext = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!isNetworkAvailable(this)) {
            Toast.makeText(this,"No Internet connection",Toast.LENGTH_LONG).show();
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void  sendSteamId(View view) {
        EditText text = (EditText)findViewById(R.id.steamIdText);
        steamId = text.getText().toString();

        Uri.Builder builder = new Uri.Builder();

        //TODO: IT WILL BE MODIFIED
        builder.scheme("https")
                .authority("api.steampowered.com")
                .appendPath("IDOTA2Match_570")
                .appendPath("GetMatchHistory")
                .appendPath("V001")
                .appendQueryParameter("key", STEAM_KEY)
                .appendQueryParameter("account_id", steamId);
        checkIdUrl = builder.build().toString();

        new CheckIdTask().execute();

    }

    //checks if internet connection is available
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }

    //this asynctask checks the steam id if it exists
    public class CheckIdTask extends AsyncTask<Void,Void,Integer>{
        @Override
        protected void onPreExecute(){

        }

        protected Integer doInBackground(Void... nothing){
            HttpClient client = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(checkIdUrl);
            Integer result;

            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("id", steamId));

                httpPost.setEntity(new UrlEncodedFormEntity(params));

                //make post request
                HttpResponse response = client.execute(httpPost);
                String responseBody = EntityUtils.toString(response.getEntity());

                if(responseBody.compareToIgnoreCase("Error")!= 0) {
                    result = SUCCESS;
                }
                else
                    result = WRONG_ID;
            }
            catch(ClientProtocolException e){
                    result = EXCEPTION_ERROR;
            }
            catch(IOException e){
                    result = EXCEPTION_ERROR;
            }

            return result;
        }

        protected void onPostExecute(Integer result){
            if(result == SUCCESS){
                Intent intent  = new Intent(mContext,DisplayPastMatchesActivity.class);
                intent.putExtra(EXTRA_MESSAGE,steamId);
                startActivity(intent);
            }

            else if(result == WRONG_ID){
                CharSequence text = "The id is not found.";
                Toast toast = Toast.makeText(mContext,text,Toast.LENGTH_SHORT);
                toast.show();
            }
            else if(result == EXCEPTION_ERROR){
                CharSequence text = "Exception is thrown";
                Toast toast = Toast.makeText(mContext,text,Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}



