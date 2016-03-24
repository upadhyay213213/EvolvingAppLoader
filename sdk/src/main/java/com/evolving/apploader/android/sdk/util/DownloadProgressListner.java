package com.evolving.apploader.android.sdk.util;


public interface DownloadProgressListner {
    public void getDownloadProgress(int progressInpercentage);
    public void onDownlodCompleted(String downlodedFileUri);
}
