package com.uniken.automation.responses;

import java.util.ArrayList;
import com.uniken.automation.beans.FeatureBean;

public class FeatureResponse extends Response {

	private ArrayList<FeatureBean> feature_details;

	public ArrayList<FeatureBean> getFeature_details() {
		return feature_details;
	}

	public void setFeature_details(ArrayList<FeatureBean> feature_details) {
		this.feature_details = feature_details;
	}
	

}

