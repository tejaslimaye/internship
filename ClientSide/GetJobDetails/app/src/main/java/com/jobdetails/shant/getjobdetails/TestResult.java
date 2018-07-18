package com.jobdetails.shant.getjobdetails;

import android.app.Application;

import com.google.gson.Gson;
import com.uniken.rdna.RDNA;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.jobdetails.shant.getjobdetails.Constant.cannotTestCount;
import static com.jobdetails.shant.getjobdetails.Constant.completedCount;
import static com.jobdetails.shant.getjobdetails.Constant.failedCount;
import static com.jobdetails.shant.getjobdetails.Constant.passedCount;
import static com.uniken.rdna.RDNA.Initialize;




public class TestResult  {


    Application mainApplication;
    public TestResult(Application mainApplication) {
        this.mainApplication = mainApplication;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.testresults);
//
//        }

    public static void printresponse(String responsecode) {

        Gson rcode = new Gson();

         Constant.r= rcode.fromJson(responsecode, ResultResponseCode.class);


       // txtview1.append("\t\t"+Constant.r.getResponse_code() + "\n");

    }


    void fetchdatafromresponse(String ip) {
        int i = 0;
        int j=0;
        int k=0;

     getTotalSize();

        for (i = 0; i <Constant.resp.getServer_execution_details().size(); i++) {
            ServerBean serverBean= Constant.resp.getServer_execution_details().get(i);

            for(j=0;j<serverBean.getTestJobExecutions().size();j++)
            {
                TestJobBean testJobBean = serverBean.getTestJobExecutions().get(j);
                //int y = serverBean.getTestJobExecutions().size();
                for(k=0; k< testJobBean.getExecutions().size(); k++ )
                {

                    ExecutionBean obj = testJobBean.getExecutions().get(k);
                    String ex_id= Integer.toString(obj.getExecution_id());
                   // beforeTestExecution(ip,ex_id,obj);
                    beforeTestExecution(ip,ex_id,obj,obj.getFeature_name());
                    //beforeTestExecution(ip,obj.getFeature_name(),obj);
                }
            }

            }


        if(i==Constant.resp.getServer_execution_details().size()){

            MainActivity.inprogress.setText("All Tests Completed !");
            MainActivity.featureName.setText("--");
            MainActivity.testName.setText("--");
            MainActivity.testCaseDesc.setText("--");
            MainActivity.startTime.setText("--");


        }
        }

    private static void getTotalSize() {

        int totalTestCount= 0;
        for (int i = 0; i <Constant.resp.getServer_execution_details().size(); i++) {
            ServerBean serverBean= Constant.resp.getServer_execution_details().get(i);

            for(int j=0;j<serverBean.getTestJobExecutions().size();j++)
            {
                TestJobBean testJobBean = serverBean.getTestJobExecutions().get(j);
                //int y = serverBean.getTestJobExecutions().size();
                for(int k=0; k< testJobBean.getExecutions().size(); k++ )
                {

                    totalTestCount=totalTestCount+1;
                }
            }

        }
        MainActivity.totalTest.setText(String.valueOf(totalTestCount));
    }

    private void beforeTestExecution(String ip, String id, ExecutionBean obj, String feature_name) {




        String resultTest ="CANNOT";
        if(resultTest.equalsIgnoreCase("CANNOT_TEST"))
        {
            //DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           //String time = df1.format(Calendar.getInstance().getTime());
            ResultDetails resultCannotTest = new ResultDetails(id,resultTest,"0000-00-00 00:00:00","0000-00-00 00:00:00");
            Gson gson1 = new Gson();
            String json1 =gson1.toJson(resultCannotTest);
            SendResult send1 = new SendResult();
            send1.execute("http://"+ip+":8080/automation/updateTestResults.htm",json1);
            cannotTestCount=cannotTestCount+1;
            completedCount= completedCount+1;
           MainActivity.cannotTest.setText(String.valueOf(cannotTestCount));
           MainActivity.completedTest.setText(String.valueOf(completedCount));

        }

        else
        {

            String resultBeforeTest = "EXECUTION_STARTED";
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = df1.format(Calendar.getInstance().getTime());
            ResultDetails resultDetailsBeforeTest = new ResultDetails(id,resultBeforeTest,time,"0000-00-00 00:00:00");
            Gson gson2 = new Gson();
            String json2 =gson2.toJson(resultDetailsBeforeTest);
            SendResult send2 = new SendResult();
            send2.execute("http://"+ip+":8080/automation/updateTestResults.htm",json2);


            MainActivity.featureName.setText(obj.getFeature_name());
            MainActivity.testName.setText(obj.getTestcase_name());
            MainActivity.testCaseDesc.setText(obj.getTestcase_desc());
            MainActivity.startTime.setText(time);

            startTesting(ip,id,feature_name);
        }
    }

    private void startTesting(String ip, String id, String feature_name) {

        if(feature_name.equalsIgnoreCase("Initialization on Mobile"))
        {
            CallBacks callBacks = new CallBacks(mainApplication);
            String agentinfo= "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";
            String authGateWayHNIP = "54.197.63.146";
            String authGateWayPort = "443";
            //RDNA.RDNAStatus<String> cipherSpecs =  ;

         //   String cipherSalt = RDNA.;
            RDNA.RDNALoggingLevel loggingLevel = RDNA.RDNALoggingLevel.RDNA_LOG_DEBUG;


            RDNA.RDNAStatus<RDNA> obj = Initialize(agentinfo,callBacks,authGateWayHNIP, Integer.parseInt(authGateWayPort),null,null,null,null,null,loggingLevel, this);
          //  obj.result.
            int sumeet =6;
        }


        String resultStatus = "CREATED";
        if(resultStatus.equalsIgnoreCase("CREATED")){
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
        send.execute("http://"+ip+":8080/automation/updateTestResults.htm",json2);

    }




}
