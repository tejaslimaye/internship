package com.uniken.automation.model;

import com.uniken.automation.beans.ExecutionBean;
import com.uniken.automation.beans.ExecutionResultBean;
import com.uniken.automation.responses.Response;

public class ExecutionModel extends BaseModel {
	
	public void updateTestExecution(ExecutionResultBean bean) throws Exception
	{
		
		execute("update test_execution set execution_status = '" + bean.getExecution_result() + "', execution_start_time = '" + 
				bean.getStart_time() + "', execution_end_time = '" + bean.getEnd_time() +"' where execution_id = " + bean.getExecution_id());
		
		
	}

}
