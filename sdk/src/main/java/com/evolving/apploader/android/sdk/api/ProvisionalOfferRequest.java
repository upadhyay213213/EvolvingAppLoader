package com.evolving.apploader.android.sdk.api;

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
public class ProvisionalOfferRequest extends Request<ProvisionalOfferResponseOne> {
    private Response.Listener<ProvisionalOfferResponseOne> mListener;
    private String mICCID;
    private String mIMEI;

    public ProvisionalOfferRequest(String url, Response.Listener responseListener, Response.ErrorListener listener, String ICCID, String IMEI) {
        super(Method.POST, url, listener);
        mListener = responseListener;
        mICCID=ICCID;
        mIMEI=IMEI;
    }

    @Override
    protected Map<String,String> getParams(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/x-www-form-urlencoded");
        params.put("ICCID","12345678901234567890");
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
    protected Response<ProvisionalOfferResponseOne> parseNetworkResponse(NetworkResponse networkResponse) {
        Gson gson = new Gson();
        try {
            String json = new String(
                    networkResponse.data,
                    HttpHeaderParser.parseCharset(networkResponse.headers));
            return Response.success(
                    gson.fromJson(json, ProvisionalOfferResponseOne.class),
                    HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(ProvisionalOfferResponseOne getConfigResponse) {
        if (mListener != null) mListener.onResponse(getConfigResponse);
    }

    public static class Builder {
        private String mICCID;
        private String mIMEI;
        public Builder(String iccid, String imei) {
            mICCID = iccid;
            mIMEI = imei;
        }

        public ProvisionalOfferRequest build(String baseUrl, Response.Listener listener, Response.ErrorListener errorListener) {
            StringBuilder stringBuilder = new StringBuilder(AppLoaderConstants.BASE_URL);
            stringBuilder.append(AppLoaderConstants.URL_GET_PROVISIONAL_OFFER);
            return new ProvisionalOfferRequest(stringBuilder.toString(), listener, errorListener,mICCID,mIMEI);
        }
    }
}