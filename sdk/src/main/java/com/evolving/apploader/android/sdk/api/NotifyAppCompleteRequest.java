package com.evolving.apploader.android.sdk.api;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.evolving.apploader.android.sdk.util.AppLoaderConstants;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nupadhay on 3/23/2016.
 */
public class NotifyAppCompleteRequest extends Request<NotifyAppCompleteResponse> {
    private Response.Listener<NotifyAppCompleteResponse> mListener;
    private String mICCID;
    private String mIMEI;
    private String mPackageID;


    public NotifyAppCompleteRequest(String url, Response.Listener responseListener, Response.ErrorListener listener, String ICCID, String IMEI, String PackageID) {
        super(Method.PUT, url, listener);
        mListener = responseListener;
        mICCID=ICCID;
        mIMEI=IMEI;
        mPackageID=PackageID;
    }

    @Override
    protected Map<String,String> getParams(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/x-www-form-urlencoded");
        params.put("ICCID", mICCID);
        params.put("IMEI",mIMEI);
        params.put("PACKAGE_NAME", mPackageID);

        return params;
    }

    @Override
    public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("User-Agent", "Android");
        return headers;
    }


    @Override
    protected Response<NotifyAppCompleteResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        Gson gson = new Gson();
        try {
            String json = new String(
                    networkResponse.data,
                    HttpHeaderParser.parseCharset(networkResponse.headers));
            return Response.success(
                    gson.fromJson(json, NotifyAppCompleteResponse.class),
                    HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(NotifyAppCompleteResponse getConfigResponse) {
        if (mListener != null) mListener.onResponse(getConfigResponse);
    }

    public static class Builder {

        private String mICCID;
        private String mIMEI;
        private String mPackageID;

        public Builder(String iccid, String imei, String useragent,String packageID) {
            mICCID = iccid;
            mIMEI = imei;
            mPackageID=packageID;
        }

        public NotifyAppCompleteRequest build(Response.Listener listener, Response.ErrorListener errorListener) {
            StringBuilder stringBuilder = new StringBuilder(AppLoaderConstants.BASE_URL);
            stringBuilder.append(AppLoaderConstants.URL_GET_NOTIFY_APP_COMPLETE);
            return new NotifyAppCompleteRequest(stringBuilder.toString(), listener, errorListener,mICCID,mIMEI,mPackageID);
        }
    }
}
