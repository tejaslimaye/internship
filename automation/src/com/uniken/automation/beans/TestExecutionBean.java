package com.uniken.automation.beans;

public class TestExecutionBean {
	
	private int execution_id;
	public int getExecution_id() {
		return execution_id;
	}

	public void setExecution_id(int execution_id) {
		this.execution_id = execution_id;
	}

	private int testrun_id;
	private int testcase_id;
	private String execution_status;

	public int getTestrun_id(){
		return testrun_id;
	}

	public void setTestrun_id(int testrun_id){
		this.testrun_id=testrun_id;
	}

	public int getTestcase_id(){
		return testcase_id;
	}

	public void setTestcase_id(int testcase_id){
		this.testcase_id=testcase_id;
	}

	public String getExecution_status(){
		return execution_status;
	}

	public void setExecution_status(String execution_status){
		this.execution_status=execution_status;
	}


}
