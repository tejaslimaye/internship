package com.uniken.automation.model;
import java.sql.ResultSet;
import java.util.ArrayList;


import com.uniken.automation.beans.TestCaseBean;

import com.uniken.automation.responses.TestCaseResponse;


public class TestCaseModel extends BaseModel {
	public void addTestCase(TestCaseBean bean) throws Exception
	{
			//String feat_id = "select feature_id from features where feature_name ="+ bean.getFeature_name();
		
		//execute("select feature_id from features where feature_name='" + bean.getFeature_name()+ "'");
					execute("insert into test_case (testcase_name,test_feature_id,testcase_desc) values ('" + 
					bean.getTestcase_name() + "'," +
					//bean.getUpdate_time()+ "','" +
					"(" +"select feature_id from features where feature_name='" + bean.getFeature_name() + "'),'" +
				// "("+"select feature_id from features where feature_name=" + bean.getFeature_name()+ ")"+ "','" +
				//	bean.getTest_feature_id()+ "','" +
					
					bean.getTestcase_desc()+"')" );
				
		}	
		
	
	public TestCaseResponse getTestCases() throws Exception
	{
		TestCaseResponse resp = new TestCaseResponse();

		
		ArrayList<TestCaseBean> list = new ArrayList<TestCaseBean>();
		ResultSet rs = executeQuery("select tc.*,f.feature_name from test_case tc,features f where tc.test_feature_id = f.feature_id");
		while(rs.next())
		{
			TestCaseBean bean = new TestCaseBean();
			bean.setTest_case_id(rs.getInt("testcase_id"));
			bean.setTestcase_name(rs.getString("testcase_name"));
			bean.setCreated_time(rs.getString("created_time"));
			bean.setUpdate_time(rs.getString("update_time"));
			bean.setTest_feature_id(rs.getInt("test_feature_id"));
			bean.setTestcase_desc(rs.getString("testcase_desc"));
			bean.setFeature_name(rs.getString("feature_name"));
			list.add(bean);
			
			
		}
		
		resp.setTestCase_details(list);
		return resp;
		
	}
		
}
		
		
		