package com.uniken.automation.model;

import java.sql.ResultSet;
import java.util.ArrayList;


import com.uniken.automation.beans.TestCaseJobMappingBean;
import com.uniken.automation.responses.TestCaseJobMappingResponse;

public class TestCaseJobMappingModel extends BaseModel {
	
		
		public void getTCMJ_ID(TestCaseJobMappingBean bean) throws Exception
		{
			
						execute("insert into test_case_job_mapping (testjob_id,testcase_id) values ('" + 
								bean.getTestjob_id() + "','" + 
								bean.getTest_case_id() +  "')" );
					System.out.println(bean.getTest_case_id());
		}	

		public TestCaseJobMappingResponse getTCMJ_ID() throws Exception
		{
			TestCaseJobMappingResponse resp = new TestCaseJobMappingResponse();

			
			ArrayList<TestCaseJobMappingBean> list = new ArrayList<TestCaseJobMappingBean>();
			ResultSet rs = executeQuery("select * from test_case_job_mapping");
			while(rs.next())
			{
				TestCaseJobMappingBean bean = new TestCaseJobMappingBean();
			//	bean.setTest_case_id(rs.get("testcase_id"));
				bean.setTestjob_id(rs.getInt("testjob_id"));
				
				list.add(bean);
				
				
			}
			
			resp.setTestCaseJobMapping_details(list);
			return resp;
			
		}
		
}
