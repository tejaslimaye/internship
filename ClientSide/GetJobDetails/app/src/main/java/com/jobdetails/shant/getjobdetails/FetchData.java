package com.jobdetails.shant.getjobdetails;

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

/**
 * Created by shant on 28-06-2018.
 */

public class FetchData extends AsyncTask<String,Void,String> {

    String dataParsed = "";
    String singleParsed ="";
    String data="";


    private String TAG;

    @Override
    protected String doInBackground(String... params) {
        try {
            Log.d(TAG, "In doinback");
            URL url = new URL(params[0]);
           Log.d(TAG, "get url");

           HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
           Log.d(TAG, "after post ");

           BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));
           out.write(params[1]);
           out.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            Log.d(TAG, "Before loop");
            while(line != null)/*bufferedReader.ready()*/{
               // Log.d(TAG, "In LOOPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
                line =bufferedReader.readLine();
                if(line!=null)
                    data = data + line;

            }
            } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } //catch (JSONException e) {
        // e.printStackTrace();
        // }

        return data;
    }

    @Override
    protected void onPostExecute(String avoid) {

        super.onPostExecute(avoid);

        MainActivity.jsontovalues(data);

    }
}
