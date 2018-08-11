package com.uniken.automation.beans;

public class ExecutionAnalysisBean {
	
		int execution_id, server_id,count;
		String feature_name,testcase_name,device_os,lib_version,execution_status,execution_end_time;
		
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public int getServer_id() {
			return server_id;
		}
		public int getExecution_id() {
			return execution_id;
		}
		public void setExecution_id(int execution_id) {
			this.execution_id = execution_id;
		}
		public int getServer() {
			return server_id;
		}
		public void setServer_id(int server_id) {
			this.server_id = server_id;
		}
		public String getFeature_name() {
			return feature_name;
		}
		public void setFeature_name(String feature_name) {
			this.feature_name = feature_name;
		}
		public String getTestcase_name() {
			return testcase_name;
		}
		public void setTestcase_name(String testcase_name) {
			this.testcase_name = testcase_name;
		}
		public String getDevice_os() {
			return device_os;
		}
		public void setDevice_os(String device_os) {
			this.device_os = device_os;
		}
		public String getLib_version() {
			return lib_version;
		}
		public void setLib_version(String lib_version) {
			this.lib_version = lib_version;
		}
		public String getExecution_status() {
			return execution_status;
		}
		public void setExecution_status(String execution_status) {
			this.execution_status = execution_status;
		}
		public String getExecution_end_time() {
			return execution_end_time;
		}
		public void setExecution_end_time(String execution_end_time) {
			this.execution_end_time = execution_end_time;
		}
		
	

}
