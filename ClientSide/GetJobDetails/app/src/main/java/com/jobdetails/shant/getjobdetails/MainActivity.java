package com.jobdetails.shant.getjobdetails;


import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.lang.*;


import java.util.ArrayList;

import static com.jobdetails.shant.getjobdetails.Constant.cannotTestCount;
import static com.jobdetails.shant.getjobdetails.Constant.completedCount;
import static com.jobdetails.shant.getjobdetails.Constant.failedCount;
import static com.jobdetails.shant.getjobdetails.Constant.passedCount;
import static com.uniken.rdna.RDNA.getSDKVersion;


public class MainActivity extends AppCompatActivity{

    public static TextView totalTest;
   public static TextView completedTest;
    public static TextView passedTest;
    public static TextView failedTest;
    public static TextView cannotTest;
    public static TextView featureName;
    public static TextView testName;
    public static TextView testCaseDesc;
    public static TextView startTime;

    String ip="192.168.1.100";
    public static TextView inprogress;
    public Button btn;             //For Start Test Button


   private static String TAG ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        converttojson(ip);

        btn = (Button) findViewById(R.id.add);
        totalTest=(TextView) findViewById(R.id.txtTotalValue);
        completedTest=(TextView) findViewById(R.id.txtCompletedValue);
        passedTest=(TextView) findViewById(R.id.txtPassesValue);
        failedTest = (TextView) findViewById(R.id.txtFailedValue);
        cannotTest = (TextView) findViewById(R.id.txtCannotTestValue);
        featureName = (TextView) findViewById(R.id.txtFeatureName_Value);
        testName=(TextView) findViewById(R.id.txtTestName_Value);
        testCaseDesc=(TextView) findViewById(R.id.txtTestDesc_Value);
        startTime= (TextView) findViewById(R.id.txtStartTime_Value);
        inprogress= (TextView) findViewById(R.id.txtInProgress);

    }

    public void jsontovalues(String data) {     // For Parsing Json response from Server

        Gson testdetails = new Gson();

        Log.i(TAG, "Afterloop : data received:" + data.toString());
        Constant.resp = testdetails.fromJson(data, Response.class);       //Resp is object of Response Class.


        if(Constant.resp.getJob_avail_code()==0)
        {
            inprogress.setText("No Tests Available");
        }

    //    int arjun = Constant.testJobBean.getExecutions().size();

      else
          TestResult.fetchdatafromresponse(ip);

    }

    private void senddatatoserver(String ip,String json) {         //To call onBackground Method in class FetchData.

        FetchData process = new FetchData(this);
        process.execute("http://"+ip+":8080/automation/getJobDetails.htm",json);    // OnBackground Fn. call

    }

    private void converttojson(String ip) {//To create a Json of Mobile Details to send as a request to server

        String lib_version = getLibraryVersion();

        Mob_Details mob_details = new Mob_Details(Build.SERIAL,Build.MODEL,"ios",Build.VERSION.RELEASE,Build.ID,Build.MANUFACTURER,Build.BRAND,lib_version);

        Log.d(TAG,"Serial = " +mob_details.getSerial_num());
        Log.d(TAG,"Model = " +mob_details.getDevice_model());
        Log.d(TAG,"OS = " +mob_details.getDevice_os());
        Log.d(TAG,"OS version  = " +mob_details.getOs_version());
        Log.d(TAG,"ID = " +mob_details.getBuild_id());
        Gson gson = new Gson();
        String json =gson.toJson(mob_details);

        senddatatoserver(ip,json);

        }

    private String getLibraryVersion() {

        String version = getSDKVersion();
        Log.d(TAG,"LibraryVersion: "+ version);
       return version;
      // return "1.1";

    }


}
