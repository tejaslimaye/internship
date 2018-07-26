package com.uniken.automation.responses;
import java.util.ArrayList;
import com.uniken.automation.beans.ExecutionResultBean;

public class ExecutionResponse extends Response {

	private ArrayList<ExecutionResultBean> execution_details;

	public ArrayList<ExecutionResultBean> getExecution_details() {
		return execution_details;
	}

	public void setExecution_details(ArrayList<ExecutionResultBean> execution_details) {
		this.execution_details = execution_details;
	}



}
