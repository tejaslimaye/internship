package com.uniken.automation.responses;

import java.util.ArrayList;

import com.uniken.automation.beans.TestCaseJobMappingBean;

public class TestCaseJobMappingResponse extends Response {

	private ArrayList<TestCaseJobMappingBean> TestCaseJobMapping_details;

	public ArrayList<TestCaseJobMappingBean> getTestCaseJobMapping_details() {
		return TestCaseJobMapping_details;
	}

	public void setTestCaseJobMapping_details(ArrayList<TestCaseJobMappingBean> TestCaseJobMapping_details) {
		this.TestCaseJobMapping_details = TestCaseJobMapping_details;
	}
	
}
