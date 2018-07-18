package com.jobdetails.shant.getjobdetails;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**Created for
 * making httpconnection with server.
 * sending mobile details as json to server and receiving json response(test cases details) from server.
 * */
public class FetchData extends AsyncTask<String,Void,String> {

    String data="";  // String data for response json parsing.
    Object cntxt;

    boolean isFetchCompleted;
    public FetchData(Object cntxt){
        this.cntxt = cntxt;
    }

    private String TAG;

    public boolean isFetchCompleted(){
        return isFetchCompleted;
    }
    @Override
    protected String doInBackground(String... params) {
        try {
            isFetchCompleted = false;
            Log.d(TAG, "In doinback");
            URL url = new URL(params[0]);       // params[0] contains url from senddatatoserver fn. in MainActivity.java
           Log.d(TAG, "get url");

           HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
           Log.d(TAG, "after post ");

           BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));
           out.write(params[1]);              // params[1] contains string Json to be sent as request to server with url
           out.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));   //To read response

            String line = "";
            Log.d(TAG, "Before loop");
            while(line != null)/*bufferedReader.ready()*/{
                line =bufferedReader.readLine();
                if(line!=null)                                  //For avoiding to add null in String data at last loop run
                    data = data + line;
                }
            isFetchCompleted = true;
            }
            catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(isFetchCompleted == false) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return data;                             // Returns String data to onPostExecute
    }

    @Override
    protected void onPostExecute(String avoid) {

        super.onPostExecute(avoid);


        //MainActivity.jsontovalues(data);
        ((MainActivity)cntxt).jsontovalues(data); //String data as parameter to jsontovalues fn. in MainActivity.java
    }
}
