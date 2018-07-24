package com.uniken.automation.beans;

public class TestRunBean {
	
	private String status;
	private int test_job_id;
	private int test_run_id;
	
	public int getTest_run_id() {
		return test_run_id;
	}

	public void setTest_run_id(int test_run_id) {
		this.test_run_id = test_run_id;
	}

	private int device_id;

	public String getStatus(){
		return status;
	}

	public void setStatus(String status){
		this.status=status;
	}

	public int getTest_job_id(){
		return test_job_id;
	}

	public void setTest_job_id(int test_job_id){
		this.test_job_id=test_job_id;
	}

	public int getDevice_id(){
		return device_id;
	}

	public void setDevice_id(int device_id){
		this.device_id=device_id;
	}

}
