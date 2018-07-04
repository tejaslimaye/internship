package com.jobdetails.shant.getjobdetails;


import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jobdetails.shant.getjobdetails.Response.TestExecutionDetailsBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static TextView tv1;
   // public static TextView tv2;

    public Button btn;


   private static String TAG ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.startbtn);
        tv1=(TextView) findViewById(R.id.textview);
        // senddatatoserver();
        converttojson();
    }

    public void onButtonClick(View v)
    {
        if(v.getId() == R.id.startbtn)
        {
            Intent i = new Intent(MainActivity.this,TestResult.class);
            startActivity(i);
        }

        TestResult.fetchdatafromresponse();
    }


    public static void jsontovalues(String data) {

        Gson testdetails = new Gson();

        Log.d(TAG, "Afterloop : data received:" + data.toString());
        Constant.resp = testdetails.fromJson(data, Response.class);

        //Log.d(TAG, "In Senddatatoserver" + Integer.toString(Constant.resp.getTest_execution_details().size()));
        MainActivity.tv1.setText("Test Case Id\tExecution Id\tTest Case Name\n");


        for (int i = 0; i <Constant.resp.getTest_execution_details().size(); i++) {
            TestExecutionDetailsBean obj = Constant.resp.getTest_execution_details().get(i);
            MainActivity.tv1.append(obj.getTestcase_id() + "\t\t\t\t\t\t\t\t\t\t\t\t\t" + obj.getExecution_id()+"\t\t\t\t\t\t\t\t\t\t"+obj.getTestcase_name()+ "\n\n");

        }

    }

    private void senddatatoserver(String json) {

        FetchData process = new FetchData();
        process.execute("http://192.168.1.103:8080/automation/getJobDetails.htm",json);

    }

    private void converttojson() {

        Mob_Details mob_details = new Mob_Details(Build.SERIAL,Build.MODEL,""+Build.VERSION_CODES.BASE,Build.VERSION.RELEASE,Build.ID,Build.MANUFACTURER,Build.BRAND);

        Gson gson = new Gson();
        String json =gson.toJson(mob_details);

        senddatatoserver(json);
    }

}
