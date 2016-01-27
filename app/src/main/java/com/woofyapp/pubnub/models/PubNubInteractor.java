package com.woofyapp.pubnub.models;

import android.util.Log;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.PubnubException;
import com.woofyapp.pubnub.adapters.BasicCallback;
import com.woofyapp.pubnub.application.Constants;
import com.woofyapp.pubnub.interfaces.PubNubInterface;

import org.json.JSONObject;

/**
 * Created by rujul on 1/25/2016.
 */
public class PubNubInteractor {
    private Pubnub mPubnub;
    private PubNubInterface currentView;

    public PubNubInteractor(String UUID){
        mPubnub = new Pubnub(Constants.PUBNUB_PUBLISH,Constants.PUBNUB_SUBSCRIBE);
        mPubnub.setUUID(UUID);
    }

    public void setChatView(PubNubInterface view){
        currentView = view;
    }

    public void subscribePubNub(String[] channelName){

        Callback cb = new Callback() {
            @Override
            public void successCallback(String channel, Object message) {
                super.successCallback(channel, message);
                Log.d("SubscribedChannel","success "+channel+message);
                if(currentView!=null)
                    currentView.Success(channel,message);
            }

            @Override
            public void connectCallback(String channel, Object message) {
                Log.d("SubscribedChannel","connect "+channel + currentView.getClass());
            }

            @Override
            public void errorCallback(String channel, PubnubError error) {
                super.errorCallback(channel, error);
                Log.d("SubscribedChannel","error "+ channel + error.toString());
            }
        };
        try {
            mPubnub.subscribe(channelName, cb);
        } catch (PubnubException e) {
            e.printStackTrace();
        }
    }

    public void publishPubNub(String channelName,JSONObject data){
        mPubnub.publish(channelName, data, new BasicCallback());
    }

    public void unsubscribeChannels(){
        mPubnub.unsubscribeAll();
        currentView = null;
    }

    public boolean isPubNubNull(){
        return this.mPubnub==null;
    }

}
