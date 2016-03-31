package com.evolving.apploader.android.sdk.api;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.evolving.apploader.android.sdk.HttpsTrustManager;
import com.evolving.apploader.android.sdk.util.AppLoaderConstants;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nandan on 3/22/2016.
 */
public class GetConfigRequest extends Request<GetConfigResponse> {
    private Response.Listener<GetConfigResponse> mListener;
    private  String mICCID;
    private  String mIMEI;

    public GetConfigRequest(String url, Response.Listener responseListener, Response.ErrorListener listener, String ICCID, String IMEI) {
        super(Method.PUT, url, listener);
        mListener = responseListener;
        mICCID=ICCID;
        mIMEI=IMEI;
    }

    @Override
    protected Map<String,String> getParams(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/x-www-form-urlencoded");
        params.put("ICCID", "12345678901234567890");
        params.put("IMEI", mIMEI);
        return params;
    }

    @Override
    public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("User-Agent", "Android");
        return headers;
    }

    @Override
    protected Response<GetConfigResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        Gson gson = new Gson();
        System.out.println("Volley");
        try {
            String json = new String(
                    networkResponse.data,
                    HttpHeaderParser.parseCharset(networkResponse.headers));
            return Response.success(
                    gson.fromJson(json, GetConfigResponse.class),
                    HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }


    @Override
    protected void deliverResponse(GetConfigResponse getConfigResponse) {
        if (mListener != null)
            mListener.onResponse(getConfigResponse);
        System.out.println("Volley"+getConfigResponse.getBaseUrl());
    }

    public static class Builder {
        private  String mICCID;
        private  String mIMEI;

        public Builder(String iccid, String imei) {
            mICCID = iccid;
            mIMEI = imei;
        }

        public GetConfigRequest build(Response.Listener listener, Response.ErrorListener errorListener) {
            StringBuilder stringBuilder = new StringBuilder(AppLoaderConstants.BASE_URL);
            stringBuilder.append(AppLoaderConstants.URL_GETCONFIG);
            return new GetConfigRequest(stringBuilder.toString(), listener, errorListener,mICCID,mIMEI);
        }
    }

}
