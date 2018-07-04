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
import com.uniken.automation.responses.Response;



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
		try{
		
              ServerModel model = new ServerModel();
		      ArrayList<ServerBean> serves = model.getAllServersDetails();
		      Gson gson = new Gson();
		      response.getWriter().print(gson.toJson(serves));
		      	
    	}
		catch(Exception e){
			e.printStackTrace();
		}
		}

}
