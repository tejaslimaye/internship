package com.uniken.automation.responses;

import java.util.ArrayList;

import com.uniken.automation.beans.TestExecutionSummaryBean;

public class TestExecutionSummaryResponse extends Response {
	
	private ArrayList<TestExecutionSummaryBean> execSummaryDetails;

	private int total_Count = 0;
	
	
	public int getTotal_Count() {
		return total_Count;
	}

	public void setTotal_Count(int total_Count) {
		this.total_Count = total_Count;
	}

	public ArrayList<TestExecutionSummaryBean> getExecSummaryDetails() {
		return execSummaryDetails;
	}

	public void setExecSummaryDetails(ArrayList<TestExecutionSummaryBean> execSummaryDetails) {
		this.execSummaryDetails = execSummaryDetails;
	}
	

}
