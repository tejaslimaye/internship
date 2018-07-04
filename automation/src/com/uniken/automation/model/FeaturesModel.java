package com.uniken.automation.model;

import com.uniken.automation.beans.FeatureBean;

import com.uniken.automation.responses.Response;

public class FeaturesModel extends BaseModel {
	public void getFeatureName(FeatureBean bean) throws Exception
	{
		
					execute("insert into features (feature_name,feature_target) values ('" + 
					bean.getFeature_name() + "','" + 
					bean.getFeature_target() +  "')" );
				
		}	
		
		
		
		
		
		
	}


