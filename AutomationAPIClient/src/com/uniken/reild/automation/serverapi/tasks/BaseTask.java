package com.uniken.reild.automation.serverapi.tasks;

import com.uniken.automation.beans.ExecutionBean;
import com.uniken.automation.beans.ExecutionResultBean;
import com.uniken.reild.automation.serverapi.responses.GenResponse;

public class BaseTask {
	
	protected static void setTestResult(GenResponse resp, ExecutionBean execution, ExecutionResultBean bean) {
		// TODO Auto-generated method stub
		if(resp==null)
			return;
		if((resp.getResponse_code()==execution.getResponse_code() && resp.getResponse_code()==0) || 
				  ( resp.getResponse_code()==1 && resp.getError_code()==execution.getError_code() && resp.getError_msg().equals(execution.getError_message())))
		{
			bean.setExecution_result("PASSED");
		}
		else
		{
			bean.setExecution_result("FAILED");
			System.out.println("Expected: ResponseCode:" + execution.getResponse_code() );
			System.out.println(", ErrorCode:" + execution.getError_code() );
			System.out.println(", ErrorMsg:" + execution.getError_message() );
			
		}
		
	}

}
