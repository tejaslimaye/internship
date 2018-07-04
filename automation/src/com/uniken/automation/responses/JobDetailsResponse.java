package com.uniken.automation.responses;

import java.util.ArrayList;

import com.uniken.automation.beans.ExecutionBean;

public class JobDetailsResponse extends Response {

	private int device_avail_code;
	private int job_avail_code;
	private ArrayList<ExecutionBean> test_execution_details;
	
	public ArrayList<ExecutionBean> getTest_execution_details() {
		return test_execution_details;
	}
	public void setTest_execution_details(ArrayList<ExecutionBean> test_execution_details) {
		this.test_execution_details = test_execution_details;
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
