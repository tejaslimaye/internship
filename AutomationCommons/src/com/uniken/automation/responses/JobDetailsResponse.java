package com.uniken.automation.responses;

import java.util.ArrayList;

import com.uniken.automation.beans.ExecutionBean;
import com.uniken.automation.beans.ServerBean;

public class JobDetailsResponse extends Response {

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
