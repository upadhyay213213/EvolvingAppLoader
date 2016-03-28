package ui;

import android.app.Activity;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.evolving.apploader.android.app.R;
import com.evolving.apploader.android.sdk.AppLoaderManager;
import com.evolving.apploader.android.sdk.api.GetConfigResponse;

/**
 * Created by nupadhay on 3/23/2016.
 */
public class TestActivity extends Activity implements Response.Listener ,Response.ErrorListener {

    AppLoaderManager mManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StringBuffer m = new StringBuffer();
//          for(int i =0; i< AppLoaderManager.getTotalAppData().getUsageAppListSorted().size();i++){
//              m.append(AppLoaderManager.getTotalAppData().getUsageAppListSorted().get(i).getAppName());
//              m.append(AppLoaderManager.getTotalAppData().getUsageAppListSorted().get(i).getmMobileData());
//              m.append(AppLoaderManager.getTotalAppData().getUsageAppListSorted().get(i).getmWifiData());
//          }
        System.out.println("AppdataUsage" + AppLoaderManager.getTotalAppData().getLatitude());
    }
    @Override
    public void onErrorResponse(VolleyError volleyError) {
        System.out.println("ErrorEvol"+volleyError.getMessage());
    }

    @Override
    public void onResponse(Object o) {

        GetConfigResponse getConfigResponse = (GetConfigResponse) o;
        System.out.println("ResponseEvol"+getConfigResponse.getResult());

    }

}
