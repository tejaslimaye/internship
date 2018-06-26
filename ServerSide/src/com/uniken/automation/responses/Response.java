package com.uniken.automation.responses;

public abstract class Response {
	
	private int response_code;
	private int error_code;
	private String error_message;
	public int getResponse_code() {
		return response_code;
	}
	public void setResponse_code(int response_code) {
		this.response_code = response_code;
	}
	public int getError_code() {
		return error_code;
	}
	public void setError_code(int error_code) {
		this.error_code = error_code;
	}
	public String getError_message() {
		return error_message;
	}
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	

}
