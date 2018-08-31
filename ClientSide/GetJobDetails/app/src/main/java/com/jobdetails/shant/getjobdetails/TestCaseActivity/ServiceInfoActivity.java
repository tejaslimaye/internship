package com.jobdetails.shant.getjobdetails.TestCaseActivity;

import android.app.Application;
import android.util.Log;

import com.jobdetails.shant.getjobdetails.Beans.JobDetailBean;
import com.jobdetails.shant.getjobdetails.Common.CommonActivity;
import com.jobdetails.shant.getjobdetails.Common.Constant;
import com.jobdetails.shant.getjobdetails.Common.RDNAManager;
import com.jobdetails.shant.getjobdetails.Common.TestCaseManager;
import com.uniken.rdna.RDNA;

public class ServiceInfoActivity {

    Application mainApplication;
    int testCaseProgress;
    String agentinfo;
    String authGateWayHNIP;
    int authGateWayPort;
    com.jobdetails.shant.getjobdetails.Protocol.TestCaseHandler testCaseHandler;
    JobDetailBean.Execution objExecution;
    CommonActivity commonActivity;

    public ServiceInfoActivity(Object activity, Application mainApplication, int testCaseProgress, String agentinfo, String authGateWayHNIP, int authGateWayPort, JobDetailBean.Execution objExecution){
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

        switch (objExecution.getTestcaseName()){

            case "GET_SERVICE_BY_SERVICE_NAME":
                getServiceByName();
                break;
            case "GET_SERVICE_BY_WRONG_SERVICE_NAME":
                getServiceByWrongName("ASDF");
                break;
            case "GET_SERVICE_BY_TARGET_COORDINATE":
                getServiceDetailsByCoordinate("GET_SERVICE_BY_TARGET_COORDINATE");
                break;
            case "GET_SERVICE_BY_TARGET_COORDINATE_WRONG_IP":
                getServiceDetailsByCoordinate("GET_SERVICE_BY_TARGET_COORDINATE_WRONG_IP");
                break;
            case "GET_SERVICE_BY_TARGET_COORDINATE_WRONG_PORT":
                getServiceDetailsByCoordinate("GET_SERVICE_BY_TARGET_COORDINATE_WRONG_PORT");
                break;
            case "GET_SERVICE_BY_TARGET_COORDINATE_WRONG_IP_AND_PORT":
                getServiceDetailsByCoordinate("GET_SERVICE_BY_TARGET_COORDINATE_WRONG_IP_AND_PORT");
                break;
            case "GET_ALL_SERVICES":
                getAllServices();
                break;
            case "SERVICE_ACCESS_START_WRONG_SERVICE_NAME":
                startServiceOperation("SERVICE_ACCESS_START_WRONG_SERVICE_NAME");
                break;
            case "SERVICE_ACCESS_START_RUNNING_SERVICE":
                startServiceOperation("SERVICE_ACCESS_START_RUNNING_SERVICE");
                break;
            case "SERVICE_ACCESS_START_STOPPED_SERVICE":
                startServiceOperation("SERVICE_ACCESS_START_STOPPED_SERVICE");
                break;
            case "SERVICE_ACCESS_STOP_WRONG_SERVICE_NAME":
                stopServiceOperation("SERVICE_ACCESS_STOP_WRONG_SERVICE_NAME");
                break;
            case "SERVICE_ACCESS_STOP_RUNNING_SERVICE":
                stopServiceOperation("SERVICE_ACCESS_STOP_RUNNING_SERVICE");
                break;
            case "SERVICE_ACCESS_STOP_STOPPED_SERVICE":
                stopServiceOperation("SERVICE_ACCESS_STOP_STOPPED_SERVICE");
                break;
            case "SERVICE_ACCESS_START_ALL_SERVICE":
                startAllService();
                break;
            case "SERVICE_ACCESS_STOP_ALL_SERVICE":
                stopAllService();
                break;

            default:
//                canNotTestUpdation();
                commonActivity.updateCanNotTest(testCaseHandler);
                break;
        }
    }


    private void getAllServices(){

        if(RDNAManager.getObjSyncRDNA() != null) {

            RDNA.RDNAStatus<RDNA.RDNAService[]> objService = RDNAManager.getObjSyncRDNA().result.getAllServices();
            commonActivity.resultChecker(testCaseHandler,objService.errorCode, objExecution.getErrorCode());

        }else {
            commonActivity.updateCanNotTest(testCaseHandler);
        }
    }

    private RDNA.RDNAService getServiceDetails(){

        RDNA.RDNAService firstService = null;

        if(RDNAManager.getObjSyncRDNA() != null) {

            RDNA.RDNAStatus<RDNA.RDNAService[]> objService = RDNAManager.getObjSyncRDNA().result.getAllServices();
            if (objService.errorCode == 0){

                RDNA.RDNAService[] RDNAServicesAll = objService.result;
                if(RDNAServicesAll.length > 0){

                    firstService = RDNAServicesAll[0];

                    return firstService;

                } else {
                    Log.e("ServiceStat:","No service availabel");
                }

            } else {
                Log.e("ServiceStat:","Initialized failed");
            }
        }
        return firstService;
    }

    private void getServiceByName(){

        RDNA.RDNAService firstService = getServiceDetails();

        if(firstService != null){

            String serviceName = firstService.serviceName;
            int targetPort = firstService.targetPort;
            String targetHNIP = firstService.targetHNIP;
            RDNA.RDNAPort portInfo = firstService.portInfo;

            RDNA.RDNAStatus<RDNA.RDNAService> RDNAStatus = RDNAManager.getObjSyncRDNA().result.getServiceByServiceName(serviceName);

            commonActivity.resultChecker(testCaseHandler,RDNAStatus.errorCode, objExecution.getErrorCode());

        }else {
            commonActivity.updateCanNotTest(testCaseHandler);
        }
    }

    private void getServiceByWrongName(String serviceName){

        RDNA.RDNAService firstService = getServiceDetails();

        if(firstService != null){

            RDNA.RDNAStatus<RDNA.RDNAService> RDNAStatus = RDNAManager.getObjSyncRDNA().result.getServiceByServiceName(serviceName);
            commonActivity.resultChecker(testCaseHandler,RDNAStatus.errorCode, objExecution.getErrorCode());

        }else {
            commonActivity.updateCanNotTest(testCaseHandler);
        }
    }



    private void getServiceDetailsByCoordinate(String testCaseName){

        RDNA.RDNAService firstService = getServiceDetails();

        if(firstService != null){

            String servicename = firstService.serviceName;
            int targetPort = firstService.targetPort;
            String targetHNIP = firstService.targetHNIP;
            RDNA.RDNAPort portInfo = firstService.portInfo;

            switch (testCaseName){

                case "GET_SERVICE_BY_TARGET_COORDINATE":
                    break;

                case "GET_SERVICE_BY_TARGET_COORDINATE_WRONG_IP":
                    targetHNIP = targetHNIP.substring(0,targetHNIP.length() - 2);
                    break;

                case "GET_SERVICE_BY_TARGET_COORDINATE_WRONG_PORT":
                    targetPort = targetPort + 1;
                    break;

                case "GET_SERVICE_BY_TARGET_COORDINATE_WRONG_IP_AND_PORT":
                    targetHNIP = targetHNIP.substring(0,targetHNIP.length() - 2);
                    targetPort = targetPort + 1;
                    break;

                    default:
                        commonActivity.updateCanNotTest(testCaseHandler);
                        break;
            }


            RDNA.RDNAStatus<RDNA.RDNAService[]> RDNAStatus = RDNAManager.getObjSyncRDNA().result.getServiceByTargetCoordinate(targetHNIP,targetPort);
            commonActivity.resultChecker(testCaseHandler,RDNAStatus.errorCode, objExecution.getErrorCode());

        }else {
            commonActivity.updateCanNotTest(testCaseHandler);
        }
    }


    private void startServiceOperation(String serviceStatus){

        RDNA.RDNAService firstService = getServiceDetails();

        if(firstService != null){

            String servicename = firstService.serviceName;
            int targetPort = firstService.targetPort;
            String targetHNIP = firstService.targetHNIP;
            RDNA.RDNAPort portInfo = firstService.portInfo;

            int serviceStat;

            switch (serviceStatus) {

                case "SERVICE_ACCESS_START_WRONG_SERVICE_NAME":
                    firstService.serviceName = "ASDDD";
                    serviceStat = RDNAManager.getObjSyncRDNA().result.serviceAccessStart(firstService);
                    break;

                case "SERVICE_ACCESS_START_RUNNING_SERVICE":
                    int startStatus = RDNAManager.getObjSyncRDNA().result.serviceAccessStart(firstService);
                    serviceStat = RDNAManager.getObjSyncRDNA().result.serviceAccessStart(firstService);
                    break;
                case "SERVICE_ACCESS_START_STOPPED_SERVICE":
                    int stopStatus = RDNAManager.getObjSyncRDNA().result.serviceAccessStop(firstService);
                    serviceStat = RDNAManager.getObjSyncRDNA().result.serviceAccessStart(firstService);
                    break;

                default:
                    serviceStat = 1;
                    break;

            }
            commonActivity.resultChecker(testCaseHandler,serviceStat, objExecution.getErrorCode());

        }else {
            commonActivity.updateCanNotTest(testCaseHandler);
        }
    }


    private void stopServiceOperation(String serviceStatus){

        RDNA.RDNAService firstService = getServiceDetails();

        if(firstService != null){

            String servicename = firstService.serviceName;
            int targetPort = firstService.targetPort;
            String targetHNIP = firstService.targetHNIP;
            RDNA.RDNAPort portInfo = firstService.portInfo;

            int serviceStat;

            switch (serviceStatus) {

                case "SERVICE_ACCESS_STOP_WRONG_SERVICE_NAME":
                    firstService.serviceName = "ASDDD";
                    serviceStat = RDNAManager.getObjSyncRDNA().result.serviceAccessStop(firstService);
                    break;

                case "SERVICE_ACCESS_STOP_RUNNING_SERVICE":
                    int startStatus = RDNAManager.getObjSyncRDNA().result.serviceAccessStart(firstService);
                    serviceStat = RDNAManager.getObjSyncRDNA().result.serviceAccessStop(firstService);
                    break;
                case "SERVICE_ACCESS_STOP_STOPPED_SERVICE":
                    int stopStatus = RDNAManager.getObjSyncRDNA().result.serviceAccessStop(firstService);
                    serviceStat = RDNAManager.getObjSyncRDNA().result.serviceAccessStop(firstService);
                    break;

                default:
                    serviceStat = 1;
                    break;

            }
            commonActivity.resultChecker(testCaseHandler,serviceStat, objExecution.getErrorCode());

        }else {
            commonActivity.updateCanNotTest(testCaseHandler);
        }
    }

    private void startAllService(){

        RDNA.RDNAService firstService = getServiceDetails();

        if(firstService != null){

            int serviceStat = RDNAManager.getObjSyncRDNA().result.serviceAccessStartAll();
            commonActivity.resultChecker(testCaseHandler,serviceStat, objExecution.getErrorCode());

        }else {
            commonActivity.updateCanNotTest(testCaseHandler);
        }

    }

    private void stopAllService(){

        RDNA.RDNAService firstService = getServiceDetails();

        if(firstService != null){

            int serviceStat = RDNAManager.getObjSyncRDNA().result.serviceAccessStopAll();
            commonActivity.resultChecker(testCaseHandler,serviceStat, objExecution.getErrorCode());

        }else {
            commonActivity.updateCanNotTest(testCaseHandler);
        }

    }
}
