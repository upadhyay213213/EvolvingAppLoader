package com.evolving.apploader.android.sdk.downlaoder;


public interface DownloadProgressListner {
    public void getDownloadProgress(int progressInpercentage);
    public void onDownlodCompleted(String downlodedFileUri);
}
