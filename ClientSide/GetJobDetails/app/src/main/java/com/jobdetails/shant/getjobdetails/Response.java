package com.jobdetails.shant.getjobdetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created for Json parsing got from server as response containing test case details .
 */

public class Response {

    private int response_code;
    private int error_code;
    private String error_message;
    public int getResponse_code() {
        return response_code;
    }
    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }
    public int getError_code() {
        return error_code;
    }
    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
    public String getError_message() {
        return error_message;
    }
    public void setError_message(String error_message) {
        this.error_message = error_message;
    }


    private int device_avail_code;
    private int job_avail_code;
    private ArrayList<ServerBean> server_execution_details;

    public ArrayList<ServerBean> getServer_execution_details() {
        return server_execution_details;
    }
    public void setServer_execution_details(ArrayList<ServerBean> server_execution_details) {
        this.server_execution_details = server_execution_details;
    }
    public void setDevice_avail_code(int device_avail_code) {
        this.device_avail_code = device_avail_code;
    }
    public int getDevice_avail_code() {
        return device_avail_code;
    }
    public int getJob_avail_code() {
        return job_avail_code;
    }
    public void setJob_avail_code(int job_avail_code) {
        this.job_avail_code = job_avail_code;
    }


}
