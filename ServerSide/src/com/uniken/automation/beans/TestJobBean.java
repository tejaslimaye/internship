package com.uniken.automation.beans;


public class TestJobBean {
	
	private int testjob_id;
	private String test_job_description;
	private String created_time; 
	private String updated_time; 
	private String status;
	private int server_id;
	private int lib_id;
	private int auto_create_on_new_device;
	
	public int getTestjob_id() {
		return testjob_id;
	}
	public void setTestjob_id(int testjob_id) {
		this.testjob_id = testjob_id;
	}
	
	public String getTest_job_description() {
		return test_job_description;
	}
	public void setTest_job_description(String test_job_description) {
		this.test_job_description = test_job_description;
	}
	
	public String getCreated_time() {
		return created_time;
	}
	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}
	
	public String getUpdated_time() {
		return updated_time;
	}
	public void setUpdated_time(String updated_time) {
		this.updated_time = updated_time;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getServer_id() {
		return server_id;
	}
	public void setServer_id(int server_id) {
		this.server_id = server_id;
	}
	public int getLib_id() {
		return lib_id;
	}
	public void setLib_id(int lib_id) {
		this.lib_id = lib_id;
	}

	public int getAuto_create_on_new_device() {
		return auto_create_on_new_device;
	}
	public void setAuto_create_on_new_device(int auto_create_on_new_device) {
		this.auto_create_on_new_device = auto_create_on_new_device;
	}


	

	


	

}
