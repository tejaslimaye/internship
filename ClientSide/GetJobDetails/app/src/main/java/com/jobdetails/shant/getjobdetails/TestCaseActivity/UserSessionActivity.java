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
import com.jobdetails.shant.getjobdetails.Common.TestAPIResult;
import com.jobdetails.shant.getjobdetails.Network.APIEnrollUser;
import com.jobdetails.shant.getjobdetails.Protocol.APIInterface;
import com.jobdetails.shant.getjobdetails.Protocol.TestCaseHandler;
import com.uniken.rdna.RDNA;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSessionActivity {

    Application mainApplication;
    int testCaseProgress;
    String agentinfo;
    String authGateWayHNIP;
    int authGateWayPort;
    TestAPIResult.APIResponseCallBacks callBacks;
    TestCaseHandler testCaseHandler;
    JobDetailBean.Execution objExecution;
    CommonActivity commonActivity;

    public UserSessionActivity(Object activity, Application mainApplication, int testCaseProgress, String agentinfo, String authGateWayHNIP, int authGateWayPort, TestAPIResult.APIResponseCallBacks callBacks,JobDetailBean.Execution objExecution){
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

    public UserSessionActivity() {

    }

    public void initTestCase() {

        switch (objExecution.getTestcaseName()){

            case "ENROLL_USER":
                testCustomEnrollUser();
                break;

            default:
//                canNotTestUpdation();
                commonActivity.updateCanNotTest(testCaseHandler);
                break;
        }
    }

    private Map<String,Object> getHeaderData(){

        RDNA.RDNAStatus<String> rdnaSession = Constant.objRDNA.result.getSessionID();
        UserProfileBean userProfileBean = new UserProfileBean("Nikhil","Kanawade","nikhil1","ASDF1H","group1","nikhil.kanawade@uniken.com","9898989898","sruser","1e99b14aa45d6add97271f8e06adacda4e521ad98a4ed18e38cfb0715e7841d2",true, rdnaSession.result,"v1");

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(userProfileBean);

        Map<String,Object> headerResult = new Gson().fromJson(json, Map.class);
        headerResult.put("Content-Type","application/x-www-form-urlencoded");
        headerResult.put("Content-Length","0");

        return headerResult;
    }

    public void testCustomEnrollUser() {

        Map<String,Object> headerResult = getHeaderData();

        APIInterface apiInterface = APIEnrollUser.getRetrofitObj().create(APIInterface.class);
        Call<UserErrorBean> userErrorBeanCall = apiInterface.enrollUser(headerResult);

        userErrorBeanCall.enqueue(new Callback<UserErrorBean>() {
            @Override
            public void onResponse(Call<UserErrorBean> call, Response<UserErrorBean> response) {

                Log.e("gotEnrollResponse:","Success");
            }

            @Override
            public void onFailure(Call<UserErrorBean> call, Throwable t) {
                Log.e("gotEnrollResponse:","Fail");
            }
        });
    }

    public void testEnrollUserDevice(){

        Map<String,Object> headerResult = getHeaderData();

        APIInterface apiInterface = APIEnrollUser.getRetrofitObj().create(APIInterface.class);
        Call<UserErrorBean> userErrorBeanCall = apiInterface.enrollUserDevice(headerResult);

        userErrorBeanCall.enqueue(new Callback<UserErrorBean>() {
            @Override
            public void onResponse(Call<UserErrorBean> call, Response<UserErrorBean> response) {

                Log.e("EnrollDeviceResponse:","Success");
            }

            @Override
            public void onFailure(Call<UserErrorBean> call, Throwable t) {
                Log.e("EnrollDeviceResponse:","Fail");
            }
        });

    }

    public void testUserStatus(){

        Map<String,Object> headerResult = getHeaderData();

        APIInterface apiInterface = APIEnrollUser.getRetrofitObj().create(APIInterface.class);
        Call<UserErrorBean> userErrorBeanCall = apiInterface.getUserStatus(headerResult);

        userErrorBeanCall.enqueue(new Callback<UserErrorBean>() {
            @Override
            public void onResponse(Call<UserErrorBean> call, Response<UserErrorBean> response) {

                Log.e("userStatus:","Success");
            }

            @Override
            public void onFailure(Call<UserErrorBean> call, Throwable t) {
                Log.e("userStatus:","Fail");
            }
        });

    }


    public void testUnEnrollUser(){

        Map<String,Object> headerResult = getHeaderData();

        APIInterface apiInterface = APIEnrollUser.getRetrofitObj().create(APIInterface.class);
        Call<UserErrorBean> userErrorBeanCall = apiInterface.unEnrollUser(headerResult);

        userErrorBeanCall.enqueue(new Callback<UserErrorBean>() {
            @Override
            public void onResponse(Call<UserErrorBean> call, Response<UserErrorBean> response) {

                Log.e("unEnrollUser:","Success");
            }

            @Override
            public void onFailure(Call<UserErrorBean> call, Throwable t) {
                Log.e("unEnrollUser:","Fail");
            }
        });

    }


    public void testUserId(){

        Map<String,Object> headerResult = getHeaderData();

        APIInterface apiInterface = APIEnrollUser.getRetrofitObj().create(APIInterface.class);
        Call<UserErrorBean> userErrorBeanCall = apiInterface.getUserId(headerResult);

        userErrorBeanCall.enqueue(new Callback<UserErrorBean>() {
            @Override
            public void onResponse(Call<UserErrorBean> call, Response<UserErrorBean> response) {

                Log.e("userId:","Success");
            }

            @Override
            public void onFailure(Call<UserErrorBean> call, Throwable t) {
                Log.e("userId:","Fail");
            }
        });

    }

}
