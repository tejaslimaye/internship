package com.jobdetails.shant.getjobdetails.Common;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.jobdetails.shant.getjobdetails.Beans.JobDetailBean;
import com.jobdetails.shant.getjobdetails.Beans.TestCaseResult;
import com.jobdetails.shant.getjobdetails.Beans.UpdateResultResponse;
import com.jobdetails.shant.getjobdetails.Network.APIClient;
import com.jobdetails.shant.getjobdetails.Protocol.APIInterface;
import com.jobdetails.shant.getjobdetails.Protocol.CountUpdater;
import com.jobdetails.shant.getjobdetails.Protocol.TestCaseHandler;
import com.jobdetails.shant.getjobdetails.TestCaseActivity.DataPrivacyActivity;
import com.jobdetails.shant.getjobdetails.TestCaseActivity.InfoGettersActivity;
import com.jobdetails.shant.getjobdetails.TestCaseActivity.InitializationActivity;
import com.jobdetails.shant.getjobdetails.TestCaseActivity.ServiceInfoActivity;
import com.jobdetails.shant.getjobdetails.TestCaseActivity.UserSessionActivity;
import com.uniken.rdna.RDNA;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jobdetails.shant.getjobdetails.Common.Constant.rdnaInitSuccess;
import static com.jobdetails.shant.getjobdetails.Common.Constant.resultFail;
import static com.jobdetails.shant.getjobdetails.Common.Constant.resultPass;

public class TestAPIResult implements TestCaseHandler {

    Application mainApplication;
    CountUpdater countUpdater;
    HashMap<String, TestCaseResult> testCasesStatusList = new HashMap<>();
    JobDetailBean.Testjobexecution testJobBean;
    int testCaseProgress = 0;
    int totalTestCaseCount = 0;
    String progressText = "In Progress";
    int serverExecutionProgress = 0;
    int totalServerExecutionCount = 0;

    String agentinfo = "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";
    String authGateWayHNIP = "18.206.222.73";
    int authGateWayPort = 4443;

    int failedCount = 0;
    int passedCount = 0;
    int notTestedCount = 0;



    public TestAPIResult(Application mainApplication, Activity activity) {
        this.mainApplication = mainApplication;
        this.countUpdater = (CountUpdater) activity;
    }

    //Start the execution of test cases in Async mode
    public void startTestExecution(JobDetailBean jobDetailBean) {

        totalTestCaseCount = getTotalSize(jobDetailBean);
        totalServerExecutionCount = jobDetailBean.getServerExecutionDetails().size();
        //update count at screen
        countUpdater.updateCount(totalTestCaseCount, 0, 0, 0, 0, progressText);

        for (int i = 0; i < jobDetailBean.getServerExecutionDetails().size(); i++) {
            JobDetailBean.ServerExecutionDetail serverBean = jobDetailBean.getServerExecutionDetails().get(i);

            agentinfo = serverBean.getAgentInfo();
            authGateWayPort = serverBean.getSdkPort();
            authGateWayHNIP = serverBean.getIpAddress();

            for (int j = 0; j < serverBean.getTestjobexecutions().size(); j++) {
                testJobBean = serverBean.getTestjobexecutions().get(j);
                for (int k = 0; k < testJobBean.getExecutions().size(); k++) {

                    testCaseProgress = 0;
                    callSyncTestCase(this.testCaseProgress);
                    break;
                }
            }
            break;
        }
    }

    public void callSyncTestCase(int testCaseNo) {

        updateCount();

        if(testCaseNo < testJobBean.getExecutions().size()) {

            JobDetailBean.Execution obj = testJobBean.getExecutions().get(testCaseNo);
            String exID = Integer.toString(obj.getExecutionId());

            checkTestCaseResult(obj, obj.getFeatureName());

        }
    }

    private void updateCount(){
        int completedTestCases = passedCount + failedCount;

        if (testCaseProgress == totalTestCaseCount) {
            progressText = "Completed";
        }

        countUpdater.updateCount(totalTestCaseCount, completedTestCases, passedCount, failedCount, notTestedCount, progressText);
    }

    public void checkTestCaseResult(JobDetailBean.Execution objExecution, String featureName) {

        String resultStatus = "";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = df.format(Calendar.getInstance().getTime());
        TestCaseResult testCaseResult = new TestCaseResult();
        testCaseResult.setStartTime(startTime);
        testCaseResult.setExecutionID(objExecution.getExecutionId());
        testCaseResult.setTestCaseID(objExecution.getTestcaseId());
        testCaseResult.setEndTime("0000-00-00 00:00:00");
        testCaseResult.setExecutionResult("STARTED");
        testCaseResult.setParamUsed("");
        testCaseResult.setResultData("");

        testCasesStatusList.put(objExecution.getTestcaseName(), testCaseResult);
        sendTestResultData(testCaseResult);

        testCaseHandler(featureName,objExecution);
        /*APIResponseCallBacks callBacks = new APIResponseCallBacks(mainApplication);
        if(featureName.equals("Initialization on Mobile")) {
            new InitializationActivity(TestAPIResult.this, mainApplication, testCaseProgress, agentinfo, authGateWayHNIP, authGateWayPort, callBacks).initTestCase(objExecution);

        }else {
            testCaseExecuted(1,0,0,1,Constant.resultCanNotTest);
        }*/
    }


    private void testCaseHandler(String featureName, JobDetailBean.Execution objExecution){

        APIResponseCallBacks callBacks = new APIResponseCallBacks(mainApplication);

        switch (featureName) {

            case "Initialization on Mobile":
                new InitializationActivity(TestAPIResult.this,mainApplication,testCaseProgress,agentinfo,authGateWayHNIP,authGateWayPort,callBacks, objExecution).initTestCase();
                break;

            case "SERVICE_INFO":
                new ServiceInfoActivity(TestAPIResult.this,mainApplication,testCaseProgress,agentinfo,authGateWayHNIP,authGateWayPort,callBacks, objExecution).initTestCase();
                break;

            case "SERVICE_ACCESS":
                new ServiceInfoActivity(TestAPIResult.this,mainApplication,testCaseProgress,agentinfo,authGateWayHNIP,authGateWayPort,callBacks, objExecution).initTestCase();
                break;

            case "INFO_GETTERS":
                new InfoGettersActivity(TestAPIResult.this,mainApplication,testCaseProgress,agentinfo,authGateWayHNIP,authGateWayPort,callBacks, objExecution).initTestCase();
                break;

            case "DATA_PRIVACY":
                new DataPrivacyActivity(TestAPIResult.this,mainApplication,testCaseProgress,agentinfo,authGateWayHNIP,authGateWayPort,callBacks, objExecution).initTestCase();
                break;

/*            case "EXECUTE_HTTP_API":
                new ExecHttpAPIActivity(TestAPIResult.this,mainApplication,testCaseProgress,agentinfo,authGateWayHNIP,authGateWayPort,callBacks, objExecution).initTestCase();
                break;*/

            case "USER_SESSION_API":
                new UserSessionActivity(TestAPIResult.this,mainApplication,testCaseProgress,agentinfo,authGateWayHNIP,authGateWayPort,callBacks, objExecution).initTestCase();
                break;

            default:
                testCaseExecuted(1,0,0,1,Constant.resultCanNotTest);
                break;

        }
    }

    private void sendTestResultData(TestCaseResult testCaseResult) {

        APIInterface apiInterface = APIClient.getJobDetailJSON().create(APIInterface.class);
        Call<UpdateResultResponse> updateDataToServer = apiInterface.updateTestResult(testCaseResult);

        updateDataToServer.enqueue(new Callback<UpdateResultResponse>() {
            @Override
            public void onResponse(Call<UpdateResultResponse> call, Response<UpdateResultResponse> response) {

                Log.d("updateResult:","Sucess: "+ response.body() +"");

            }

            @Override
            public void onFailure(Call<UpdateResultResponse> call, Throwable t) {
                Log.d("updateResult:","Fail: "+ t.getMessage() +"");
            }
        });

    }

    private int getTotalSize(JobDetailBean jobDetailBean) {

        int totalTestCount = 0;
        for (int i = 0; i < jobDetailBean.getServerExecutionDetails().size(); i++) {
            JobDetailBean.ServerExecutionDetail serverBean = jobDetailBean.getServerExecutionDetails().get(i);

            for (int j = 0; j < serverBean.getTestjobexecutions().size(); j++) {
                JobDetailBean.Testjobexecution testJobBean = serverBean.getTestjobexecutions().get(j);
                //int y = serverBean.getTestJobExecutions().size();
                for (int k = 0; k < testJobBean.getExecutions().size(); k++) {

                    totalTestCount = totalTestCount + 1;
                }
            }

        }

        return totalTestCount;
    }

    //update test case result end time and update into list
    private TestCaseResult updateEndTime(int testCaseNumber, String caseResult) throws Exception {

        Log.e("passCount", String.valueOf(passedCount));
        Log.e("failCount", String.valueOf(failedCount));
        Log.e("testResults", String.valueOf(caseResult));
        Log.e("testCaseProgress",String.valueOf(testCaseProgress));
        Log.e("runningTests",testJobBean.getExecutions().get(testCaseProgress).getTestcaseName());

        JobDetailBean.Execution objExecution = testJobBean.getExecutions().get(testCaseNumber);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endTime = df.format(Calendar.getInstance().getTime());

        TestCaseResult testCaseResult = testCasesStatusList.get(objExecution.getTestcaseName());
        testCaseResult.setEndTime(endTime);
        testCaseResult.setExecutionResult(caseResult);
        testCaseResult.setParamUsed("");
        testCaseResult.setResultData("");

        testCasesStatusList.put(objExecution.getTestcaseName(), testCaseResult);

        sendTestResultData(testCaseResult);

        return testCaseResult;
    }

    @Override
    public void testCaseExecuted(int testCaseProgress, int failedCount, int passedCount, int notTestCount, String caseResult) {

        try {
            updateEndTime(this.testCaseProgress, caseResult);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error:","While updating the end time function");
        }

        this.failedCount += failedCount;
        this.passedCount += passedCount;
        this.notTestedCount += notTestCount;
        //increment the test case and them in sync call
        this.testCaseProgress += testCaseProgress;
        callSyncTestCase(this.testCaseProgress);

    }

    private void updatePassCount(){

        /*Log.e("pass", String.valueOf(passedCount));
        Log.e("fail", String.valueOf(failedCount));
        Log.e("TestCaseProgress",String.valueOf(testCaseProgress));
        Log.e("RunningTests",testJobBean.getExecutions().get(testCaseProgress).getTestcaseName());*/

        //update end time
        try {
            updateEndTime(testCaseProgress,resultPass);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error:","While updating the end time function");
        }

        passedCount = passedCount + 1;

        testCaseProgress += 1;
        callSyncTestCase(this.testCaseProgress);

    }

    private void updateFailCount(){

/*        Log.e("pass", String.valueOf(passedCount));
        Log.e("fail", String.valueOf(failedCount));
        Log.e("TestCaseProgress",String.valueOf(testCaseProgress));
        Log.e("RunningTests",testJobBean.getExecutions().get(testCaseProgress).getTestcaseName());*/


        //update end time
        try {
            updateEndTime(testCaseProgress,resultFail);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error:","While updating the end time function");
        }

        failedCount = failedCount + 1;

        testCaseProgress += 1;
        callSyncTestCase(this.testCaseProgress);
    }

    public class APIResponseCallBacks implements RDNA.RDNACallbacks {

        Application mainApplication;

        public APIResponseCallBacks(Application application) {
            this.mainApplication = application;
        }

        @Override
        public int onInitializeCompleted(final RDNA.RDNAStatusInit rdnaStatusInit) {
            if (rdnaStatusInit.errCode == testJobBean.getExecutions().get(testCaseProgress).getErrorCode()) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("UI thread:", "on main thread");
                        if (rdnaStatusInit.errCode == 0) {
                            rdnaInitSuccess = true;
                        }
                        updatePassCount();
                    }
                });

                return 0;
            } else {
                //if it is false
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("UI thread:", "on main thread");
                        updateFailCount();
                    }
                });
                return 1;
            }
        }

        @Override
        public Context getDeviceContext() {
            return mainApplication.getApplicationContext();
        }

        @Override
        public String getDeviceToken() {
            return null;
        }

        @Override
        public int onGetNotifications(RDNA.RDNAStatusGetNotifications rdnaStatusGetNotifications) {
            return 0;
        }

        @Override
        public int onUpdateNotification(RDNA.RDNAStatusUpdateNotification rdnaStatusUpdateNotification) {
            return 0;
        }

        @Override
        public int onTerminate(RDNA.RDNAStatusTerminate rdnaStatusTerminate) {
            return 0;
        }

        @Override
        public int onPauseRuntime(RDNA.RDNAStatusPause rdnaStatusPause) {
            return 0;
        }

        @Override
        public int onResumeRuntime(RDNA.RDNAStatusResume rdnaStatusResume) {
            return 0;
        }

        @Override
        public int onConfigReceived(RDNA.RDNAStatusGetConfig rdnaStatusGetConfig) {
            return 0;
        }

        @Override
        public int onCheckChallengeResponseStatus(RDNA.RDNAStatusCheckChallengeResponse rdnaStatusCheckChallengeResponse) {
            return 0;
        }

        @Override
        public int onGetAllChallengeStatus(RDNA.RDNAStatusGetAllChallenges rdnaStatusGetAllChallenges) {
            return 0;
        }

        @Override
        public int onUpdateChallengeStatus(RDNA.RDNAStatusUpdateChallenges rdnaStatusUpdateChallenges) {
            return 0;
        }

        @Override
        public int onGetPostLoginChallenges(RDNA.RDNAStatusGetPostLoginChallenges rdnaStatusGetPostLoginChallenges) {
            return 0;
        }

        @Override
        public int onLogOff(RDNA.RDNAStatusLogOff rdnaStatusLogOff) {
            return 0;
        }

        @Override
        public RDNA.RDNAIWACreds getCredentials(String s) {
            return null;
        }

        @Override
        public String getApplicationName() {
            return null;
        }

        @Override
        public String getApplicationVersion() {
            return null;
        }

        @Override
        public int onGetRegistredDeviceDetails(RDNA.RDNAStatusGetRegisteredDeviceDetails rdnaStatusGetRegisteredDeviceDetails) {
            return 0;
        }

        @Override
        public int onUpdateDeviceDetails(RDNA.RDNAStatusUpdateDeviceDetails rdnaStatusUpdateDeviceDetails) {
            return 0;
        }

        @Override
        public int onGetNotificationsHistory(RDNA.RDNAStatusGetNotificationHistory rdnaStatusGetNotificationHistory) {
            return 0;
        }

        @Override
        public int onSessionTimeout(String s) {
            return 0;
        }

        @Override
        public int onSdkLogPrintRequest(RDNA.RDNALoggingLevel rdnaLoggingLevel, String s) {
            return 0;
        }

        @Override
        public int onSecurityThreat(String s) {
            return 0;
        }
    }


}
