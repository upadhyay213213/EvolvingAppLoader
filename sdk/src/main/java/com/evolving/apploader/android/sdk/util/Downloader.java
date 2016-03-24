package com.evolving.apploader.android.sdk.util;

import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;


public class Downloader {
    private final NotificationManager mNotifyManager;
    private final NotificationCompat.Builder mBuilder;
    private Context context;
    private String url;
    private DownloadManager dm;
    private long enqueue;
    private DownloadProgressListner downloadProgressListner;

    public Downloader(Context context, String url) {
        this.context = context;
        this.url = url;
        this.downloadProgressListner = (DownloadProgressListner) context;

        mNotifyManager = (NotificationManager) context.getApplicationContext().getSystemService(context.getApplicationContext().NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setContentTitle("Downloading file")
                .setContentText("Downloading... 0%");
        mNotifyManager.notify(11, mBuilder.build());


    }

    public void startDownloading() {
        dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse(url));
        enqueue = dm.enqueue(request);
        new Thread(new Runnable() {

            @Override
            public void run() {

                boolean downloading = true;

                while (downloading) {

                    DownloadManager.Query q = new DownloadManager.Query();
                    q.setFilterById(enqueue);

                    Cursor cursor = dm.query(q);
                    if (cursor.moveToFirst()) {

                        if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                            downloading = false;
                            String uriString = cursor// URI of downloaded file
                                    .getString(cursor
                                            .getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                            downloadProgressListner.onDownlodCompleted(uriString);
                        }

                        int progress=getProgressPercentage();
                        mBuilder.setContentText("Downloading... " +String.valueOf(progress))
                                .setProgress(100, progress, false);
                        mNotifyManager.notify(11, mBuilder.build());
                        downloadProgressListner.getDownloadProgress(progress);

                        Log.d("Status message", statusMessage(cursor));
                        cursor.close();
                    }
                }
            }
        }).start();

    }


    // Get the downloaded percent
    private int getProgressPercentage() {

        int DOWNLOADED_BYTES_SO_FAR_INT = 0, TOTAL_BYTES_INT = 0, PERCENTAGE = 0;

        try {
            Cursor c = dm.query(new DownloadManager.Query()
                    .setFilterById(enqueue));

            if (c.moveToFirst()) {
                DOWNLOADED_BYTES_SO_FAR_INT = (int) c
                        .getLong(c
                                .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                TOTAL_BYTES_INT = (int) c
                        .getLong(c
                                .getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
            }

            System.out.println("PERCEN ------" + DOWNLOADED_BYTES_SO_FAR_INT
                    + " ------ " + TOTAL_BYTES_INT + "****" + PERCENTAGE);
            PERCENTAGE = (DOWNLOADED_BYTES_SO_FAR_INT * 100 / TOTAL_BYTES_INT);
            System.out.println("PERCENTAGE % " + PERCENTAGE);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return PERCENTAGE;
    }

    private String statusMessage(Cursor c) {
        String msg = "???";

        switch (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
            case DownloadManager.STATUS_FAILED:
                msg = "Download failed!";
                break;

            case DownloadManager.STATUS_PAUSED:
                msg = "Download paused!";
                break;

            case DownloadManager.STATUS_PENDING:
                msg = "Download pending!";
                break;

            case DownloadManager.STATUS_RUNNING:
                msg = "Download in progress!";

                getProgressPercentage();
                break;

            case DownloadManager.STATUS_SUCCESSFUL:
                msg = "Download complete!";
                break;

            default:
                msg = "Download is nowhere in sight";
                break;
        }

        return (msg);
    }
}
