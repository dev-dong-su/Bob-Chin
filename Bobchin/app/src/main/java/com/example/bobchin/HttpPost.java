package com.example.bobchin;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPost extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
        try{
            return POST(strings[0],strings[1]);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private String POST(String requesturl,String string) throws IOException{
        InputStream inputStream = null;
        String returnString = "";
        try{
            URL url =  new URL(requesturl);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Accept","application/json");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.connect();

            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(string);
            wr.flush();
            wr.close();

            InputStream stream = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while((line = reader.readLine()) != null)
                buffer.append(line);

            returnString = buffer.toString();
            Log.d("returnString",returnString);

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                inputStream.close();
            }
        }

        return returnString;
    }
}