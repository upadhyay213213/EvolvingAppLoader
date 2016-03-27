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
public class ProvisionalOfferRequest extends Request<ProvisionalOfferResponse> {
    private Response.Listener<ProvisionalOfferResponse> mListener;

    public ProvisionalOfferRequest(String url, Response.Listener responseListener, Response.ErrorListener listener, String userAgent) {
        super(Method.POST, url, listener);
        mListener = responseListener;
//        try {
//            getHeaders().put("User-Agent", userAgent);
//        } catch (AuthFailureError authFailureError) {
//            authFailureError.printStackTrace(); //todo
//        }
    }

    @Override
    public Map<String, String> getHeaders(){
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("User-agent", "Android");
        return headers;
    }

    @Override
    protected Response<ProvisionalOfferResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        Gson gson = new Gson();
        try {
            String json = new String(
                    networkResponse.data,
                    HttpHeaderParser.parseCharset(networkResponse.headers));
            return Response.success(
                    gson.fromJson(json, ProvisionalOfferResponse.class),
                    HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(ProvisionalOfferResponse getConfigResponse) {
        if (mListener != null) mListener.onResponse(getConfigResponse);
    }

    public static class Builder {

        private String mICCID;
        private String mIMEI;
        private String mUserAgent;

        public Builder(String iccid, String imei, String useragent) {
            mICCID = iccid;
            mIMEI = imei;
            mUserAgent = useragent;
        }

        public ProvisionalOfferRequest build(String baseUrl, Response.Listener listener, Response.ErrorListener errorListener) {
            StringBuilder stringBuilder = new StringBuilder(baseUrl);
            stringBuilder.append(AppLoaderConstants.URL_GET_PROVISIONAL_OFFER);
            stringBuilder.append("?ICCID=").append(mICCID).append("&IMEI=").append(mIMEI);
            return new ProvisionalOfferRequest(stringBuilder.toString(), listener, errorListener, mUserAgent);
        }
    }
}