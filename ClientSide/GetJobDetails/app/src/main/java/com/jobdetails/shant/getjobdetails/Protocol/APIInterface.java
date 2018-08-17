package com.jobdetails.shant.getjobdetails.Protocol;

import com.jobdetails.shant.getjobdetails.Beans.JobDetailBean;
import com.jobdetails.shant.getjobdetails.Beans.Mob_Details;
import com.jobdetails.shant.getjobdetails.Beans.TestCaseResult;
import com.jobdetails.shant.getjobdetails.Beans.UpdateResultResponse;
import com.jobdetails.shant.getjobdetails.Beans.UserErrorBean;
import com.jobdetails.shant.getjobdetails.Beans.UserProfileBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface APIInterface {

    @POST("/automation/getJobDetails.htm")
    Call<JobDetailBean> getTestCasesJob(@Body Mob_Details mob_details);

//    @GET("/bins/18enq0")
    @GET("/bins/1edbf8")
    Call<JobDetailBean> getTestCasesDummyJob();

    @POST("/automation/updateTestResults.htm")
    Call<UpdateResultResponse> updateTestResult(@Body TestCaseResult testCaseResult);

    @POST("/rest/enrollUser.htm")
    Call<UserErrorBean> enrollUser(@HeaderMap Map<String, Object> headers);

    @POST("/rest/enrollUserDevice.htm")
    Call<UserErrorBean> enrollUserDevice(@HeaderMap Map<String, Object> headers);

    @POST("/rest/getUserStatus.htm")
    Call<UserErrorBean> getUserStatus(@HeaderMap Map<String, Object> headers);

    @POST("/rest/unenrollUser.htm")
    Call<UserErrorBean> unEnrollUser(@HeaderMap Map<String, Object> headers);

    @POST("/rest/getUserId.htm")
    Call<UserErrorBean> getUserId(@HeaderMap Map<String, Object> headers);
}
