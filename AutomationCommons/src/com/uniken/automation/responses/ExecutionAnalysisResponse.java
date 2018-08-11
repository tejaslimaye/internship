package com.uniken.automation.responses;

import java.util.ArrayList;

import com.uniken.automation.beans.ExecutionAnalysisBean;

public class ExecutionAnalysisResponse extends Response {

	ArrayList<ExecutionAnalysisBean> executions;

	public ArrayList<ExecutionAnalysisBean> getExecutions() {
		return executions;
	}

	public void setExecutions(ArrayList<ExecutionAnalysisBean> executions) {
		this.executions = executions;
	}
		

}
