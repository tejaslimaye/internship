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
            String agentinfo= "";
            String authGateWayHNIP = "";
            String authGateWayPort = "";
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
