package com.jobdetails.shant.getjobdetails;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestResult extends Activity {

    //Response r =new Response();

   // private static TextView txtview;
    //private static TextView txtview1;




    private static int cannotTestCount=0;
    private static int completedCount=0;
    private static int passedCount=0;
    private static int failedCount=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testresults);

       // txtview= (TextView) findViewById(R.id.tv1);
      //  txtview1 =(TextView) findViewById(R.id.tv2);


      // txtview.setText("Execution Id\t\tResult\t\tStart Time\t\tEnd Time\n\n");
     //  txtview1.append("\t\tResponse\n\t\t"+"Code\n\n");
        //fetchdatafromresponse();
       // startTesting();

        }

    public static void printresponse(String responsecode) {

        Gson rcode = new Gson();

         Constant.r= rcode.fromJson(responsecode, ResultResponseCode.class);


       // txtview1.append("\t\t"+Constant.r.getResponse_code() + "\n");

    }


    static void fetchdatafromresponse() {
        int i = 0;

        for (  i = 0; i <Constant.resp.getTest_execution_details().size(); i++) {
            Response.TestExecutionDetailsBean obj = Constant.resp.getTest_execution_details().get(i);
            String ex_id= Integer.toString(obj.getExecution_id());
          //  txtview1.append("\t\t\t" + x + "\n");
            beforeTestExecution(ex_id,obj);
          //  count=count+1;

            //startTesting(x);
            }

            if(i==Constant.resp.getTest_execution_details().size()){

                MainActivity.inprogress.setText("All Tests Completed !");
                MainActivity.featureName.setText("--");
                MainActivity.testName.setText("--");
                MainActivity.testCaseDesc.setText("--");
                MainActivity.startTime.setText("--");


            }


    }

    private static void beforeTestExecution(String id, Response.TestExecutionDetailsBean obj) {

        String resultTest ="CANNOT_TEST";
        if(resultTest == "CANNOT_TEST")
        {
            //DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           //String time = df1.format(Calendar.getInstance().getTime());
            ResultDetails resultCannotTest = new ResultDetails(id,resultTest,"null","null");
            Gson gson1 = new Gson();
            String json1 =gson1.toJson(resultCannotTest);
            SendResult send1 = new SendResult();
            send1.execute("http://192.168.1.101:8080/automation/updateTestResults.htm",json1);
            cannotTestCount=cannotTestCount+1;
            completedCount=completedCount+1;
           MainActivity.cannotTest.setText(String.valueOf(cannotTestCount));
           MainActivity.completedTest.setText(String.valueOf(completedCount));

        }

        else
        {
            String resultBeforeTest = "EXECUTION_STARTED";
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = df1.format(Calendar.getInstance().getTime());
            ResultDetails resultDetailsBeforeTest = new ResultDetails(id,resultBeforeTest,time,"null");
            Gson gson2 = new Gson();
            String json2 =gson2.toJson(resultDetailsBeforeTest);
            SendResult send2 = new SendResult();
            send2.execute("http://192.168.1.100:8080/automation/updateTestResults.htm",json2);


            MainActivity.featureName.setText(obj.getFeature_name());
            MainActivity.testName.setText(obj.getTestcase_name());
            MainActivity.testCaseDesc.setText(obj.getTestcase_desc());
            MainActivity.startTime.setText(time);

            startTesting(id);
        }
    }

    private static void startTesting(String id) {

        String resultStatus = "PASSED";
        if(resultStatus=="PASSED"){
            passedCount=passedCount+1;
          MainActivity.passedTest.setText(String.valueOf(passedCount));
        }
        else
        {
            failedCount=failedCount+1;
            MainActivity.failedTest.setText(String.valueOf(failedCount));
        }

        completedCount=completedCount+1;
        MainActivity.completedTest.setText(String.valueOf(completedCount));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String starttime = df.format(Calendar.getInstance().getTime());
        String endtime = df.format(Calendar.getInstance().getTime());

       // txtview.append(id+"\t\t\t\t"+result+"\t\t\t\t"+starttime+"\t\t\t\t"+endtime+"\n");
        ResultDetails resultDetails = new ResultDetails(id,resultStatus,starttime,endtime);


        Gson gson = new Gson();
        String json2 =gson.toJson(resultDetails);
        SendResult send = new SendResult();
        send.execute("http://192.168.1.100:8080/automation/updateTestResults.htm",json2);

    }




}
