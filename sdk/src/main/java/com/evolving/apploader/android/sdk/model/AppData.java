package com.evolving.apploader.android.sdk.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AppData {
	
	private int appID;	
	private String appName;
	private String packageName;
	private long mobileData;
	private long cellularData;
	private Map<String, Long> totalData = new HashMap<>();

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


	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public long getCellularData() {
		return cellularData;
	}

	public void setCellularData(long cellularData) {
		this.cellularData = cellularData;
	}

	public long getMobileData() {
		return mobileData;
	}

	public void setMobileData(long mobileData) {
		this.mobileData = mobileData;
	}
}
