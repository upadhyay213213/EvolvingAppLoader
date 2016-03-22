package com.evolving.apploader.android.sdk.datausage;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AppData {
	
	private int appID;	
	private String appName;
	private Map<String, Long> totalData = new HashMap<String, Long> ();
	private TData foreGrounddata;
	private TData backGrounddata;
	
	
	public int getAppID() {
		return appID;
	}
	public void setAppID(int uid) {
		this.appID = uid;
	}
	
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public TData getForeGrounddata() {
		return foreGrounddata;
	}
	public void setForeGrounddata(TData foreGrounddata) {
		this.foreGrounddata = foreGrounddata;
	}
	public TData getBackGrounddata() {
		return backGrounddata;
	}
	public void setBackGrounddata(TData backGrounddata) {
		this.backGrounddata = backGrounddata;
	}
	public Map<String, Long> getTotalData() {
		return totalData;
	}
	
	public long getDataConsumption (String s) {
		long mobile=0, lan=0;
		
		for (Entry<String, Long> entry: totalData.entrySet()) {
			System.out.println("Key:"+entry.getKey());
			if (entry.getKey().contains("MOB")) {
			mobile+= entry.getValue();
			} else 
				lan+=entry.getValue();
			
		}
		if ("mobile".equalsIgnoreCase(s)) {
			return mobile;
			
		} else {
			return lan;
		}
		
	}
		

}
