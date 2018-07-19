package com.uniken.automation.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;


import com.google.gson.Gson;
import com.uniken.automation.beans.ServerBean;
import com.uniken.automation.model.ServerModel;
import com.uniken.automation.model.TestJobModel;
import com.uniken.automation.responses.Response;
import com.uniken.automation.responses.ServerResponse;



/**
 * Servlet implementation class getServer
 */
@WebServlet("/getServer.htm")
public class ServerDetailsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServerDetailsController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServerResponse resp  = new ServerResponse();
		try{
			resp= new ServerModel().getAllServersDetails();
			resp.setResponse_code(0);
		      resp.setError_code(0);
		      	
    	}
		catch(Exception e){
			resp.setResponse_code(1);
			resp.setError_code(3);
			resp.setError_message(e.getMessage());
			e.printStackTrace();
		}
		
	response.getWriter().write(new Gson().toJson(resp.getServer_details()));
		//	Gson gson = new Gson();
	    //  response.getWriter().print(gson.toJson(resp));
	      
		}

}
