package com.jobdetails.shant.getjobdetails.Common;

import com.jobdetails.shant.getjobdetails.Protocol.TestCaseHandler;
import com.uniken.rdna.RDNA;

/*
* Manage RDNA objects
* */
public class RDNAManager {

    private static RDNA.RDNAStatus<RDNA> objSyncRDNA;
    private static RDNA.RDNAStatusInit objAsyncINIT;
    private static boolean rdnaInitSuccess = false;
    private static TestCaseHandler testCaseHandler;

    public static TestCaseHandler getTestCaseHandler() {
        return testCaseHandler;
    }

    public static void setTestCaseHandler(TestCaseHandler testCaseHandler) {
        RDNAManager.testCaseHandler = testCaseHandler;
    }

    public static boolean isRdnaInitSuccess() {
        return rdnaInitSuccess;
    }

    public static void setRdnaInitSuccess(boolean rdnaInitSuccess) {
        RDNAManager.rdnaInitSuccess = rdnaInitSuccess;
    }

    public static RDNA.RDNAStatus<RDNA> getObjSyncRDNA() {
        return objSyncRDNA;
    }

    public static void setObjSyncRDNA(RDNA.RDNAStatus<RDNA> objSyncRDNA) {
        RDNAManager.objSyncRDNA = objSyncRDNA;
    }

    public static RDNA.RDNAStatusInit getObjAsyncINIT() {
        return objAsyncINIT;
    }

    public static void setObjAsyncINIT(RDNA.RDNAStatusInit objAsyncINIT) {
        RDNAManager.objAsyncINIT = objAsyncINIT;
    }
}
