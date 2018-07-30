package com.uniken.automation.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.uniken.automation.beans.TestCaseJobMappingBean;
import com.uniken.automation.model.TestCaseJobMappingModel;
import com.uniken.automation.responses.Response;

/**
 * Servlet implementation class TCMJController
 */
@WebServlet("/addTCMJ.htm")
public class TCMJController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TCMJController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stubResponse objResp = new Response();
		Gson gsonResponse = new Gson();
	
		Response objResp = new Response();
		try
		{
		
			StringBuffer buff = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			while(reader.ready())
			{
				buff.append(reader.readLine());
			}
			
			System.out.println("data: " + buff.toString());
			
			Gson gsonTcmj = new Gson();
			TestCaseJobMappingBean bean = gsonTcmj.fromJson(buff.toString(), TestCaseJobMappingBean.class);
			
			
			TestCaseJobMappingModel model = new TestCaseJobMappingModel();
			model.getTCMJ_ID(bean);
			
			objResp.setResponse_code(0);

			
		}
			
			catch(Exception e)
			{
				e.printStackTrace();
				objResp.setError_code(1);
				objResp.setResponse_code(1);
				objResp.setError_message("NULL");
				System.out.println("MESSAGE: " +e.getLocalizedMessage());
				//objResp.setError_message(e.getLocalizedMessage());
			}
			
			PrintWriter out = response.getWriter();	
			System.out.println(gsonResponse.toJson(objResp));
			out.write(gsonResponse.toJson(objResp));
		
		}
	


}
