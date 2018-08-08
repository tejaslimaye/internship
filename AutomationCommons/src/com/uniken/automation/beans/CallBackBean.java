package com.uniken.automation.beans;

public class CallBackBean {
	
	int callback_id,response_code,error_code;
	String notification_uuid,status,create_time,verification_mode,error_message,user_id,msg_id,push_fallback_status,original_verification_mode;
	public int getCallback_id() {
		return callback_id;
	}
	public void setCallback_id(int callback_id) {
		this.callback_id = callback_id;
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
	public String getNotification_uuid() {
		return notification_uuid;
	}
	public void setNotification_uuid(String notification_uuid) {
		this.notification_uuid = notification_uuid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getVerification_mode() {
		return verification_mode;
	}
	public void setVerification_mode(String verification_mode) {
		this.verification_mode = verification_mode;
	}
	public String getError_message() {
		return error_message;
	}
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}
	public String getPush_fallback_status() {
		return push_fallback_status;
	}
	public void setPush_fallback_status(String push_fallback_status) {
		this.push_fallback_status = push_fallback_status;
	}
	public String getOriginal_verification_mode() {
		return original_verification_mode;
	}
	public void setOriginal_verification_mode(String original_verification_mode) {
		this.original_verification_mode = original_verification_mode;
	}
	

}
