package com.jobdetails.shant.getjobdetails.TestCaseActivity;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jobdetails.shant.getjobdetails.Beans.JobDetailBean;
import com.jobdetails.shant.getjobdetails.Beans.UserErrorBean;
import com.jobdetails.shant.getjobdetails.Beans.UserProfileBean;
import com.jobdetails.shant.getjobdetails.Common.CommonActivity;
import com.jobdetails.shant.getjobdetails.Common.Constant;
import com.jobdetails.shant.getjobdetails.Common.RDNAManager;
import com.jobdetails.shant.getjobdetails.Common.TestCaseManager;
import com.jobdetails.shant.getjobdetails.Network.APIEnrollUser;
import com.jobdetails.shant.getjobdetails.Protocol.APIInterface;
import com.jobdetails.shant.getjobdetails.Protocol.CheckChallenge;
import com.jobdetails.shant.getjobdetails.Protocol.TestCaseHandler;
import com.uniken.rdna.RDNA;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSessionActivity implements CheckChallenge{

    Application mainApplication;
    int testCaseProgress;
    String agentinfo;
    String authGateWayHNIP;
    int authGateWayPort;
    com.jobdetails.shant.getjobdetails.Protocol.TestCaseHandler testCaseHandler;
    JobDetailBean.Execution objExecution;
    CommonActivity commonActivity;
    //    private EventBus bus = EventBus.getDefault();
    private String userID = "nikhil1";
    private String actCode = "ASDF1H";
    private String password = "1e99b14aa45d6add97271f8e06adacda4e521ad98a4ed18e38cfb0715e7841d2";

    public UserSessionActivity(Object activity, Application mainApplication, int testCaseProgress, String agentinfo, String authGateWayHNIP, int authGateWayPort, JobDetailBean.Execution objExecution) {
        this.testCaseHandler = (com.jobdetails.shant.getjobdetails.Protocol.TestCaseHandler) activity;
        this.mainApplication = mainApplication;
        this.testCaseProgress = testCaseProgress;
        this.agentinfo = agentinfo;
        this.authGateWayHNIP = authGateWayHNIP;
        this.authGateWayPort = authGateWayPort;
        this.objExecution = objExecution;
        this.commonActivity = new CommonActivity();

    }

    public UserSessionActivity() {

    }

    public void initTestCase() {

        RDNAManager.getObjSyncRDNA().result.serviceAccessStartAll();

        switch (objExecution.getTestcaseName()) {

            case "ENROLL_USER":
                testEnrollUser();
                break;

            case "ENROLL_USER_DEVICE":
                testEnrollUserDevice();
                break;

            case "ENROLL_USER_STATUS":
                testUserStatus();
                break;

            case "UNENROLL_USER":
                testUnEnrollUser();
                break;

            case "GET_USER_ID":
                testUserId();
                break;

            case "ACTIVE_USER":
                RDNAManager.setTestCaseHandler(testCaseHandler);
                testActiveUser();
                break;

            default:
                commonActivity.updateCanNotTest(testCaseHandler);
                break;
        }
    }


    private void testActiveUser() {

        Map<String, Object> headerResult = getHeaderData();

        APIInterface apiInterface = APIEnrollUser.getRetrofitObj().create(APIInterface.class);
        Call<UserErrorBean> userErrorBeanCall = apiInterface.enrollUser(headerResult);

        userErrorBeanCall.enqueue(new Callback<UserErrorBean>() {
            @Override
            public void onResponse(Call<UserErrorBean> call, Response<UserErrorBean> response) {
                Log.e("gotEnrollResponse:", "Success");
                UserErrorBean userErrorBean = response.body();
                if (userErrorBean != null && !userErrorBean.getError()) {
                    activeUser(RDNAManager.getObjAsyncINIT().challenges, RDNA.RDNAResponseStatusCode.RDNA_RESP_STATUS_SUCCESS,commonActivity,testCaseHandler);
                } else {
                    commonActivity.updateFailedTest(testCaseHandler);
                }
            }

            @Override
            public void onFailure(Call<UserErrorBean> call, Throwable t) {
                Log.e("gotEnrollResponse:", "Fail");
                commonActivity.updateCanNotTest(testCaseHandler);
            }
        });

    }

    public void activeUser(RDNA.RDNAChallenge[] challenges, RDNA.RDNAResponseStatusCode statusCode, CommonActivity commonActivity, TestCaseHandler testCaseHandler) {

        if (statusCode == RDNA.RDNAResponseStatusCode.RDNA_RESP_STATUS_SUCCESS) {

            if (challenges != null && challenges.length > 0) {
                fillChallenge(challenges);
                int activationUserRes = RDNAManager.getObjSyncRDNA().result.checkChallengeResponse(challenges, userID);
                if (activationUserRes != 0) {
                    commonActivity.updateFailedTest(testCaseHandler);
                }

            } else {
                commonActivity.updatePassedTest(testCaseHandler);
            }
        } else {
            commonActivity.updateFailedTest(testCaseHandler);
        }

    }

    private void fillChallenge(RDNA.RDNAChallenge[] challenges) {

        for (int i = 0; i < challenges.length; i++) {
            String challengeKey = challenges[i].name;
            switch (challengeKey) {

                case "checkuser":
                    challenges[i].responseValue = userID;
                    break;

                case "actcode":
                    challenges[i].responseValue = actCode;
                    break;

                case "pass":
                    challenges[i].responseValue = "Uniken123$";
                    break;

                case "devbind":
                    challenges[i].responseValue = "true";
                    break;

                case "devname":
                    challenges[i].responseValue = "Android_Moto G Play_082118092940";
                    break;

                default:
                    break;

            }
        }

    }


   /* @Subscribe
    public void postRDNAChallenge(RDNA.RDNAChallenge[] rdnaChallenge){

        int activationUserRes = RDNAManager.getObjSyncRDNA().result.checkChallengeResponse(Constant.objINIT.challenges,"nikhil1");

        if(activationUserRes!=0){
            commonActivity.updateFailedTest(testCaseHandler);
        }

    }*/


    private Map<String, Object> getHeaderData() {

        RDNA.RDNAStatus<String> rdnaSession = RDNAManager.getObjSyncRDNA().result.getSessionID();
        UserProfileBean userProfileBean = new UserProfileBean("Nikhil", "Kanawade", userID, actCode, "group1", "nikhil.kanawade@uniken.com", "9898989898", "sruser", password, true, rdnaSession.result, "v1");

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(userProfileBean);

        Map<String, Object> headerResult = new Gson().fromJson(json, Map.class);
        headerResult.put("Content-Type", "application/x-www-form-urlencoded");
        headerResult.put("Content-Length", "0");

        return headerResult;
    }

    private void checkUserSessionResult(Response<UserErrorBean> response) {
        UserErrorBean userErrorBean = response.body();
        if (userErrorBean != null && !userErrorBean.getError()) {
            commonActivity.updatePassedTest(testCaseHandler);
//            activeUser(Constant.objINIT.challenges, RDNA.RDNAResponseStatusCode.RDNA_RESP_STATUS_SUCCESS);
        } else {
            commonActivity.updateFailedTest(testCaseHandler);
        }
    }

    private void testEnrollUser() {

        Map<String, Object> headerResult = getHeaderData();

        APIInterface apiInterface = APIEnrollUser.getRetrofitObj().create(APIInterface.class);
        Call<UserErrorBean> userErrorBeanCall = apiInterface.enrollUser(headerResult);

        userErrorBeanCall.enqueue(new Callback<UserErrorBean>() {
            @Override
            public void onResponse(Call<UserErrorBean> call, Response<UserErrorBean> response) {

                Log.e("gotEnrollResponse:", "Success");
                checkUserSessionResult(response);
            }

            @Override
            public void onFailure(Call<UserErrorBean> call, Throwable t) {
                Log.e("gotEnrollResponse:", "Fail");
                commonActivity.updateCanNotTest(testCaseHandler);
            }
        });
    }

    private void testEnrollUserDevice() {

        Map<String, Object> headerResult = getHeaderData();

        APIInterface apiInterface = APIEnrollUser.getRetrofitObj().create(APIInterface.class);
        Call<UserErrorBean> userErrorBeanCall = apiInterface.enrollUserDevice(headerResult);

        userErrorBeanCall.enqueue(new Callback<UserErrorBean>() {
            @Override
            public void onResponse(Call<UserErrorBean> call, Response<UserErrorBean> response) {

                Log.e("EnrollDeviceResponse:", "Success");
                checkUserSessionResult(response);
            }

            @Override
            public void onFailure(Call<UserErrorBean> call, Throwable t) {
                Log.e("EnrollDeviceResponse:", "Fail");
                commonActivity.updateCanNotTest(testCaseHandler);
            }
        });

    }

    private void testUserStatus() {

        Map<String, Object> headerResult = getHeaderData();

        APIInterface apiInterface = APIEnrollUser.getRetrofitObj().create(APIInterface.class);
        Call<UserErrorBean> userErrorBeanCall = apiInterface.getUserStatus(headerResult);

        userErrorBeanCall.enqueue(new Callback<UserErrorBean>() {
            @Override
            public void onResponse(Call<UserErrorBean> call, Response<UserErrorBean> response) {

                Log.e("userStatus:", "Success");
                checkUserSessionResult(response);
            }

            @Override
            public void onFailure(Call<UserErrorBean> call, Throwable t) {
                Log.e("userStatus:", "Fail");
                commonActivity.updateCanNotTest(testCaseHandler);
            }
        });

    }


    private void testUnEnrollUser() {

        Map<String, Object> headerResult = getHeaderData();

        APIInterface apiInterface = APIEnrollUser.getRetrofitObj().create(APIInterface.class);
        Call<UserErrorBean> userErrorBeanCall = apiInterface.unEnrollUser(headerResult);

        userErrorBeanCall.enqueue(new Callback<UserErrorBean>() {
            @Override
            public void onResponse(Call<UserErrorBean> call, Response<UserErrorBean> response) {

                Log.e("unEnrollUser:", "Success");
                checkUserSessionResult(response);
            }

            @Override
            public void onFailure(Call<UserErrorBean> call, Throwable t) {
                Log.e("unEnrollUser:", "Fail");
                commonActivity.updateCanNotTest(testCaseHandler);
            }
        });

    }


    private void testUserId() {

        Map<String, Object> headerResult = getHeaderData();

        APIInterface apiInterface = APIEnrollUser.getRetrofitObj().create(APIInterface.class);
        Call<UserErrorBean> userErrorBeanCall = apiInterface.getUserId(headerResult);

        userErrorBeanCall.enqueue(new Callback<UserErrorBean>() {
            @Override
            public void onResponse(Call<UserErrorBean> call, Response<UserErrorBean> response) {
                Log.e("userId:", "Success");
                checkUserSessionResult(response);
            }

            @Override
            public void onFailure(Call<UserErrorBean> call, Throwable t) {
                Log.e("userId:", "Fail");
                commonActivity.updateCanNotTest(testCaseHandler);
            }
        });

    }

    @Override
    public void onChallengeResponse(RDNA.RDNAChallenge[] challenges, RDNA.RDNAResponseStatusCode statusCode) {
//        activeUser(challenges, statusCode);
    }

}
