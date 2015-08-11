package com.example.burak.dota2statistics;

import android.content.Intent;
import android.os.AsyncTask;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DisplayPastMatchesActivity extends ActionBarActivity {
    public static final String STEAM_KEY = "44FD2324B81A18F255A49BF33489C97A";
    public String steamId;
    public String userMatchHistoryURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        steamId = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        Uri.Builder builder = new Uri.Builder();
        //TODO: IT WILL BE MODIFIED
        builder.scheme("https")
                .authority("api.steampowered.com")
                .appendPath("IDOTA2Match_570")
                .appendPath("GetMatchHistory")
                .appendPath("V001")
                .appendQueryParameter("key", STEAM_KEY)
                .appendQueryParameter("account_id", steamId);
        userMatchHistoryURL = builder.build().toString();

        new FetchUserDataTask().execute();


        setContentView(R.layout.activity_display_past_matches);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_past_matches, menu);
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
    private class FetchUserDataTask extends AsyncTask<Void,Void,Boolean>{
        ArrayList<MatchData> matchDataList = new ArrayList<MatchData>();

        protected Boolean doInBackground(Void... params){

            JSONParser parser = new JSONParser();
            parser.getJSON(userMatchHistoryURL);

            JSONArray matches = new JSONArray();
            JSONObject obj = new JSONObject();
            obj  = parser.getJSON(userMatchHistoryURL);

            try {
                if(obj != null) {
                    matches = obj.getJSONArray("matches");

                    for (int i = 0; i < matches.length(); i++) {
                        MatchData data = new MatchData();

                        JSONObject match = matches.getJSONObject(i);

                        data.setHeroImageUrl(match.getString("hero_image"));
                        data.setDate(match.getString("date"));
                        data.setHeroName(match.getString("hero_name"));
                        data.setMatchId("match_id");

                        matchDataList.add(data);
                    }
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(aBoolean != true){
                Toast.makeText(getApplicationContext(),"cannot fetch data",Toast.LENGTH_SHORT).show();
            }
            else{

                ListView list = (ListView) findViewById(R.id.matchList);
                MatchDataAdapter adapter = new MatchDataAdapter(getApplicationContext(),R.layout.list_view,matchDataList);
                list.setAdapter(adapter);
            }
        }
    }
}
