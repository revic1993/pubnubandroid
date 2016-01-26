package com.woofyapp.pubnub.adapters;

import android.util.Log;

import com.pubnub.api.Callback;
import com.pubnub.api.PubnubError;

/**
 * Created by rujul on 1/24/2016.
 */
public class BasicCallback extends Callback {
    public BasicCallback(){

    }

    @Override
    public void successCallback(String channel, Object response) {
        Log.d("PUBNUBSAILS", "Success: " + response.toString());
    }

    @Override
    public void connectCallback(String channel, Object message) {
        Log.d("PUBNUBSAILS", "Connect: " + message.toString());
    }

    @Override
    public void errorCallback(String channel, PubnubError error) {
        Log.d("PUBNUBSAILS", "Error: " + error.toString());
    }
}
