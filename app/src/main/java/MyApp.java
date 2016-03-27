import android.app.Application;

import com.evolving.apploader.android.sdk.AppLoaderManager;

/**
 *
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppLoaderManager.init(getApplicationContext(), "https://pestlinux7.uk.evolving.com/sdk");
    }
}
