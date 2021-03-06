package com.uniken.automation.beans;

public class TestCaseBean {
	
	private int test_case_id;
	private String testcase_name,created_time,update_time,testcase_desc,feature_name;
	private int error_code,response_code;
	public int getError_code() {
		return error_code;
	}
	public void setError_code(int error_code) {
		this.error_code = error_code;
	}
	public int getResponse_code() {
		return response_code;
	}
	public void setResponse_code(int response_code) {
		this.response_code = response_code;
	}
	public String getError_message() {
		return error_message;
	}
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	private String error_message;
	
	public String getFeature_name() {
		return feature_name;
	}
	public void setFeature_name(String feature_name) {
		this.feature_name = feature_name;
	}
	private int test_feature_id;
	public int getTest_case_id() {
		return test_case_id;
	}
	public void setTest_case_id(int test_case_id) {
		this.test_case_id = test_case_id;
	}
	public String getTestcase_name() {
		return testcase_name;
	}
	public void setTestcase_name(String testcase_name) {
		this.testcase_name = testcase_name;
	}
	public String getCreated_time() {
		return created_time;
	}
	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getTestcase_desc() {
		return testcase_desc;
	}
	public void setTestcase_desc(String testcase_desc) {
		this.testcase_desc = testcase_desc;
	}
	public int getTest_feature_id() {
		return test_feature_id;
	}
	public void setTest_feature_id(int test_feature_id) {
		this.test_feature_id = test_feature_id;
	}
	

	

}
