package com.example.bobchin.Networking;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.UUID;

public class HttpPostMultipart extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
        try{
            return POST(strings[0],strings[1]);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private String POST(String requesturl,String filePath) throws IOException{
        File file = new File(filePath);
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        FileInputStream fileInputStream = new FileInputStream(file);

        URL url = new URL(requesturl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        String boundary = UUID.randomUUID().toString();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

        DataOutputStream request = new DataOutputStream(connection.getOutputStream());

        request.writeBytes("--" + boundary + "\r\n");
        request.writeBytes("Content-Disposition: form-data; name=\"imagefile\"; filename=\"" + file.getName() + "\"\r\n\r\n");
        fileInputStream.read(bytes);
        request.write(bytes);
        request.writeBytes("\r\n");

        request.writeBytes("--" + boundary + "--\r\n");
        request.flush();

        InputStream stream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while((line = reader.readLine()) != null)
            buffer.append(line);

        String returnString = buffer.toString();

        return returnString;
    }
}
