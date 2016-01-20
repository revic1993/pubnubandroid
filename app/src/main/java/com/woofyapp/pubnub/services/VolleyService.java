package com.woofyapp.pubnub.services;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.woofyapp.pubnub.application.Constants;
import com.woofyapp.pubnub.application.PubnubApp;
import com.woofyapp.pubnub.interfaces.VolleyInterface;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by rujul on 1/16/2016.
 */
public class VolleyService {

    private PubnubApp mApp;

    public VolleyService() {
        mApp = PubnubApp.getApp();
    }

    public void makePostRequest(String url,Map<String,String> params, final VolleyInterface view){
        params.put(Constants.TOKEN_NAME,Constants.TOKEN);
        JsonObjectRequest jsonObjectRequest =   new JsonObjectRequest
                    (Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        view.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        view.onError(error);
                    }
                });
        mApp.addToRequestQueue(jsonObjectRequest, Constants.TAG);
    }



}
