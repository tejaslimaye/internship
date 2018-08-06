package com.uniken.automation.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.uniken.automation.beans.TestCaseBean;
import com.uniken.automation.beans.TestCaseJobMappingBean;
import com.uniken.automation.responses.TestCaseJobMappingResponse;

public class TestCaseJobMappingModel extends BaseModel {
	
		ArrayList<Integer> tempBean = new ArrayList<>();
		
		public void getTCMJ_ID(TestCaseJobMappingBean bean) throws Exception
		{
			int testCase ;
			tempBean = bean.getTest_case_id();
			for(int i=0; i<tempBean.size(); i++) {
		
				        testCase = tempBean.get(i).intValue();
				        System.out.println(testCase);
						execute("insert into test_case_job_mapping (testjob_id,testcase_id) values ('" + 
								bean.getTestjob_id() + "','" + 
								testCase +  "')" );
			}
		}	

		public TestCaseJobMappingResponse getTCMJDetails() throws Exception
		{
			TestCaseJobMappingResponse resp = new TestCaseJobMappingResponse();
			
			ArrayList<TestCaseJobMappingBean> list = new ArrayList<TestCaseJobMappingBean>();
			ResultSet rs = executeQuery("select * from test_case_job_mapping");
//			List<Integer> tempList = new ArrayList<>();
//			int tempTestCase;
			
			while(rs.next())
			{
//				tempTestCase = rs.getInt("testcase_id");
//				Integer tempInt = new Integer(tempTestCase);
//				tempList.add(tempInt);
				TestCaseJobMappingBean bean = new TestCaseJobMappingBean();
				bean.setTestcase_id(rs.getInt("testcase_id"));
				bean.setTestjob_id(rs.getInt("testjob_id"));
				
				list.add(bean);
				
				
			}
			
			resp.setTestCaseJobMapping_details(list);
			return resp;
			
		}
		
}
