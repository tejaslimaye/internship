package com.uniken.automation.beans;

public class TestCaseBean {
	
	private String testcase_name;
	private int test_feature_id;
	private int test_case_id;
	
	public int getTest_case_id() {
		return test_case_id;
	}

	public void setTest_case_id(int test_case_id) {
		this.test_case_id = test_case_id;
	}

	public String getTestcase_name(){
		return testcase_name;
	}

	public void setTestcase_name(String testcase_name){
		this.testcase_name=testcase_name;
	}

	public int getTest_feature_id(){
		return test_feature_id;
	}

	public void setTest_feature_id(int test_feature_id){
		this.test_feature_id=test_feature_id;
	}

}
