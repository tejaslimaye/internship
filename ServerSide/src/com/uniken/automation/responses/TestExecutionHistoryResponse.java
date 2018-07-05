package com.uniken.automation.responses;

import java.util.ArrayList;

import com.uniken.automation.beans.TestExecutionSummaryBean;

public class TestExecutionHistoryResponse extends Response {
	
	private ArrayList<TestExecutionSummaryBean> failedList, passedList,unableToTestList;
	public ArrayList<TestExecutionSummaryBean> getFailedList() {
		return failedList;
	}
	public void setFailedList(ArrayList<TestExecutionSummaryBean> failedList) {
		this.failedList = failedList;
	}
	public ArrayList<TestExecutionSummaryBean> getPassedList() {
		return passedList;
	}
	public void setPassedList(ArrayList<TestExecutionSummaryBean> passedList) {
		this.passedList = passedList;
	}
	public ArrayList<TestExecutionSummaryBean> getUnableToTestList() {
		return unableToTestList;
	}
	public void setUnableToTestList(ArrayList<TestExecutionSummaryBean> unableToTestList) {
		this.unableToTestList = unableToTestList;
		
	}
	

}
