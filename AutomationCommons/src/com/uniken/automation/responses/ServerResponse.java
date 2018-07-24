package com.uniken.automation.responses;

import java.util.ArrayList;

import com.uniken.automation.beans.ExecutionBean;
import com.uniken.automation.beans.ServerBean;

public class ServerResponse extends Response {

	private ArrayList<ServerBean> server_details;

	public ArrayList<ServerBean> getServer_details() {
		return server_details;
	}

	public void setServer_details(ArrayList<ServerBean> server_details) {
		this.server_details = server_details;
	}
	

}

