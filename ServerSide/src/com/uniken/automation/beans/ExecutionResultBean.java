package com.uniken.automation.beans;

public class ExecutionResultBean {
	
	private int execution_id;
	private String execution_result,start_time, end_time;
	public int getExecution_id() {
		return execution_id;
	}
	public void setExecution_id(int execution_id) {
		this.execution_id = execution_id;
	}
	public String getExecution_result() {
		return execution_result;
	}
	public void setExecution_result(String execution_result) {
		this.execution_result = execution_result;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
		

}
