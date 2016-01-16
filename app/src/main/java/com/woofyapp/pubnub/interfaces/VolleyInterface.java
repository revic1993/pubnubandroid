package com.woofyapp.pubnub.interfaces;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by rujul on 1/16/2016.
 */
public interface VolleyInterface {
    public void onSuccess(JSONObject json);
    public void onError(VolleyError error);
}
