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
 * Created by vipul on 3/22/2016.
 */
public class GetConfigRequest extends Request<GetConfigResponse> {
    private Response.Listener<GetConfigResponse> mListener;

    String mUserAgent;
    public GetConfigRequest(String url, Response.Listener responseListener, Response.ErrorListener listener, String userAgent) {
        super(Method.PUT, url, listener);
        mListener = responseListener;
        mUserAgent = userAgent;

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
    protected Response<GetConfigResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        Gson gson = new Gson();
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

        public GetConfigRequest build(Response.Listener listener, Response.ErrorListener errorListener) {
            StringBuilder stringBuilder = new StringBuilder(AppLoaderConstants.BASE_URL);
            stringBuilder.append(AppLoaderConstants.URL_GETCONFIG);
            stringBuilder.append("?ICCID=").append(mICCID).append("&IMEI=").append(mIMEI);
            return new GetConfigRequest(stringBuilder.toString(), listener, errorListener, mUserAgent);
        }
    }

}
