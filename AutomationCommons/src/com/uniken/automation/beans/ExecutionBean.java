package com.uniken.automation.beans;

public class ExecutionBean {
	
	private int execution_id;
	private int testrun_id ;
	private int testcase_id ;
	private int feature_id ;
	private String feature_name;
	private String testcase_name;
	private String  testcase_desc;
	private int device_id ;
	private int testjob_id;
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
	public int getExecution_id() {
		return execution_id;
	}
	public void setExecution_id(int execution_id) {
		this.execution_id = execution_id;
	}
	public int getTestrun_id() {
		return testrun_id;
	}
	public void setTestrun_id(int testrun_id) {
		this.testrun_id = testrun_id;
	}
	public int getTestcase_id() {
		return testcase_id;
	}
	public void setTestcase_id(int testcase_id) {
		this.testcase_id = testcase_id;
	}
	public int getFeature_id() {
		return feature_id;
	}
	public void setFeature_id(int feature_id) {
		this.feature_id = feature_id;
	}
	public String getFeature_name() {
		return feature_name;
	}
	public void setFeature_name(String feature_name) {
		this.feature_name = feature_name;
	}
	public String getTestcase_name() {
		return testcase_name;
	}
	public void setTestcase_name(String testcase_name) {
		this.testcase_name = testcase_name;
	}
	public String getTestcase_desc() {
		return testcase_desc;
	}
	public void setTestcase_desc(String testcase_desc) {
		this.testcase_desc = testcase_desc;
	}
	public int getDevice_id() {
		return device_id;
	}
	public void setDevice_id(int device_id) {
		this.device_id = device_id;
	}
	public int getTestjob_id() {
		return testjob_id;
	}
	public void setTestjob_id(int testjob_id) {
		this.testjob_id = testjob_id;
	}
	

}
