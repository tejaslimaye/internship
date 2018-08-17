package com.jobdetails.shant.getjobdetails.TestCaseActivity;

import android.app.Application;

import com.jobdetails.shant.getjobdetails.Beans.JobDetailBean;
import com.jobdetails.shant.getjobdetails.Common.CommonActivity;
import com.jobdetails.shant.getjobdetails.Common.Constant;
import com.jobdetails.shant.getjobdetails.Common.TestAPIResult;
import com.jobdetails.shant.getjobdetails.Protocol.TestCaseHandler;
import com.uniken.rdna.RDNA;

import static com.jobdetails.shant.getjobdetails.Common.Constant.rdnaInitSuccess;
import static com.uniken.rdna.RDNA.Initialize;

public class InitializationActivity {

    Application mainApplication;
    int testCaseProgress;
    String agentinfo;
    String authGateWayHNIP;
    int authGateWayPort;
    TestAPIResult.APIResponseCallBacks callBacks;
    TestCaseHandler testCaseHandler;
    JobDetailBean.Execution objExecution;
    CommonActivity commonActivity;

    public InitializationActivity(Object activity, Application mainApplication, int testCaseProgress, String agentinfo, String authGateWayHNIP, int authGateWayPort, TestAPIResult.APIResponseCallBacks callBacks,JobDetailBean.Execution objExecution){
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

        if (objExecution.getTestcaseName().equals("INIT_FAIL_WRONG_PORT")) {

            authGateWayPort += 1;
            callInitFromSDK(agentinfo, authGateWayHNIP, authGateWayPort);

        } else if (objExecution.getTestcaseName().equals("INIT")) {

            callInitFromSDK(agentinfo, authGateWayHNIP, authGateWayPort);

        } else if (objExecution.getTestcaseName().equals("INIT_FAIL_WRONG_APPID")) {

            agentinfo = agentinfo.substring(0,agentinfo.length() -2);
            callInitFromSDK(agentinfo, authGateWayHNIP, authGateWayPort);

        }  else if (objExecution.getTestcaseName().equals("INIT_FAIL_WRONG_PORT_AND_APPID")) {

            agentinfo = agentinfo.substring(0,agentinfo.length() -2);
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
        RDNA.RDNAStatus<RDNA> objRDNA = Initialize(agentInfo, callBacks, authGateWayIP, authGateWayPort, null, null, null, null, null, loggingLevel, this);//  obj.result.

        if(!rdnaInitSuccess){
            Constant.objRDNA = objRDNA;
        }

        if (objRDNA.errorCode != 0) {
            commonActivity.resultChecker(testCaseHandler,objRDNA.errorCode, objExecution.getErrorCode());
        }
    }

    private void testTerminate(){

        if(Constant.objRDNA != null) {

            int terminateStat = Constant.objRDNA.result.terminate();
            commonActivity.resultChecker(testCaseHandler,terminateStat, objExecution.getErrorCode());

        }else {
            commonActivity.updateCanNotTest(testCaseHandler);
        }
    }


    private void terminateFromSDK() {

        testCaseProgress += 1;
        testCaseHandler.testCaseExecuted(1,1,0,0, Constant.resultFail);

    }

}
