package com.jobdetails.shant.getjobdetails.TestCaseActivity;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.jobdetails.shant.getjobdetails.Beans.JobDetailBean;
import com.jobdetails.shant.getjobdetails.Common.CommonActivity;
import com.jobdetails.shant.getjobdetails.Common.Constant;
import com.jobdetails.shant.getjobdetails.Common.TestCaseManager;
import com.uniken.rdna.RDNA;

import static com.uniken.rdna.RDNA.Initialize;

public class InitializationActivity {

    Application mainApplication;
    int testCaseProgress;
    String agentinfo;
    String authGateWayHNIP;
    int authGateWayPort;
    com.jobdetails.shant.getjobdetails.Protocol.TestCaseHandler testCaseHandler;
    JobDetailBean.Execution objExecution;
    CommonActivity commonActivity;
    RDNA.RDNAStatus<RDNA> initRDNA;
    String caseStatus = Constant.resultCanNotTest;

    public InitializationActivity(Object activity, Application mainApplication, int testCaseProgress, String agentinfo, String authGateWayHNIP, int authGateWayPort, JobDetailBean.Execution objExecution) {
        this.testCaseHandler = (com.jobdetails.shant.getjobdetails.Protocol.TestCaseHandler) activity;
        this.mainApplication = mainApplication;
        this.testCaseProgress = testCaseProgress;
        this.agentinfo = agentinfo;
        this.authGateWayHNIP = authGateWayHNIP;
        this.authGateWayPort = authGateWayPort;
        this.objExecution = objExecution;
        this.commonActivity = new CommonActivity();
    }


    /*
     * Used for initialization sub methods to executes
     * @param objExecution JobDetailBean.Execution object to check and start execution of test cases
     *
     * */
    public void initTestCase() {

        if (objExecution.getTestcaseName().equals("INIT_FAIL_WRONG_PORT")) {

            authGateWayPort += 1;
            callInitFromSDK(agentinfo, authGateWayHNIP, authGateWayPort);

        } else if (objExecution.getTestcaseName().equals("INIT")) {

            callInitFromSDK(agentinfo, authGateWayHNIP, authGateWayPort);

        } else if (objExecution.getTestcaseName().equals("INIT_FAIL_WRONG_APPID")) {

            agentinfo = agentinfo.substring(0, agentinfo.length() - 2);
            callInitFromSDK(agentinfo, authGateWayHNIP, authGateWayPort);

        } else if (objExecution.getTestcaseName().equals("INIT_FAIL_WRONG_PORT_AND_APPID")) {

            agentinfo = agentinfo.substring(0, agentinfo.length() - 2);
            authGateWayPort += 1;
            callInitFromSDK(agentinfo, authGateWayHNIP, authGateWayPort);

        } else if (objExecution.getTestcaseName().equals("TERMINATE")) {

            terminateFromSDK();

        } else {

            commonActivity.updateCanNotTest(testCaseHandler);
//            commonActivity.updateResult(testCaseHandler,Constant.resultCanNotTest,0,0,1);
        }

    }

    private void callInitFromSDK(String agentInfo, String authGateWayIP, int authGateWayPort) {

        RDNA.RDNALoggingLevel loggingLevel = RDNA.RDNALoggingLevel.RDNA_LOG_DEBUG;
        InitResponseCallBacks callBacks = new InitResponseCallBacks(mainApplication);
        initRDNA = Initialize(agentInfo, callBacks, authGateWayIP, authGateWayPort, null, null, null, null, null, loggingLevel, this);//  obj.result.

        if (initRDNA.errorCode != 0) {
//            commonActivity.resultChecker(testCaseHandler, initRDNA.errorCode, objExecution.getErrorCode());
            if(initRDNA.errorCode == objExecution.getErrorCode()) {
                caseStatus = Constant.resultPass;
                commonActivity.updatePassedTest(testCaseHandler);
            }else {
                caseStatus = Constant.resultFail;
                commonActivity.updateFailedTest(testCaseHandler);
            }
        }
    }

    private void testTerminate() {

/*        if(RDNAManager.getObjSyncRDNA() != null) {

            int terminateStat = RDNAManager.getObjSyncRDNA().result.terminate();
            commonActivity.resultChecker(testCaseHandler,terminateStat, objExecution.getErrorCode());

        }else {*/
        commonActivity.updateCanNotTest(testCaseHandler);
        /*}*/
    }


    private void terminateFromSDK() {

        testCaseProgress += 1;
        testCaseHandler.testCaseExecuted(1, 1, 0, 0, Constant.resultFail);

    }

    private void updateResultExplicitly(){

        if (caseStatus.equals(Constant.resultPass)) {
            commonActivity.updatePassedTest(testCaseHandler);
        } else if (caseStatus.equals(Constant.resultFail)) {
            commonActivity.updateFailedTest(testCaseHandler);
        } else if (caseStatus.equals(Constant.resultCanNotTest)) {
            commonActivity.updateCanNotTest(testCaseHandler);
        }

    }

    public class InitResponseCallBacks implements RDNA.RDNACallbacks {

        Application mainApplication;

        public InitResponseCallBacks(Application application) {
            this.mainApplication = application;
        }

        @Override
        public int onInitializeCompleted(final RDNA.RDNAStatusInit rdnaStatusInit) {
            if (rdnaStatusInit.errCode == objExecution.getErrorCode()) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        caseStatus = Constant.resultPass;

                        if (rdnaStatusInit.errCode == 0){
                            initRDNA.result.terminate();
                        }else {
                            updateResultExplicitly();
                        }
                    }
                });

            } else {
                //if it is false
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        caseStatus = Constant.resultFail;

                        if (rdnaStatusInit.errCode == 0){
                            initRDNA.result.terminate();
                        }else {
                            updateResultExplicitly();
                        }

                    }
                });
            }

            return 0;
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

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    updateResultExplicitly();
                }
            });

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
