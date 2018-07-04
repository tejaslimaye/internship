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
import com.uniken.automation.beans.ServerBean;
import com.uniken.automation.model.ServerModel;
import com.uniken.automation.responses.Response;

/**
 * Servlet implementation class ServerController
 */
@WebServlet("/updateServerDetails.htm")
public class ServerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServerController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Response objResp2 = new Response();
		Gson gsonResponse = new Gson();
	

		try
		{
		
			StringBuffer buff = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			while(reader.ready())
			{
				buff.append(reader.readLine());
			}
			
			Gson gsonServer = new Gson();
			ServerBean bean = gsonServer.fromJson(buff.toString(), ServerBean.class);
			
			
			ServerModel model = new ServerModel();
			model.updateServerDetails(bean);
			
			objResp2.setResponse_code(0);

			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			objResp2.setError_code(1);
			objResp2.setResponse_code(1);
			System.out.println("MESSAGE: " +e.getLocalizedMessage());
			objResp2.setError_message(e.getLocalizedMessage());
		}
		
		PrintWriter out = response.getWriter();	
		out.write(gsonResponse.toJson(objResp2));
		
	}


	}


