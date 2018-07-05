package com.uniken.automation.model;
import com.uniken.automation.beans.TestCaseBean;


public class TestCaseModel extends BaseModel {
	public void addTestCase(TestCaseBean bean) throws Exception
	{
		
					execute("insert into test_case (testcase_name,update_time,test_feature_id,testcase_desc) values ('" + 
					bean.getTestcase_name() + "','" + 
					bean.getUpdate_time()+ "','" +
					bean.getTest_feature_id()+ "','" +
					bean.getTestcase_desc()+"')" );
				
		}	
		
		
}
		
		
		