package com.uniken.automation.beans;

public class TestJobBean {
	
	private String test_job_description;
	private String status;
	private int server_id;
	private int lib_id;
	private int testjob_id;
	public int getTestjob_id() {
		return testjob_id;
	}

	public void setTestjob_id(int testjob_id) {
		this.testjob_id = testjob_id;
	}

	public String getTest_job_description(){
		return test_job_description;
	}

	public void setTest_job_description(String test_job_description){
		this.test_job_description=test_job_description;
	}

	public String getStatus(){
		return status;
	}

	public void setStatus(String status){
		this.status=status;
	}

	public int getServer_id(){
		return server_id;
	}

	public void setServer_id(int server_id){
		this.server_id=server_id;
	}

	public int getLib_id(){
		return lib_id;
	}

	public void setLib_id(int lib_id){
		this.lib_id=lib_id;
	}

}
