package com.jobdetails.shant.getjobdetails.Beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestCaseResult {

    @SerializedName("execution_id")
    @Expose
    private int executionID;
    @SerializedName("test_case_id")
    @Expose
    private int testCaseID;
    @SerializedName("execution_result")
    @Expose
    private String executionResult;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("params_used")
    @Expose
    private String paramUsed;

    public String getParamUsed() {
        return paramUsed;
    }

    public void setParamUsed(String paramUsed) {
        this.paramUsed = paramUsed;
    }

    public String getResultData() {
        return resultData;
    }

    public void setResultData(String resultData) {
        this.resultData = resultData;
    }

    @SerializedName("result_data")
    @Expose
    private String resultData;

    /*public TestCaseResult(int execution_id, int testCaseID, String executionResult, String startTime, String endTime) {
        this.executionID = execution_id;
        this.testCaseID = testCaseID
        this.executionResult = executionResult;
        this.startTime = startTime;
        this.endTime = endTime;
    }
*/

    public int getExecutionID() {
        return executionID;
    }

    public void setExecutionID(int executionID) {
        this.executionID = executionID;
    }

    public int getTestCaseID() {
        return testCaseID;
    }

    public void setTestCaseID(int testCaseID) {
        this.testCaseID = testCaseID;
    }

    public String getExecutionResult() {
        return executionResult;
    }

    public void setExecutionResult(String executionResult) {
        this.executionResult = executionResult;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
