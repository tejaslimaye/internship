package com.jobdetails.shant.getjobdetails.TestCaseActivity;

import android.app.Application;

import com.jobdetails.shant.getjobdetails.Beans.JobDetailBean;
import com.jobdetails.shant.getjobdetails.Common.CommonActivity;
import com.jobdetails.shant.getjobdetails.Common.Constant;
import com.jobdetails.shant.getjobdetails.Common.TestAPIResult;
import com.jobdetails.shant.getjobdetails.Protocol.TestCaseHandler;
import com.uniken.rdna.RDNA;

public class InfoGettersActivity {

    Application mainApplication;
    int testCaseProgress;
    String agentinfo;
    String authGateWayHNIP;
    int authGateWayPort;
    TestAPIResult.APIResponseCallBacks callBacks;
    TestCaseHandler testCaseHandler;
    JobDetailBean.Execution objExecution;
    CommonActivity commonActivity;

    public InfoGettersActivity(Object activity, Application mainApplication, int testCaseProgress, String agentinfo, String authGateWayHNIP, int authGateWayPort, TestAPIResult.APIResponseCallBacks callBacks,JobDetailBean.Execution objExecution){
        this.testCaseHandler = (TestCaseHandler) activity;
        this.mainApplication = mainApplication;
        this.testCaseProgress = testCaseProgress;
        this.agentinfo = agentinfo;
        this.authGateWayHNIP = authGateWayHNIP;
        this.authGateWayPort = authGateWayPort;
        this.callBacks = callBacks;
        this.objExecution = objExecution;
        this.commonActivity = new CommonActivity();

    }

    /*
     * Used for initialization sub methods to executes
     * @param objExecution JobDetailBean.Execution object to check and start execution of test cases
     *
     * */
    public void initTestCase() {

        switch (objExecution.getTestcaseName()){

            case "GET_DEFAULT_CIPHER_SPEC":
                testDefaultCipherSpec();
                break;

            case "GET_DEFAULT_CIPHER_SALT":
                testDefaultCipherSalt();
                break;

            case "GET_API_SDK_VERSION":
                testSDKVersion();
                break;

            case "GET_ERROR_CODE":
                testErrorCode();
                break;

            case "GET_SESSION_ID":
                testSessionID();
                break;

            case "GET_DEVICE_ID":
                testGetDeviceID();
                break;

            case "GET_AGENT_ID":
                testAgentID();
                break;

            default:
                commonActivity.updateCanNotTest(testCaseHandler);
                break;
        }
    }

    private void testDefaultCipherSpec(){

        if(Constant.objRDNA != null) {
            RDNA.RDNAStatus<String> sdkStat = Constant.objRDNA.result.getDefaultCipherSpec();
            commonActivity.resultChecker(testCaseHandler,sdkStat.errorCode, objExecution.getErrorCode());
        }else {
            commonActivity.updateCanNotTest(testCaseHandler);
        }
    }

    private void testDefaultCipherSalt(){

        if(Constant.objRDNA != null) {

            RDNA.RDNAStatus<byte[]> sdkStat = Constant.objRDNA.result.getDefaultCipherSalt();
            commonActivity.resultChecker(testCaseHandler,sdkStat.errorCode, objExecution.getErrorCode());

        }else {
            commonActivity.updateCanNotTest(testCaseHandler);
        }

    }

    private void testSDKVersion(){

        if(Constant.objRDNA != null) {

            String sdkStat = Constant.objRDNA.result.getSDKVersion();
            if(!sdkStat.equalsIgnoreCase("")){
                commonActivity.updatePassedTest(testCaseHandler);
            }else {
                commonActivity.updateFailedTest(testCaseHandler);
            }

        }else {
            commonActivity.updateCanNotTest(testCaseHandler);
        }
    }

    private void testErrorCode(){

        if(Constant.objRDNA != null) {

            RDNA.RDNAErrorID errorStat = Constant.objRDNA.result.getErrorInfo(291504204);
            if(!errorStat.name().equalsIgnoreCase("")){
                commonActivity.updatePassedTest(testCaseHandler);
            }else {
                commonActivity.updateFailedTest(testCaseHandler);
            }

        }else {
            commonActivity.updateCanNotTest(testCaseHandler);
        }
    }

    private void testSessionID(){

        if(Constant.objRDNA != null) {

            RDNA.RDNAStatus<String> RDNAStat = Constant.objRDNA.result.getSessionID();
            commonActivity.resultChecker(testCaseHandler,RDNAStat.errorCode, objExecution.getErrorCode());

        }else {
            commonActivity.updateCanNotTest(testCaseHandler);
        }

    }

    private void testGetDeviceID(){

        if(Constant.objRDNA != null) {

            RDNA.RDNAStatus<String> RDNAStat = Constant.objRDNA.result.getDeviceID();
            commonActivity.resultChecker(testCaseHandler,RDNAStat.errorCode, objExecution.getErrorCode());

        }else {
            commonActivity.updateCanNotTest(testCaseHandler);
        }

    }

    private void testAgentID(){

        if(Constant.objRDNA != null) {

            RDNA.RDNAStatus<String> RDNAStat = Constant.objRDNA.result.getAgentID();
            commonActivity.resultChecker(testCaseHandler,RDNAStat.errorCode, objExecution.getErrorCode());

        }else {
            commonActivity.updateCanNotTest(testCaseHandler);
        }
    }

}
