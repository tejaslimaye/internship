package com.uniken.automation.model;
import com.uniken.automation.beans.UpdateTestCaseBean;
import com.uniken.automation.beans.TestCaseBean;

import com.uniken.automation.responses.Response;

public class TestCaseModel extends BaseModel {
	public void getTestCaseName(UpdateTestCaseBean bean) throws Exception
	{
		
					execute("insert into test_case (testcase_name,created_time,update_time,test_feature_id,testcase_desc) values ('" + 
					bean.getTestcase_name() + "','" + 
					bean.getCreated_time() +  "','" + 
					bean.getUpdate_time()+ "','" +
					bean.getTest_feature_id()+ "','" +
					bean.getTestcase_desc()+"')" );
				
		}	
		
		
}
		
		
		