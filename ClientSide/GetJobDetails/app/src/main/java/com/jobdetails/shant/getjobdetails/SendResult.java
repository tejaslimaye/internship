package com.jobdetails.shant.getjobdetails;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class SendResult extends AsyncTask<String,Void,String> {

    String responsecode="";
    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");


            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));
            out.write(params[1]);
            out.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
          //  Log.d(TAG, "Before loop");
            while(line != null)/*bufferedReader.ready()*/{
            //    Log.d(TAG, "In LOOPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
                line =bufferedReader.readLine();
                if(line!=null)
                    responsecode = responsecode + line;
                //int i=1;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String avoid) {

        super.onPostExecute(avoid);

        TestResult.printresponse(responsecode);

    }
}


