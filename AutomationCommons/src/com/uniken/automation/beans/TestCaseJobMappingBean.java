package com.uniken.automation.beans;

import java.util.ArrayList;

public class TestCaseJobMappingBean {
	private int testjob_id;
	ArrayList<Integer> test_case_id = new ArrayList<>();
	private int testcase_id ;
	
	
	public int getTestcase_id() {
		return testcase_id;
	}

	public void setTestcase_id(int testcase_id) {
		this.testcase_id = testcase_id;
	}

	public ArrayList<Integer> getTest_case_id() {
		return test_case_id;
	}
	
	public void setTest_case_id(ArrayList<Integer> test_case_id) {
		this.test_case_id = test_case_id;
	}
	public int getTestjob_id() {
		return testjob_id;
	}
	public void setTestjob_id(int testjob_id) {
		this.testjob_id = testjob_id;
	}
//	public int getTest_case_id() {
//		return test_case_id;
//	}
//	public void setTest_case_id(int test_case_id) {
//		this.test_case_id = test_case_id;
//	}
//	

}
