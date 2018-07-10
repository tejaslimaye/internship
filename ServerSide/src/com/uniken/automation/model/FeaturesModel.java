package com.uniken.automation.model;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.uniken.automation.beans.FeatureBean;
import com.uniken.automation.responses.FeatureResponse;
import com.uniken.automation.responses.Response;

public class FeaturesModel extends BaseModel {
	public void getFeatureName(FeatureBean bean) throws Exception
	{
		
					execute("insert into features (feature_name,feature_target) values ('" + 
					bean.getFeature_name() + "','" + 
					bean.getFeature_target() +  "')" );
				
	}	
		
	
	public FeatureResponse getFeatures() throws Exception
	{
		FeatureResponse resp = new FeatureResponse();

		
		ArrayList<FeatureBean> list = new ArrayList<FeatureBean>();
		ResultSet rs = executeQuery("select * from features");
		while(rs.next())
		{
			FeatureBean bean = new FeatureBean();
			bean.setFeature_id(rs.getInt("feature_id"));
			bean.setFeature_name(rs.getString("feature_name"));
			bean.setFeature_target(rs.getString("feature_target"));
			list.add(bean);
			
			
		}
		
		resp.setFeature_details(list);
		return resp;
		
	}
	
		
		
		
		
	}


