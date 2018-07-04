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

    private static TextView txtview;
    private static TextView txtview1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testresults);

        txtview= (TextView) findViewById(R.id.tv1);
        txtview1 =(TextView) findViewById(R.id.tv2);


      // txtview.setText("Execution Id\t\tResult\t\tStart Time\t\tEnd Time\n\n");
       txtview1.append("\t\tResponse\n\t\t"+"Code\n\n");
        //fetchdatafromresponse();
       // startTesting();

        }

    public static void printresponse(String responsecode) {

        Gson rcode = new Gson();

         Constant.r= rcode.fromJson(responsecode, ResultResponseCode.class);


        txtview1.append("\t\t"+Constant.r.getResponse_code() + "\n");

    }


    static void fetchdatafromresponse() {

        for (int i = 0; i <Constant.resp.getTest_execution_details().size(); i++) {
            Response.TestExecutionDetailsBean obj = Constant.resp.getTest_execution_details().get(i);
            String x= Integer.toString(obj.getExecution_id());
          //  txtview1.append("\t\t\t" + x + "\n");
            startTesting(x);
            }
    }

    private static void startTesting(String id) {

        String result = "CANNOT_TEST";

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String starttime = df.format(Calendar.getInstance().getTime());
        String endtime = df.format(Calendar.getInstance().getTime());

       // txtview.append(id+"\t\t\t\t"+result+"\t\t\t\t"+starttime+"\t\t\t\t"+endtime+"\n");
        ResultDetails resultDetails = new ResultDetails(id,result,starttime,endtime);


        Gson gson = new Gson();
        String json1 =gson.toJson(resultDetails);
        SendResult send = new SendResult();
        send.execute("http://192.168.1.103:8080/automation/updateTestResults.htm",json1);

    }




}
