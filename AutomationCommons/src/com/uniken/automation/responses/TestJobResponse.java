package com.uniken.automation.responses;

import java.util.ArrayList;

import com.uniken.automation.beans.TestJobBean;

public class TestJobResponse extends Response{
	
	private ArrayList<TestJobBean> joblist = null;

	public ArrayList<TestJobBean> getList() {
		return joblist;
	}

	public void setList(ArrayList<TestJobBean> list) {
		this.joblist = list;
	}

}
