package com.uniken.automation.model;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.uniken.automation.beans.ServerBean;
import com.uniken.automation.responses.ServerResponse;
import com.uniken.automation.responses.TestJobResponse;

public class ServerModel extends BaseModel{
	
	
	public ServerResponse getAllServersDetails() throws Exception
	{
		ServerResponse resp = new ServerResponse();
		
				ResultSet rs=executeQuery("select * from server");
				
				ArrayList<ServerBean> servers = new ArrayList<ServerBean>();
				 
				while(rs.next()){
					
					ServerBean bean = new ServerBean();
					bean.setServer_id(rs.getInt("server_id"));
					bean.setGm_port(rs.getInt("gm_port"));
					bean.setSdk_port(rs.getInt("sdk_port"));
					bean.setVerify_port(rs.getInt("verify_port"));
					bean.setApi_port(rs.getInt("api_port"));
					bean.setIp_address(rs.getString("ip_address"));
					bean.setOs_version(rs.getString("os_version"));
					bean.setConsole_user(rs.getString("console_user"));
					bean.setConsole_password(rs.getString("console_password"));
					bean.setEnterprise_id(rs.getString("enterprise_id"));
					bean.setEnterprise_user(rs.getString("enterprise_user"));
					bean.setEnterprise_password(rs.getString("enterprise_password"));
					bean.setServer_user(rs.getString("server_user"));
					bean.setServer_password(rs.getString("server_password"));
					bean.setAgent_info(rs.getString("agent_info"));
					servers.add(bean);
				
				}
				resp.setServer_details(servers);
				return resp;
				
				
	}

	
	
	public void addServer(ServerBean bean) throws Exception
	{
		
					execute("insert into server (ip_address,os_version,enterprise_password,agent_info) values ('" + 
				//	bean.getGm_port() + "," + 
				//  bean.getSdk_port() +  "," + 
				//	bean.getVerify_port()+ "," +
				//	bean.getApi_port()+ "," +
					bean.getIp_address()+"','"+
					bean.getOs_version()+"','"+
//					bean.getConsole_user()+"','"+
//					bean.getConsole_password()+"','"+
//					bean.getEnterprise_id()+"','"+
//					bean.getEnterprise_user()+"','"+
					bean.getEnterprise_password()+"','"+
//					bean.getServer_user()+"','"+
//					bean.getServer_password()+"','"+
					bean.getAgent_info()+ "')" );
				
		}	
	
}
