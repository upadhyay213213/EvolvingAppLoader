package com.evolving.apploader.android.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.evolving.apploader.android.app.R;
import com.evolving.apploader.android.sdk.AppLoaderManager;
import com.evolving.apploader.android.sdk.model.AppTotalData;
import com.evolving.apploader.android.sdk.model.ProvisionalOffer;

import java.util.ArrayList;

/**
 * Created by nupadhay on 3/23/2016.
 */
public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        StringBuffer m = new StringBuffer();
//          for(int i =0; i< AppLoaderManager.getTotalAppData().getUsageAppListSorted().size();i++){
//              m.append(AppLoaderManager.getTotalAppData().getUsageAppListSorted().get(i).getAppName());
//              m.append(AppLoaderManager.getTotalAppData().getUsageAppListSorted().get(i).getmMobileData());
//              m.append(AppLoaderManager.getTotalAppData().getUsageAppListSorted().get(i).getmWifiData());
//          }
   //     System.out.println("AppdataUsage" + AppLoaderManager.getTotalAppData().getLatitude());
        final TextView text = (TextView) findViewById(R.id.text);
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder stringBuilder = new StringBuilder("");
                AppTotalData appTotalData = AppLoaderManager.getTotalAppData();
                stringBuilder.append(appTotalData.getIccid()).append("\n")
                        .append(appTotalData.getImei()).append("\n")
                        .append(appTotalData.getLatitude()).append("\n")
                        .append(appTotalData.getLongitude()).append("\n")
                        .append(appTotalData.getTotalCellularBytes()).append("\n")
                        .append(appTotalData.getTotalWifiBytes()).append("\n")
                        .append(appTotalData.getUsageAppListSorted()).append("\n");
                text.setText(stringBuilder);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder stringBuilder = new StringBuilder("");
                ArrayList<ProvisionalOffer> provisionalOffers = AppLoaderManager.getProvisionalOffer(getApplicationContext());
                for (ProvisionalOffer p : provisionalOffers) {
                    stringBuilder.append(p.getmLabel()).append(" ")
                            .append(p.getmDescription()).append("\n");
                }
                text.setText(stringBuilder);
            }
        });
    }

}
