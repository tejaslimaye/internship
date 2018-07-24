package com.uniken.reild.automation.serverapi.responses;


public class GenResponse {

	String notification_uuid,verification_mode,user_status,msg_id,error_msg;
	int response_code,error_code;

	public String getNotification_uuid() {
		return notification_uuid;
	}

	public void setNotification_uuid(String notification_uuid) {
		this.notification_uuid = notification_uuid;
	}

	public String getVerification_mode() {
		return verification_mode;
	}

	public void setVerification_mode(String verification_mode) {
		this.verification_mode = verification_mode;
	}

	public String getUser_status() {
		return user_status;
	}

	public void setUser_status(String user_status) {
		this.user_status = user_status;
	}

	public String getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}


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

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}


	
}
