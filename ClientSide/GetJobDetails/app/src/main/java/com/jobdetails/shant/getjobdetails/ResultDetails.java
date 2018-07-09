package com.jobdetails.shant.getjobdetails;

public class ResultDetails {

    /**
     Created for declaring Test Cases Results after completion of test cases to be sent as request to server as json
     */

    private String execution_id;
    private String execution_result;
    private String start_time;
    private String end_time;

    public ResultDetails(String execution_id, String execution_result, String start_time, String end_time) {
        this.execution_id = execution_id;
        this.execution_result = execution_result;
        this.start_time = start_time;
        this.end_time = end_time;
    }


}
