package com.jobdetails.shant.getjobdetails.Common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.jobdetails.shant.getjobdetails.Beans.JobDetailBean;
import com.jobdetails.shant.getjobdetails.Beans.TestCaseResult;
import com.jobdetails.shant.getjobdetails.Beans.UpdateResultResponse;
import com.jobdetails.shant.getjobdetails.Network.APIClient;
import com.jobdetails.shant.getjobdetails.Protocol.APIInterface;
import com.jobdetails.shant.getjobdetails.Protocol.CountUpdater;
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

import static com.jobdetails.shant.getjobdetails.Common.Constant.resultFail;
import static com.jobdetails.shant.getjobdetails.Common.Constant.resultPass;
import static com.uniken.rdna.RDNA.Initialize;

public class TestCaseManager implements com.jobdetails.shant.getjobdetails.Protocol.TestCaseHandler {

    Application mainApplication;
    CountUpdater countUpdater;
    HashMap<String, TestCaseResult> testCasesStatusList = new HashMap<>();
    JobDetailBean.Testjobexecution testJobBean;
    int testCaseProgress = 0;
    int totalTestCaseCount = 0;
    String progressText = "In Progress";
    int serverExecutionProgress = 0;
    int totalServerExecutionCount = 0;

    int failedCount = 0;
    int passedCount = 0;
    int notTestedCount = 0;

    String initFeatureNM = "Initialization on Mobile";

    public TestCaseManager(Application mainApplication, Activity activity) {
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

            ConnectionProfile connectionProfile = new ConnectionProfile();
            connectionProfile.setAgentInfo(serverBean.getAgentInfo());
            connectionProfile.setAuthGateWayPort(serverBean.getSdkPort());
            connectionProfile.setAuthGateWayHNIP(serverBean.getIpAddress());

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

    private void initSDK(){

        InitCallBacks callBacks = new InitCallBacks(mainApplication);

        RDNA.RDNALoggingLevel loggingLevel = RDNA.RDNALoggingLevel.RDNA_LOG_VERBOSE;
        RDNA.RDNAStatus<RDNA> objRDNA = Initialize(ConnectionProfile.getAgentInfo(), callBacks, ConnectionProfile.getAuthGateWayHNIP(), ConnectionProfile.getAuthGateWayPort(), null, null, null, null, null, loggingLevel, this);//  obj.result.

        if(!RDNAManager.isRdnaInitSuccess()){
            RDNAManager.setObjSyncRDNA(objRDNA);
        }
    }

    private void terminateSDK(){

        int objRDNA = RDNAManager.getObjSyncRDNA().result.terminate();

        if(objRDNA == 0){
            RDNAManager.setObjSyncRDNA(null);
        }
    }

    public void checkTestCaseResult(JobDetailBean.Execution objExecution, String featureName) {

        if(RDNAManager.getObjAsyncINIT() == null && !featureName.equalsIgnoreCase("Initialization on Mobile")){
            initSDK();
            return;
        } else if(RDNAManager.getObjAsyncINIT() != null && featureName.equalsIgnoreCase("Initialization on Mobile")){
            terminateSDK();
        }

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
//        sendTestResultData(testCaseResult);

        testCaseHandler(featureName,objExecution);
    }


    private void testCaseHandler(String featureName, JobDetailBean.Execution objExecution){

        switch (featureName) {

            case "Initialization on Mobile":
                new InitializationActivity(TestCaseManager.this,mainApplication,testCaseProgress,ConnectionProfile.getAgentInfo(),ConnectionProfile.getAuthGateWayHNIP(),ConnectionProfile.getAuthGateWayPort(), objExecution).initTestCase();
                break;

            case "SERVICE_INFO":
                new ServiceInfoActivity(TestCaseManager.this,mainApplication,testCaseProgress, ConnectionProfile.getAgentInfo(),ConnectionProfile.getAuthGateWayHNIP(),ConnectionProfile.getAuthGateWayPort(), objExecution).initTestCase();
                break;

            case "SERVICE_ACCESS":
                new ServiceInfoActivity(TestCaseManager.this,mainApplication,testCaseProgress, ConnectionProfile.getAgentInfo(),ConnectionProfile.getAuthGateWayHNIP(),ConnectionProfile.getAuthGateWayPort(), objExecution).initTestCase();
                break;

            case "INFO_GETTERS":
                new InfoGettersActivity(TestCaseManager.this,mainApplication,testCaseProgress, ConnectionProfile.getAgentInfo(),ConnectionProfile.getAuthGateWayHNIP(),ConnectionProfile.getAuthGateWayPort(), objExecution).initTestCase();
                break;

            case "DATA_PRIVACY":
                new DataPrivacyActivity(TestCaseManager.this,mainApplication,testCaseProgress, ConnectionProfile.getAgentInfo(),ConnectionProfile.getAuthGateWayHNIP(),ConnectionProfile.getAuthGateWayPort(), objExecution).initTestCase();
                break;

/*            case "EXECUTE_HTTP_API":
                new ExecHttpAPIActivity(TestCaseManager.this,mainApplication,testCaseProgress,agentInfo,authGateWayHNIP,authGateWayPort, objExecution).initTestCase();
                break;*/

            case "USER_SESSION_API":
                new UserSessionActivity(TestCaseManager.this,mainApplication,testCaseProgress, ConnectionProfile.getAgentInfo(),ConnectionProfile.getAuthGateWayHNIP(),ConnectionProfile.getAuthGateWayPort(), objExecution).initTestCase();
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

//        sendTestResultData(testCaseResult);

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

    public void createDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mainApplication.getApplicationContext());
        builder.setTitle("Initialization failed.\nDo you want to retry? ");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                callSyncTestCase(testCaseProgress);
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        // Create the AlertDialog object and return it
        builder.create();

    }

    public class InitCallBacks implements RDNA.RDNACallbacks {

        Application mainApplication;

        public InitCallBacks(Application application) {
            this.mainApplication = application;
        }

        @Override
        public int onInitializeCompleted(final RDNA.RDNAStatusInit rdnaStatusInit) {
            if (rdnaStatusInit.errCode == testJobBean.getExecutions().get(testCaseProgress).getErrorCode()) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {

                        if (!RDNAManager.isRdnaInitSuccess()) {
                            RDNAManager.setObjAsyncINIT(rdnaStatusInit);
                        }


                        if (rdnaStatusInit.errCode == 0) {
                            RDNAManager.setRdnaInitSuccess(true);
                        }

                        callSyncTestCase(testCaseProgress);
                    }
                });

                return 0;
            } else {
                //if it is false
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        createDialog();
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
            callSyncTestCase(testCaseProgress);
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

            new UserSessionActivity().activeUser(rdnaStatusCheckChallengeResponse.challenges, rdnaStatusCheckChallengeResponse.status.statusCode, new CommonActivity(), RDNAManager.getTestCaseHandler());

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
