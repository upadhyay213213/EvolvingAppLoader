package com.evolving.apploader.android.sdk.datausage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.evolving.apploader.android.sdk.R;

public class MainActivity extends Activity {

	TextView mTextView;
	TableLayout table;
	private static Context mContext; 
	private Map<Integer, AppData> appDataList;
	TableLayout.LayoutParams tableParams = 
			new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 
					TableLayout.LayoutParams.MATCH_PARENT);
	TableLayout.LayoutParams rowParams = 
			new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
					TableLayout.LayoutParams.WRAP_CONTENT, 1.0f);
	TableRow.LayoutParams itemParams = 
			new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);


	public enum appInterfaceTypes {
		WIFIBG, WIFIFG, MOBBG, MOBFG
	}

	public enum mobileInterfaceTypes {
		rmnet,pdp,ppp,uwbr,wimax,vsnet,ccmni,usb,lo,p2p0, tun0,rmnet0
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		appDataList = new HashMap<Integer, AppData>();
		mContext = getApplicationContext();
		//appDataConsumptionList = new HashMap<Integer, AppData>();
		StringBuilder s = new StringBuilder();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		prepareAppDataList();
		processStatsFile();
		System.out.println("after file processing");

		table = (TableLayout) findViewById(R.id.tl);
		TableRow row = new TableRow(mContext);
		row.setLayoutParams(rowParams);
		row.addView(getBoldTextView("Application Name "));
		row.addView(getBoldTextView("Mobile Data "));
		row.addView(getBoldTextView("Wifi Data "));
		row.setBackgroundColor(Color.LTGRAY);
		table.addView(row);
		for (Entry<Integer, AppData> entry : appDataList.entrySet()) {
			System.out.println("after file processing"+ entry.getValue().getAppName());
			if (entry.getValue() != null && ! entry.getValue().getTotalData().isEmpty()) {
				TableRow rowtemp = new TableRow(mContext);
				rowtemp.setLayoutParams(rowParams);
				rowtemp.addView(getTextView(entry.getValue().getAppName()));
				rowtemp.addView(getTextView(entry.getValue().getDataConsumption("mobile")));
				rowtemp.addView(getTextView(entry.getValue().getDataConsumption("WIFI")));
				table.addView(rowtemp);
				System.out.println("Application Name:" +entry.getValue().getAppName() + " mobileData:"+ entry.getValue().getDataConsumption("mobile") + " WIFIData:"+ entry.getValue().getDataConsumption("WIFI"));
				s.append("Application Name:" +entry.getValue().getAppName() + " mobile interface Data:"+ entry.getValue().getDataConsumption("mobile") + " WIFI interface Data:"+ entry.getValue().getDataConsumption("WIFI"));
			}

		}


	}

	private View getTextView(long dataConsumption) {
		double kb = 0;

		double mb = 0;
		if (dataConsumption >1024)
			kb = dataConsumption/1024;
		if (kb > 1024)
			mb=kb/1024;
		TextView t = new TextView(this);
		t.setLayoutParams(itemParams);
		t.setGravity(Gravity.CENTER | Gravity.BOTTOM);
		if (mb > 1) {
			t.setText(Double.toString(new BigDecimal(mb ).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue())+"MB");
			return t; 
		}
		else if (kb > 1) {
			t.setText(Double.toString(new BigDecimal(kb ).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue())+"KB");
			return t;
		}
		else 
			t.setText(Long.toString(dataConsumption)+"Bytes");
		return t;
	}

	String processStatsFile() {
		StringBuilder s = new StringBuilder();
		String line;
		File netFile = new File("/proc/net/xt_qtaguid/stats");
		BufferedReader buffer = null;
		int i=0;

		try {
			buffer = new BufferedReader(new FileReader(netFile));
			while ( ( line = buffer.readLine()) != null) {
				System.out.println(line+i);

				if (line.startsWith("idx"))
					continue;
				s.append(line+"\n");
				String items [] = line.split(" ");
				if (items.length >= 20)
					processRecord(items);				

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				buffer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return s.toString();
	}

	private void processRecord(String[] items) {
		AppData a = null;
		String key = getKey(items);

		System.out.println("items:  "+items[3]);
		if (Integer.parseInt(items[3]) > 0) {
			a = appDataList.get(Integer.parseInt(items[3]));
			System.out.println(Integer.parseInt(items[3]));
		}

		if (a != null) {
			System.out.println(a.getAppName());
			System.out.println(Long.parseLong( items[4]));
			if (Long.parseLong( items[4]) == 0) {
				TData backGrData = new TData();
				System.out.println("BG data:"+a.getAppName()+"  data:"+ Long.parseLong( items[5]) +  Long.parseLong( items[7]));
				backGrData.setTotalData(Long.parseLong( items[5]) +  Long.parseLong( items[7]));
				if (a.getTotalData() != null) {					
					if (key != null && key.equals("wifi"))
						a.getTotalData().put(appInterfaceTypes.WIFIBG.toString(), backGrData.getTotalData());
					else if (key != null && key.equals("mobile")) {
						a.getTotalData().put(appInterfaceTypes.MOBBG.toString(), backGrData.getTotalData());
					}
				}

			} else {
				TData fGrData = new TData();
				fGrData.setRecBytes(items[5]);
				fGrData.setSentBytes(items[7]);
				System.out.println("FG data:"+a.getAppName()+"  data:"+ Long.parseLong( items[5]) +  Long.parseLong( items[7]));
				fGrData.setTotalData(Long.parseLong( items[5]) +  Long.parseLong( items[7]));
				if (a.getTotalData() != null) {
					if (key != null && key.equals("wifi"))
						a.getTotalData().put(appInterfaceTypes.WIFIFG.toString(), fGrData.getTotalData());
					else if (key != null && key.equals("mobile")) {
						a.getTotalData().put(appInterfaceTypes.MOBFG.toString(), fGrData.getTotalData());
					}
				}
			}
			appDataList.put(Integer.parseInt(items[3]), a);			

		} 

	}

	private String getKey(String[] items) {
		String key = null;
		if (items[1].contains("wlan")) {
			key = "wifi";
		} else {
			switch (items[1]) {
			case "ccmni":
			case "pdp":
			case "ppp":
			case "rmnet":
			case "usb":
			case "uwbr":
			case "vsnet":
			case "wimax":
			case "rmnet0":
				key = "mobile";
				break;
			case "lo":
			case "p2p0":
			case "tun0":
				break;

			default:
				System.out.println("invalid interface type");
				break;
			}
		}
		return key;
	}

	void prepareAppDataList () {
		mContext = getApplicationContext();
		PackageManager pm = mContext.getPackageManager();
		List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
		for (ApplicationInfo a: packages) {
			AppData ad = new AppData();
			ad.setAppName(pm.getApplicationLabel(a).toString());
			ad.setAppID(a.uid);
			if (a.uid >= 10000)
				appDataList.put(a.uid, ad);
			System.out.println("package name:"+a.packageName +" ID:"+a.uid);
		}
	}

	public TextView getTextView(String s) {
		TextView t = new TextView(this);
		t.setLayoutParams(itemParams);
		t.setText(s);
		return t;
	}

	public TextView getBoldTextView(String s) {
		TextView t = new TextView(this);
		t.setLayoutParams(itemParams);
		t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		//t.setBackgroundColor(Color.LTGRAY);
		t.setGravity(Gravity.CENTER | Gravity.BOTTOM);
		t.setText(s);
		return t;
	}
}
