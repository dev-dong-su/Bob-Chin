package com.example.bobchin.Networking;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpGet extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection con = null;
        BufferedReader reader = null;
        StringBuffer returnString = null;
        try{
            URL url = new URL(strings[0]);
            con = (HttpURLConnection)url.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.connect();

            InputStream stream = con.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            returnString = new StringBuffer();
            String line = "";

            while((line = reader.readLine()) != null){
                returnString.append(line);
            }

            return returnString.toString();

        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(con !=null){
                con.disconnect();
            }
            try{
                if(reader !=null){
                    reader.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return returnString.toString();
    }
    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
        Log.d("returnString", result);
    }

}
