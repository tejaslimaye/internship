package com.uniken.automation.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.uniken.automation.model.ExecutionModel;
import com.uniken.automation.responses.ExecutionAnalysisResponse;

/**
 * Servlet implementation class ExecutionAnalysisServlet
 */
@WebServlet("/getExecutionAnalysis.htm")
public class ExecutionAnalysisServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExecutionAnalysisServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	//	response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	//	doGet(request, response);
	
		ExecutionAnalysisResponse resp = new ExecutionAnalysisResponse();
		try {
			
			resp = new ExecutionModel().getExecutionDetails();
			resp.setResponse_code(0);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			resp.setResponse_code(1);
			resp.setError_code(7);
			resp.setError_message(e.getMessage());

		}
		response.getWriter().print(new Gson().toJson(resp));
	
	}

}
