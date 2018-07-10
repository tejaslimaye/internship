package com.uniken.automation.responses;

import java.util.ArrayList;
import com.uniken.automation.beans.TestCaseBean;

public class TestCaseResponse extends Response {

	private ArrayList<TestCaseBean> TestCase_details;

	public ArrayList<TestCaseBean> getTestCase_details() {
		return TestCase_details;
	}

	public void setTestCase_details(ArrayList<TestCaseBean> TestCase_details) {
		this.TestCase_details = TestCase_details;
	}
	

}
