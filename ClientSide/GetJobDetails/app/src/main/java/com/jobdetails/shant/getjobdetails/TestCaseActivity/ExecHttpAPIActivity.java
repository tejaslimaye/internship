package com.jobdetails.shant.getjobdetails.TestCaseActivity;

import android.app.Application;

import com.jobdetails.shant.getjobdetails.Beans.JobDetailBean;
import com.jobdetails.shant.getjobdetails.Common.CommonActivity;
import com.jobdetails.shant.getjobdetails.Common.Constant;
import com.jobdetails.shant.getjobdetails.Common.TestAPIResult;
import com.jobdetails.shant.getjobdetails.Protocol.TestCaseHandler;
import com.uniken.rdna.RDNA;

public class ExecHttpAPIActivity {

    Application mainApplication;
    int testCaseProgress;
    String agentinfo;
    String authGateWayHNIP;
    int authGateWayPort;
    TestAPIResult.APIResponseCallBacks callBacks;
    TestCaseHandler testCaseHandler;
    JobDetailBean.Execution objExecution;
    CommonActivity commonActivity;

    public ExecHttpAPIActivity(Object activity, Application mainApplication, int testCaseProgress, String agentinfo, String authGateWayHNIP, int authGateWayPort, TestAPIResult.APIResponseCallBacks callBacks,JobDetailBean.Execution objExecution){
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

            case "ENCRYPT_HTTP_REQUEST":
                canNotTestUpdation();
                break;

            case "DECRYPT_HTTP_RESPONSE":
                canNotTestUpdation();
                break;


            default:
                canNotTestUpdation();
                break;
        }
    }

   /*  private void testEncryptHttpRequest(){

        if(Constant.objRDNA != null) {

            RDNA.RDNAStatus<byte[]> cipherDefaultSalt = Constant.objRDNA.result.getDefaultCipherSalt();
            RDNA.RDNAStatus<String> cipherDefaultSpec = Constant.objRDNA.result.getDefaultCipherSpec();

            int privacyScope = 1;
            String cipherSpec = cipherDefaultSpec.result;
            byte[] cipherSalt = cipherDefaultSalt.result;
//            byte[] encryptedBytes = encryptStat.result;

            RDNA.RDNAStatus<byte[]> sdkStat = Constant.objRDNA.result.encryptHttpRequest();
            if(sdkStat.errorCode == objExecution.getErrorCode()) {
                commonActivity.updateResult(testCaseHandler,Constant.resultPass,1,0,0);
            }else {
                commonActivity.updateResult(testCaseHandler,Constant.resultFail,0,1,0);
            }

        }else {
            canNotTestUpdation();
        }

    }

    private void testDecryptHttpResponse() {

        if(Constant.objRDNA != null) {

            RDNA.RDNAStatus<byte[]> cipherDefaultSalt = Constant.objRDNA.result.getDefaultCipherSalt();
            RDNA.RDNAStatus<String> cipherDefaultSpec = Constant.objRDNA.result.getDefaultCipherSpec();

            RDNA.RDNAStatus<byte[]> encryptStat = Constant.objRDNA.result.encryptDataPacket(1, cipherDefaultSpec.result, cipherDefaultSalt.result, encyptionString.getBytes());

            int privacyScope = 1;
            String cipherSpec = cipherDefaultSpec.result;
            byte[] cipherSalt = cipherDefaultSalt.result;
            byte[] encryptedBytes = encryptStat.result;

            RDNA.RDNAStatus<byte[]> sdkStat = Constant.objRDNA.result.decryptHttpResponse();
            if(sdkStat.errorCode == objExecution.getErrorCode()) {
                commonActivity.updateResult(testCaseHandler,Constant.resultPass,1,0,0);
            }else {
                commonActivity.updateResult(testCaseHandler,Constant.resultFail,0,1,0);
            }

        }else {
            canNotTestUpdation();
        }

    }
*/
    private void canNotTestUpdation(){

        testCaseProgress += 1;
        testCaseHandler.testCaseExecuted(1,0,0,1, Constant.resultCanNotTest);

    }
}
