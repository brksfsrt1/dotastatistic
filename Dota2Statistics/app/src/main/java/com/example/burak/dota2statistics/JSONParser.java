package com.example.burak.dota2statistics;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by burak on 03.08.2015.
 */
public class JSONParser {
    static JSONObject jObj = null;
    static String result ="";
    static InputStream inputStream;

    public JSONParser(){

    }

    public JSONObject getJSON(String url){
        String result ="";
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);



        try{
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder builder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                builder.append(line + "\n");
            }
            result = builder.toString();
            JSONObject jObj = new JSONObject(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finally{
            try{
                if(inputStream != null)
                    inputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                return jObj;
            }
        }
    }
}
