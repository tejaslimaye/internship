package com.jobdetails.shant.getjobdetails.TestCaseActivity;

import android.app.Application;

import com.jobdetails.shant.getjobdetails.Beans.JobDetailBean;
import com.jobdetails.shant.getjobdetails.Common.CommonActivity;
import com.jobdetails.shant.getjobdetails.Common.Constant;
import com.jobdetails.shant.getjobdetails.Common.RDNAManager;
import com.jobdetails.shant.getjobdetails.Common.TestCaseManager;
import com.uniken.rdna.RDNA;

public class DataPrivacyActivity {

    Application mainApplication;
    int testCaseProgress;
    String agentInfo;
    String authGateWayHNIP;
    int authGateWayPort;
    com.jobdetails.shant.getjobdetails.Protocol.TestCaseHandler testCaseHandler;
    JobDetailBean.Execution objExecution;
    CommonActivity commonActivity;

    private String encryptionString = "UNIKEN";

    public DataPrivacyActivity(Object activity, Application mainApplication, int testCaseProgress, String agentInfo, String authGateWayHNIP, int authGateWayPort, JobDetailBean.Execution objExecution){
        this.testCaseHandler = (com.jobdetails.shant.getjobdetails.Protocol.TestCaseHandler) activity;
        this.mainApplication = mainApplication;
        this.testCaseProgress = testCaseProgress;
        this.agentInfo = agentInfo;
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

        switch (objExecution.getTestcaseName()){

            case "ENCRYPT_DATA_PACKET":
                testEncryptDataPacket("ENCRYPT_DATA_PACKET");
                break;

            case "ENCRYPT_DATA_PACKET_EMPTY_CIPHERSPEC":
                testEncryptDataPacket("ENCRYPT_DATA_PACKET_EMPTY_CIPHERSPEC");
                break;

            case "ENCRYPT_DATA_PACKET_GET_EMPTY_CIPHERSALT":
                testEncryptDataPacket("ENCRYPT_DATA_PACKET_GET_EMPTY_CIPHERSALT");
                break;

            case "ENCRYPT_DATA_PACKET_GET_EMPTY_PLAINTEXT":
                testEncryptDataPacket("ENCRYPT_DATA_PACKET_GET_EMPTY_PLAINTEXT");
                break;

            case "DECRYPT_DATA_PACKET":
                testDecryptDataPacket("DECRYPT_DATA_PACKET");
                break;

            case "DECRYPT_DATA_PACKET_EMPTY_CIPHERSPEC":
                testDecryptDataPacket("DECRYPT_DATA_PACKET_EMPTY_CIPHERSPEC");
                break;

            case "DECRYPT_DATA_PACKET_EMPTY_CIPHERSALT":
                testDecryptDataPacket("DECRYPT_DATA_PACKET_EMPTY_CIPHERSALT");
                break;

            case "DECRYPT_DATA_PACKET_GET_EMPTY_PLAINTEXT":
                testDecryptDataPacket("DECRYPT_DATA_PACKET_GET_EMPTY_PLAINTEXT");
                break;

            default:
                commonActivity.updateCanNotTest(testCaseHandler);
                break;
        }
    }

    private void testEncryptDataPacket(String testCase){

        if(RDNAManager.getObjSyncRDNA() != null) {

            RDNA.RDNAStatus<byte[]> cipherDefaultSalt = RDNAManager.getObjSyncRDNA().result.getDefaultCipherSalt();
            RDNA.RDNAStatus<String> cipherDefaultSpec = RDNAManager.getObjSyncRDNA().result.getDefaultCipherSpec();

            int privacyScope = 1;
            String cipherSpec = cipherDefaultSpec.result;
            byte[] cipherSalt = cipherDefaultSalt.result;
            byte[] encryptBytes = encryptionString.getBytes();

            switch (testCase){

                case "ENCRYPT_DATA_PACKET":
                    break;

                case "ENCRYPT_DATA_PACKET_GET_EMPTY_CIPHERSALT":
                    cipherSalt = null;
                    break;

                case "ENCRYPT_DATA_PACKET_GET_EMPTY_PLAINTEXT":
                    encryptBytes = "".getBytes();
                    break;

                case "ENCRYPT_DATA_PACKET_EMPTY_CIPHERSPEC":
                    cipherSpec = "";
                    break;

                    default:
                        commonActivity.updateCanNotTest(testCaseHandler);
                        break;

            }

            RDNA.RDNAStatus<byte[]> encryptStat = RDNAManager.getObjSyncRDNA().result.encryptDataPacket(privacyScope, cipherSpec, cipherSalt, encryptBytes);

            commonActivity.resultChecker(testCaseHandler,encryptStat.errorCode, objExecution.getErrorCode());

        }else {
            commonActivity.updateCanNotTest(testCaseHandler);
        }

    }


    private void testDecryptDataPacket(String testCase){

        if(RDNAManager.getObjSyncRDNA() != null) {

            RDNA.RDNAStatus<byte[]> cipherDefaultSalt = RDNAManager.getObjSyncRDNA().result.getDefaultCipherSalt();
            RDNA.RDNAStatus<String> cipherDefaultSpec = RDNAManager.getObjSyncRDNA().result.getDefaultCipherSpec();

            RDNA.RDNAStatus<byte[]> encryptStat = RDNAManager.getObjSyncRDNA().result.encryptDataPacket(1, cipherDefaultSpec.result, cipherDefaultSalt.result, encryptionString.getBytes());

            int privacyScope = 1;
            String cipherSpec = cipherDefaultSpec.result;
            byte[] cipherSalt = cipherDefaultSalt.result;
            byte[] encryptedBytes = encryptStat.result;

            switch (testCase){

                case "DECRYPT_DATA_PACKET":
                    break;

                case "DECRYPT_DATA_PACKET_EMPTY_CIPHERSPEC":
                    cipherSpec = "";
                    break;

                case "DECRYPT_DATA_PACKET_EMPTY_CIPHERSALT":
                    cipherSalt = "".getBytes();
                    break;

                case "DECRYPT_DATA_PACKET_GET_EMPTY_PLAINTEXT":
                    encryptedBytes = "".getBytes();
                    break;


                default:
                    commonActivity.updateCanNotTest(testCaseHandler);
                    break;

            }

            RDNA.RDNAStatus<byte[]> decryptStat = RDNAManager.getObjSyncRDNA().result.decryptDataPacket(privacyScope, cipherSpec, cipherSalt, encryptedBytes);

            commonActivity.resultChecker(testCaseHandler,decryptStat.errorCode, objExecution.getErrorCode());

        }else {
            commonActivity.updateCanNotTest(testCaseHandler);
        }

    }

}
