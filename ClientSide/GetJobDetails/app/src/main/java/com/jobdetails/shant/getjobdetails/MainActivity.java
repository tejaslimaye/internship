package com.jobdetails.shant.getjobdetails;


import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jobdetails.shant.getjobdetails.Response.TestExecutionDetailsBean;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static TextView totalTest;
   public static TextView completedTest;
    public static TextView passedTest;
    public static TextView failedTest;
    public static TextView cannotTest;
    public static TextView featureName;
    public static TextView testName;
    public static TextView testCaseDesc;
    public static TextView startTime;


    public static TextView inprogress;
    public Button btn;             //For Start Test Button
 //  public static RecyclerView rv_testdetails;
   //public static LinearLayoutManager layoutManager;



   // public static ArrayList<PrintResponse> printResponses = new ArrayList<>();    //To display Test Cases Information in recycler view.


   private static String TAG ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      // rv_testdetails = (RecyclerView) findViewById(R.id.rv_response);

    // layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);



      //  btn = (Button) findViewById(R.id.startbtn);
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
        // senddatatoserver();
        converttojson();
    }

/*    public void onButtonClick(View v)        // For Start Test Button which on click shows test results.
    {
        if(v.getId() == R.id.startbtn)
        {
            Intent i = new Intent(MainActivity.this,TestResult.class);
            startActivity(i);
        }


    }
*/

    public void jsontovalues(String data) {     // For Parsing Json response from Server

        Gson testdetails = new Gson();

        Log.d(TAG, "Afterloop : data received:" + data.toString());
        Constant.resp = testdetails.fromJson(data, Response.class);       //Resp is object of Response Class.

        totalTest.setText(String.valueOf(Constant.resp.getTest_execution_details().size()));

        TestResult.fetchdatafromresponse();

        //Log.d(TAG, "In Senddatatoserver" + Integer.toString(Constant.resp.getTest_execution_details().size()));



    /*    for (int i = 0; i <Constant.resp.getTest_execution_details().size(); i++) {
            //*Fetching and adding execution details to arrayList to print it in RecyclerView
            TestExecutionDetailsBean obj = Constant.resp.getTest_execution_details().get(i);
           // printResponses.add(new PrintResponse(String.valueOf(obj.getExecution_id()),String.valueOf(obj.getTestcase_id()),obj.getTestcase_name()));
        }*/


       // ResponseAdapter adapter = new ResponseAdapter(this, (ArrayList<PrintResponse>) printResponses);

    //    rv_testdetails.setLayoutManager(layoutManager);
     //   rv_testdetails.setAdapter(adapter);

    }

    private void senddatatoserver(String json) {         //To call onBackground Method in class FetchData.

        FetchData process = new FetchData(this);
        process.execute("http://192.168.1.101:8080/automation/getJobDetails.htm",json);    // OnBackground Fn. call

    }

    private void converttojson() {        //To create a Json of Mobile Details to send as a request to server

        Mob_Details mob_details = new Mob_Details(Build.SERIAL,Build.MODEL,""+Build.VERSION_CODES.BASE,Build.VERSION.RELEASE,Build.ID,Build.MANUFACTURER,Build.BRAND);

        Gson gson = new Gson();
        String json =gson.toJson(mob_details);

        senddatatoserver(json);
    }

}
